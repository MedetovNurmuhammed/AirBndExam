package java12.dao.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import java12.config.HibernateConfig;
import java12.dao.OwnerDao;
import java12.entities.*;
import org.hibernate.HibernateException;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OwnerDaoImpl implements OwnerDao {
    EntityManagerFactory entityManagerFactory = HibernateConfig.getSession();
    public String saveOwner(Owner owner) {
       LocalDate date1 = owner.getDateOfBirth();
        LocalDate date2 = LocalDate.now();
        if (owner.getDateOfBirth() == null) {
            return "Дата рождения Owner не установлена!";
        }
        long yearsDifference = date1.until(date2, ChronoUnit.YEARS);
        if (yearsDifference<18){
            return "Owner младше 18 лет и не может быть сохранен!";

        }
        else {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(owner);
            entityManager.getTransaction().commit();
            return "Owner успешно сохранен!";
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return e.getMessage();
        } finally {
            entityManager.close();
        }
    }

}

    public List<Owner> getaAllOwner() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        String query = "select a from Owner a";
        List<Owner>OwnerList = entityManager.createQuery(query).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return OwnerList;
    }

//    public void DeleteOwnerById(Long id) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        try {
//            entityManager.getTransaction().begin();
//            Owner owner = findByOwnerId(id);
//            entityManager.remove(owner);
//            entityManager.getTransaction().commit();
//            entityManager.close();
//            System.out.println("Успешно удален!");
//
//        }catch (Exception e){
//            if (entityManager.getTransaction().isActive()) {
//                entityManager.getTransaction().rollback();}
//        }}

    @Override
    public String saveOwner(Owner newOwner, House newHouse) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            LocalDate date1 = newOwner.getDateOfBirth();
            LocalDate date2 = LocalDate.now();
            long yearsDifference = date1.until(date2, ChronoUnit.YEARS);
            if (yearsDifference<18){
                return "Owner младше 18 лет и не может быть сохранен!";

            }
            else {
            entityManager.getTransaction().begin();
            newOwner.addHouse(newHouse);
            newHouse.setOwner(newOwner);
            entityManager.persist(newOwner);
            entityManager.persist(newHouse);
            entityManager.getTransaction().commit();
            return newOwner.getFirstName() + " Успешно сохранен!";
        } }catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: " + e.getMessage();
        } finally {
            entityManager.close();
        }
    }



    public String updateOwnerById(Long id, Owner newOwner) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            try {

                Owner owner = entityManager.find(Owner.class,id);
                owner.setFirstName(newOwner.getFirstName());
                entityManager.merge(owner);
                entityManager.getTransaction().commit();
                return "Успешно изменен!";

            } catch (NullPointerException npe) {

                entityManager.getTransaction().rollback();
                return "Owner с ID " + id + " не найдено.";
            }

        } catch (HibernateException e) {

            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return e.getMessage();

        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Owner> findOwnerById(Long ownerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Owner findOwner = null;
        try {
            entityManager.getTransaction().begin();
            findOwner = entityManager.createQuery("select o from Owner o where id =:ownerId", Owner.class)
                    .setParameter("ownerId", ownerId)
                    .getSingleResult();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return Optional.ofNullable(findOwner);

    }

    @Override
    public String deleteOwnerById(Long ownerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Owner findOwner = entityManager.find(Owner.class, ownerId);
            List<RentInfo> rentInfo = findOwner.getRentInfos();
            if (!rentInfo.isEmpty()){
                for (RentInfo info : rentInfo) {
                    if (info.getCheckout().isAfter(LocalDate.now())) {
                        return "Невозможно удалить Owner. его дом сдан в аренду";
                    }
                    Agency agency = info.getAgency();
                    agency.getRentInfos().remove(info);
                    Customer customer = info.getCustomers();
                    customer.getRentInfo().remove(info);
                    entityManager.remove(info);
                }
            }
            List<Agency> findAgency = findOwner.getAgencies();
            for (Agency agency : findAgency) {
                agency.getOwners().remove(findOwner);
            }
            entityManager.remove(findOwner);
            entityManager.getTransaction().commit();
            return findOwner.getFirstName()+" Успешно удален!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return  e.getMessage();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public String assignOwnerToAgency(Long ownerId, Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Owner findOwner = entityManager.find(Owner.class, ownerId);
            Agency findAgency = entityManager.find(Agency.class, agencyId);
            findOwner.getAgencies().add(findAgency);
            findAgency.getOwners().add(findOwner);
            entityManager.getTransaction().commit();
            return "Successfully assigned!!!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return  e.getMessage();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Owner> getOwnersByAgencyId(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Owner> owners = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            owners = entityManager.createQuery("select o from Owner o join o.agencies a" +
                            " where a.id =:agencyId", Owner.class)
                    .setParameter("agencyId", agencyId)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return owners;
    }

//    public Owner findByOwnerId(Long id) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        try {
//            entityManager.getTransaction().begin();
//            Owner agency = entityManager.createQuery("SELECT a FROM Owner a WHERE a.id = :id", Owner.class)
//                    .setParameter("id", id)
//                    .getSingleResult();
//            entityManager.getTransaction().commit();
//            return agency;
//        } catch (NoResultException e) {
//            System.out.println("Agency с ID " + id + " не найден.");
//            return null;
//        } finally {
//            entityManager.close();
//        }
//    }
    }

