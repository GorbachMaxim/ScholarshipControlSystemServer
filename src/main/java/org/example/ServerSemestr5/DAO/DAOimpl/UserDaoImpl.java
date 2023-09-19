package org.example.ServerSemestr5.DAO.DAOimpl;


import org.example.ServerSemestr5.DAO.DAO;
import org.example.ServerSemestr5.Model.entity.User;
import org.example.ServerSemestr5.Utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

public class UserDaoImpl implements DAO<User> {

//    @Override
//    public User findEntity(int id) {
////        User user = null;
////        try {
////            Session session = HibernateUtil.getSessionFactory().openSession();
////            Transaction tx = session.beginTransaction();
////            Query query = session.createQuery("from User where idUser = :paramName");
////            query.setParameter("paramName", "id");
////            user = (User) query.getSingleResult();
////            tx.commit();
////            session.close();
////        } catch (NoClassDefFoundError | NoResultException e) {
////            System.out.println("Exception: " + e);
////        }
////        return user;
//        return null;
//    }

    @Override
    public boolean updateEntity(User entity) {
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
    public boolean deleteEntity(User entity) {
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
    public List<User> findAllEntity() {
        List<User> users = (List<User>) HibernateUtil.getSessionFactory().openSession().createQuery("FROM User ORDER BY login ").list();
        return users;
    }


//    private List<User> getAllEntityNotConnectedToSession(){
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        List<User> users = (List<User>) session.createQuery("FROM User ORDER BY login ").list();
//        session.close();
//        return users;
//    }


}
