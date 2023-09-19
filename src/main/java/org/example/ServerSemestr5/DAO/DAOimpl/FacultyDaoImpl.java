package org.example.ServerSemestr5.DAO.DAOimpl;

import org.example.ServerSemestr5.DAO.DAO;
import org.example.ServerSemestr5.Model.entity.Faculty;
import org.example.ServerSemestr5.Utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

public class FacultyDaoImpl implements DAO<Faculty> {

//    @Override
////    public Faculty findEntity(int id) {
//////        User user = null;
//////        try {
//////            Session session = HibernateUtil.getSessionFactory().openSession();
//////            Transaction tx = session.beginTransaction();
//////            Query query = session.createQuery("from User where idUser = :paramName");
//////            query.setParameter("paramName", "id");
//////            user = (User) query.getSingleResult();
//////            tx.commit();
//////            session.close();
//////        } catch (NoClassDefFoundError | NoResultException e) {
//////            System.out.println("Exception: " + e);
//////        }
//////        return user;
////        return null;
////    }



    @Override
    public List<Faculty> findAllEntity() {
        return (List<Faculty>) HibernateUtil.getSessionFactory().openSession().createQuery("FROM Faculty order by facultyName").list();
    }

    @Override
    public boolean updateEntity(Faculty entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Faculty> list = session.createQuery("FROM Faculty").list();
        for (Faculty f:list){
            if(f.getIdFaculty()==entity.getIdFaculty()){
                f.setFacultyName(entity.getFacultyName());
                try {
                    Transaction tx = session.beginTransaction();
                    session.update(f);
                    tx.commit();
                    session.close();
                    return true;
                }
                catch (Exception e){
                    System.out.println("Exception: " + e);
                }
            }
        }
        return false;
    }

    public boolean deleteEntity(Faculty entity){
        boolean isUpdated = false;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<Faculty> list = session.createQuery("FROM Faculty").list();
            for (Faculty f:list){
                if(f.getFacultyName().equals(entity.getFacultyName())){
                    Transaction tx = session.beginTransaction();
                    session.delete(f);
                    tx.commit();
                    session.close();
                    isUpdated = true;
                }
            }


        } catch (NoClassDefFoundError | NoResultException e) {
            System.out.println("Exception: " + e);
        }
        return isUpdated;
    };


//    private List<Faculty> getAllEntityNotConnectedToSession(){
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        List<Faculty> faculties = (List<Faculty>) session.createQuery("FROM Faculty").list();
//        session.close();
//        return faculties;
//    }
}
