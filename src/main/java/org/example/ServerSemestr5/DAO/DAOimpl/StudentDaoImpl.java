package org.example.ServerSemestr5.DAO.DAOimpl;

import org.example.ServerSemestr5.DAO.DAO;
import org.example.ServerSemestr5.Model.entity.Group;
import org.example.ServerSemestr5.Model.entity.Student;
import org.example.ServerSemestr5.Utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

public class StudentDaoImpl implements DAO<Student> {

    @Override
    public List<Student> findAllEntity() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> students = (List<Student>) session.createQuery("FROM Student ORDER BY group.name ").list();
        for(Student s: students){
            s.getGroup().getSubjects().contains(new Group());
            s.getGroup().getFaculty().getGroups().contains(new Group());
            s.getGroup().getFaculty().getUsers().contains(new Group());
        }
        session.close();
        return students;
    }

    @Override
    public boolean updateEntity(Student entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> list = (List<Student>) session.createQuery("FROM Student").list();
        for(Student s: list){
            s.getGroup().getSubjects().contains(new Group());
            s.getGroup().getFaculty().getGroups().contains(new Group());
            s.getGroup().getFaculty().getUsers().contains(new Group());
        }
        for (Student s : list) {
            if (s.getIdStudent() == entity.getIdStudent()) {
                s.setLastname(entity.getLastname());
                s.setFirstname(entity.getFirstname());
                s.setBudget(entity.isBudget());
                s.setMarks(entity.getMarks());
                s.setGroup(entity.getGroup());
                try {
                    Transaction tx = session.beginTransaction();
                    session.update(s);
                    tx.commit();
                    session.close();
                    return true;
                } catch (Exception e) {
                    System.out.println("Exception: " + e);
                }
            }
        }
        return false;
    }

    public boolean deleteEntity(Student entity) {
        boolean isUpdated = false;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<Student> list = session.createQuery("FROM Student").list();
            for (Student s : list) {
                if (s.getIdStudent() == entity.getIdStudent()) {
                    Transaction tx = session.beginTransaction();
                    session.delete(s);
                    tx.commit();
                    session.close();
                    isUpdated = true;
                }
            }


        } catch (NoClassDefFoundError | NoResultException e) {
            System.out.println("Exception: " + e);
        }
        return isUpdated;
    }
}