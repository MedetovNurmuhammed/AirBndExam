package java12.dao.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import java12.config.HibernateConfig;
import java12.dao.AddressDao;
import java12.entities.Address;
import java12.entities.Agency;
import org.hibernate.HibernateException;

import java.util.*;

public class AddressDaoImpl implements AddressDao {
    EntityManagerFactory entityManagerFactory = HibernateConfig.getSession();

    @Override
    public List<Address> getallAddress() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            String query = "select a from Address a";
            List<Address> addresses = entityManager.createQuery(query).getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return addresses;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            entityManager.close();

        }
    }

    @Override
    public Address findAddressById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Address address = entityManager.find(Address.class, id);
            entityManager.getTransaction().commit();
            return address;
        } catch (NoResultException e) {
            System.out.println("Address с ID " + id + " не найден.");
            return null;
        } finally {
            entityManager.close();
        }
    }


    @Override
    public String updateAddress(Long id, Address newAddress) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Address address = findAddressById(id);
            if (address != null) {
                address.setCity(newAddress.getCity());
                address.setRegion(newAddress.getRegion());
                address.setStreet(newAddress.getStreet());
                entityManager.merge(address);
                entityManager.getTransaction().commit();
                return "Успешно изменен!";
            } else {
                return "Не существует";
            }
        } catch (HibernateException e) {

            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return e.getMessage();
        }
    }

    @Override
    public Map<Address,Agency> getallAgencyAddressById() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Map<Address, Agency> addresses = new HashMap<>();
        try {
            entityManager.getTransaction().begin();

            List<Address> resultList = entityManager.createQuery(
                            "select distinct a from Address a join a.agency", Address.class)
                    .getResultList();
            for (Address address : resultList) {
                addresses.put(address, address.getAgency());
            }

            entityManager.getTransaction().commit();
        } catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return addresses;
    }
    @Override
    public int countAgencynameCity(String Cityname) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            String query = "select count(a.agency.id) from Address a where a.city = :cityName";
            Long count = entityManager.createQuery(query, Long.class)
                    .setParameter("cityName", Cityname)
                    .getSingleResult();

            entityManager.getTransaction().commit();
            return count.intValue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Map<String, List<Agency>> groupByRegion() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            String query = "select a.region, a.agency from Address a";
            List<Object[]> resultList = entityManager.createQuery(query, Object[].class).getResultList();

            entityManager.getTransaction().commit();
            Map<String, List<Agency>> groupedByRegion = new HashMap<>();
            for (Object[] result : resultList) {
                String region = (String) result[0];
                Agency agency = (Agency) result[1];

                groupedByRegion.computeIfAbsent(region, k -> new ArrayList<>()).add(agency);
            }

            return groupedByRegion;
        } catch (Exception e) {
            System.out.println("Произошла ошибка при выполнении запроса.");
            return Collections.emptyMap();
        } finally {
            entityManager.close();
        }
    }
}