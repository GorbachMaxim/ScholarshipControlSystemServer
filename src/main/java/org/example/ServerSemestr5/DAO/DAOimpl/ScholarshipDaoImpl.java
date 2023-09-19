package org.example.ServerSemestr5.DAO.DAOimpl;

import org.apache.commons.math3.util.Precision;
import org.example.ServerSemestr5.DAO.DAO;
import org.example.ServerSemestr5.Model.entity.Scholarship;
import org.example.ServerSemestr5.Utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class ScholarshipDaoImpl implements DAO<Scholarship> {
    @Override
    public boolean updateEntity(Scholarship entity) {

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
    public boolean deleteEntity(Scholarship entity) {
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
    public ArrayList<Scholarship> findAllEntity() {
        return (ArrayList<Scholarship>) HibernateUtil.getSessionFactory().openSession().createQuery("FROM Scholarship order by coefficient desc").list();
    }



    public void setAmount(double amount){
        List<Scholarship> list = findAllEntity();
        for (Scholarship sc: list) {
            Double buf = amount*sc.getCoefficient();
            buf = Precision.round(buf, 2);
            sc.setScholarshipAmount(buf);
            updateEntity(sc);
        }
    }
}
