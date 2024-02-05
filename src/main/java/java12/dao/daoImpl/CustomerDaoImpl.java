package java12.dao.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import java12.config.HibernateConfig;
import java12.dao.CustomerDao;
import java12.entities.*;
import org.hibernate.HibernateException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDaoImpl implements CustomerDao {
    EntityManagerFactory entityManagerFactory = HibernateConfig.getSession();
    @Override
    public String saveCustomerOzu(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
      try {  entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        entityManager.close();
        return "Успешно сохранен!";
    } catch (
    HibernateException e) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        return e.getMessage();
    } finally {
        entityManager.close();
    }
    }

    @Override
    public String saveCustomerRentInto(Customer newCustomer, Long houseId, Long agencyId, LocalDate checkin, LocalDate checkout) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Agency findAgency = entityManager.find(Agency.class, agencyId);
            House findHouse = entityManager.find(House.class, houseId);
            if (!checkHouseAble(entityManager, houseId, checkin, checkout)){
                return "There are no houses for the selected dates";
            }
            RentInfo rentInfo = new RentInfo();
            rentInfo.setCustomers(newCustomer);
            rentInfo.setHouse(findHouse);
            rentInfo.setAgency(findAgency);
            rentInfo.setCheckin(checkin);
            rentInfo.setCheckout(checkout);

            findHouse.setRentInfo(rentInfo);
            findAgency.getRentInfos().add(rentInfo);
            entityManager.persist(rentInfo);
            entityManager.persist(newCustomer);

            if (newCustomer.getRentInfo() == null) {
                newCustomer.setRentInfo(new ArrayList<>());
            }
            newCustomer.getRentInfo().add(rentInfo);

            entityManager.persist(newCustomer);
            entityManager.persist(rentInfo);
            entityManager.getTransaction().commit();
            return newCustomer.getFirstName() + " Successfully saved!!!";
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return e.getMessage();
        }finally {
            entityManager.close();
        }
    };




    @Override
    public List<Customer> getaAllCusromer() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        String query = "select a from Customer a";
        List<Customer>CistomerList = entityManager.createQuery(query).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return CistomerList;
    }



    @Override
    public void DeleteCustomerById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Customer customer = entityManager.find(Customer.class,id);
            List<RentInfo> rentInfo = customer.getRentInfo();
            if (!rentInfo.isEmpty()){
                for (RentInfo info : rentInfo) {
                    if (info.getCheckout().isAfter(LocalDate.now())) {
                        System.out.println("У Customer усть активная аренда,невозможно удалить");
                    }
                    House house = info.getHouse();
                    house.setRentInfo(null);
                    Owner owner = info.getOwner();
                    owner.getRentInfos().remove(info);
                    Agency agency = info.getAgency();
                    agency.getRentInfos().remove(info);
                    entityManager.remove(info);
                }
            }
            entityManager.remove(customer);
            entityManager.getTransaction().commit();
            System.out.println(customer.getFirstName() + " Успешно удален!!");
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }}



    @Override
    public String updateCustomerById(Long id, Customer newOwner) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            try {

                Customer customer = entityManager.find(Customer.class,id);
                customer.setFirstName(newOwner.getFirstName());
                entityManager.merge(customer);
                entityManager.getTransaction().commit();
                return "Успешно изменен!";

            } catch (NullPointerException npe) {

                entityManager.getTransaction().rollback();
                return "Customer с ID " + id + " не найдено.";
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
    public Optional<Customer> findByCustomerId(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Customer findCustomer = null;
        try {
            entityManager.getTransaction().begin();
            findCustomer = entityManager.createQuery("select c from Customer c" +
                            " where id =:customerId", Customer.class)
                    .setParameter("customerId", id)
                    .getSingleResult();

            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return Optional.ofNullable(findCustomer);
    }
    private boolean checkHouseAble(EntityManager entityManager, Long houseId,
                                   LocalDate checkIn, LocalDate checkout) {
        String jpql = "select count(distinct r.house.id) from RentInfo r " +
                "where r.house.id = :houseId " +
                "and (:checkIn between r.checkin and r.checkin " +
                "or :checkout between r.checkin and r.checkout)";

        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("houseId", houseId)
                .setParameter("checkIn", checkIn)
                .setParameter("checkout", checkout)
                .getSingleResult();

        return count == 0;
    }}

