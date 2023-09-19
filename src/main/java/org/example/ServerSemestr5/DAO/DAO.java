package org.example.ServerSemestr5.DAO;

import org.example.ServerSemestr5.Utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

public interface DAO<T> {
    default boolean saveEntity(T entity){
        boolean isUpdated = false;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            session.close();
            isUpdated = true;
        } catch (NoClassDefFoundError | NoResultException e) {
            System.out.println("Exception: " + e);
        }
        return isUpdated;
    };

    boolean updateEntity(T entity);

    boolean deleteEntity(T entity);

    public List<T> findAllEntity();
}
