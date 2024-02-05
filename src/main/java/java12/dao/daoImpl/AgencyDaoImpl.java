package java12.dao.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import java12.config.HibernateConfig;
import java12.dao.AgencyDao;
import java12.entities.Agency;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import java.util.List;

public class AgencyDaoImpl implements AgencyDao {
    EntityManagerFactory entityManagerFactory = HibernateConfig.getSession();
    ////    Agency тузулуп жатканда адреси кошо тузулсун!
    ////    Agency очкондо адреси жана rent_info кошо очсун
    ////    Constraint: phoneNumber (+996) and length(13)
    @Override
    public String saveAgency(Agency agency) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String phoneNumber = agency.getPhoneNumber();
            if (phoneNumber != null && !phoneNumber.startsWith("+996")) {
                return "Номер агентства должен начинаться с +996";
            }

            entityManager.getTransaction().begin();
            entityManager.persist(agency);
            entityManager.getTransaction().commit();
            return "Agency успешно сохранен!";
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
    public List<Agency> getaAllAgency() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
entityManager.getTransaction().begin();
String query = "select a from Agency a";
List<Agency>agencyList = entityManager.createQuery(query).getResultList();
entityManager.getTransaction().commit();
entityManager.close();
return agencyList;



    }

    @Override
    public void DeleteAgencyById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
       try {
           entityManager.getTransaction().begin();
Agency agency = findByAgencyId(id);
entityManager.remove(agency);
entityManager.getTransaction().commit();
entityManager.close();
           System.out.println("Успешно удален!");

    }catch (Exception e){
           if (entityManager.getTransaction().isActive()) {
           entityManager.getTransaction().rollback();}
           }}

    @Override
    public String updateAgencyById(Long id,Agency newAgency) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            try {

                Agency agency = findByAgencyId(id);
                agency.setName(newAgency.getName());
                entityManager.merge(agency);
                entityManager.getTransaction().commit();
                return "Успешно изменен!";

            } catch (NullPointerException npe) {

                entityManager.getTransaction().rollback();
                return "Агентство с ID " + id + " не найдено.";
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
    public Agency findByAgencyId(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Agency agency = entityManager.createQuery("SELECT a FROM Agency a WHERE a.id = :id", Agency.class)
                    .setParameter("id", id)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return agency;
        } catch (NoResultException e) {
            System.out.println("Agency с ID " + id + " не найден.");
            return null;
        } finally {
            entityManager.close();
        }
}}
