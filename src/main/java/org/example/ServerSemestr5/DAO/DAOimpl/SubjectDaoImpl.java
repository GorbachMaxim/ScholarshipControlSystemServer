package org.example.ServerSemestr5.DAO.DAOimpl;

import org.example.ServerSemestr5.DAO.DAO;
import org.example.ServerSemestr5.Model.entity.Group;
import org.example.ServerSemestr5.Model.entity.Student;
import org.example.ServerSemestr5.Model.entity.Subject;
import org.example.ServerSemestr5.Utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

public class SubjectDaoImpl implements DAO<Subject> {


    @Override
    public List<Subject> findAllEntity() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Subject> subjects = (List<Subject>) session.createQuery("FROM Subject ORDER BY name ").list();
        for(Subject s: subjects){
            for(Group gr: s.getGroups()){
                gr.getSubjects().contains(new Group());
                for(Student st: gr.getStudents()){
                    st.getGroup().getSubjects().contains(new Group());
                    st.getGroup().getFaculty().getGroups().contains(new Group());
                    st.getGroup().getFaculty().getUsers().contains(new Group());
                }
                gr.getFaculty().getGroups().contains(new Group());
                gr.getFaculty().getUsers().contains(new Group());
            }
        }
        session.close();
        return subjects;
    }

    @Override
    public boolean updateEntity(Subject entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Subject> list = session.createQuery("FROM Subject").list();
        for (Subject s:list){
            if(s.getIdSubject()==entity.getIdSubject()){
                s.setName(entity.getName());
                s.setHoursNumber(entity.getHoursNumber());
                try {
                    Transaction tx = session.beginTransaction();
                    session.update(s);
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

    public boolean deleteEntity(Subject entity){
        boolean isUpdated = false;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<Subject> list = session.createQuery("FROM Subject").list();
            for (Subject s:list){
                if(s.getIdSubject()==entity.getIdSubject()){
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
    };
}
