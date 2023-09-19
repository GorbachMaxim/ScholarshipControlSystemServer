package org.example.ServerSemestr5.DAO.DAOimpl;

import org.example.ServerSemestr5.DAO.DAO;
import org.example.ServerSemestr5.Model.entity.Person;
import org.example.ServerSemestr5.Utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

public class PersonDaoImpl implements DAO<Person> {


    @Override
    public boolean updateEntity(Person entity) {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            session.close();
            return true;
        } catch (NoClassDefFoundError | NoResultException e) {
            System.out.println("Exception: " + e);

        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return false;

    }

    @Override
    public boolean deleteEntity(Person entity) {
        boolean isUpdated = false;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
            session.close();
            isUpdated = true;
        } catch (NoClassDefFoundError | NoResultException e) {
            System.out.println("Exception: " + e);
        }
        return isUpdated;
    }

    @Override
    public List<Person> findAllEntity() {
        return (List<Person>) HibernateUtil.getSessionFactory().openSession().createQuery("FROM Person").list();
    }

//    @Override
//    private List<Person> getAllEntityNotConnectedToSession(){
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        List<Person> people = (List<Person>) session.createQuery("FROM Person").list();
//        session.close();
//        return people;
//    }
}
