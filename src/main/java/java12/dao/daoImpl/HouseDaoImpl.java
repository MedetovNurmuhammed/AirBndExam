package java12.dao.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.HibernateConfig;
import java12.dao.HouseDao;
import java12.entities.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HouseDaoImpl implements HouseDao,AutoCloseable {
    EntityManagerFactory entityManagerFactory = HibernateConfig.getSession();
    @Override
    public String saveHouse(Long ownerId, House newHouse) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Owner findOwner = entityManager.find(Owner.class, ownerId);
            findOwner.getHouseList().add(newHouse);
            newHouse.setOwner(findOwner);
            entityManager.persist(newHouse);
            entityManager.getTransaction().commit();
            return newHouse.getHouseType() + " Успешно сохранен!!";
        } catch (Exception r) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return r.getMessage();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<House> findHouseById(Long houseId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        House findHouse = null;
        try {
            entityManager.getTransaction().begin();
            findHouse = entityManager.createQuery("select h from House h where id =:houseId", House.class)
                    .setParameter("houseId", houseId)
                    .getSingleResult();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return Optional.ofNullable(findHouse);
    }

    @Override
    public List<House> findAllHouse() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h", House.class)
                    .getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return houses;
    }

    @Override
    public String updateHouseById(Long houseId, House newHouse) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            House findHouse = entityManager.find(House.class, houseId);
            findHouse.setHouseType(newHouse.getHouseType());
            findHouse.setPrice(newHouse.getPrice());
            findHouse.setRating(newHouse.getRating());
            findHouse.setDescription(newHouse.getDescription());
            findHouse.setRoom(newHouse.getRoom());
            findHouse.setFurniture(newHouse.isFurniture());
            entityManager.getTransaction().commit();
            return newHouse.getHouseType() + " House Успешно измнен!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return e.getMessage();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public String deleteHouseById(Long houseId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            House findHouse = entityManager.find(House.class, houseId);
            RentInfo rentInfo = findHouse.getRentInfo();
            if (rentInfo.getCheckin() != null) {
                if (rentInfo.getCheckout().isAfter(LocalDate.now())) {
                    return "Невозможно удалить,в доме есть жители";
                }
                Owner owner = rentInfo.getOwner();
                owner.getRentInfos().remove(rentInfo);
                Agency agencyForDelete = rentInfo.getAgency();
                agencyForDelete.getRentInfos().remove(rentInfo);
                Customer infoCustomer = rentInfo.getCustomers();
                infoCustomer.getRentInfo().remove(rentInfo);
            }
            Address address = findHouse.getAddress();
            Agency agency = address.getAgency();
            agency.setAddress(null);
            Owner houseOwner = findHouse.getOwner();
            houseOwner.getHouseList().remove(findHouse);
            entityManager.remove(findHouse);
            entityManager.getTransaction().commit();
            return findHouse.getHouseType() + " Успешно удален!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return e.getMessage();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<House> getHousesInRegion(String region) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h " +
                            " where h.address.region =:region", House.class)
                    .setParameter("region", region).getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return houses;
    }

    @Override
    public List<House> allHousesByAgencyId(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h " +
                            " where h.address.agency.id =:agencyId", House.class)
                    .setParameter("agencyId", agencyId).getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return houses;
    }

    @Override
    public List<House> allHousesByOwnerId(Long ownerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h " +
                            " where h.owner.id =:ownerId", House.class)
                    .setParameter("ownerId", ownerId).getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(  e.getMessage());
        } finally {
            entityManager.close();
        }
        return houses;
    }


    @Override
    public List<House> housesBetweenDates(LocalDate fromDate, LocalDate toDate) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h " +
                            "where h.rentInfo.checkin between :from and :to", House.class)
                    .setParameter("from", fromDate)
                    .setParameter("to", toDate)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return houses;
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
