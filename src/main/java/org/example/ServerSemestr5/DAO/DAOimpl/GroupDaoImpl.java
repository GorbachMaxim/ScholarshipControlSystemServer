

package org.example.ServerSemestr5.DAO.DAOimpl;

import org.example.ServerSemestr5.DAO.DAO;
import org.example.ServerSemestr5.Model.entity.Faculty;
import org.example.ServerSemestr5.Model.entity.Group;
import org.example.ServerSemestr5.Model.entity.Student;
import org.example.ServerSemestr5.Utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

public class GroupDaoImpl implements DAO<Group> {
    @Override
    public List<Group> findAllEntity() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Group> groups = (List<Group>) session.createQuery("FROM Group order by name").list();

        for(Group gr: groups){
            gr.getStudents().contains(new Student());
            gr.getSubjects().contains(new Group());
            gr.getFaculty().getGroups().contains(new Group());
            gr.getFaculty().getUsers().contains(new Group());
        }
        session.close();
        return groups;
    }

    @Override
    public boolean updateEntity(Group entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Group> list = session.createQuery("FROM Group ").list();
        for (Group g:list){
            if(g.getIdGroup()==entity.getIdGroup()){
                g.setName(entity.getName());
                g.setFaculty(entity.getFaculty());
                try {
                    Transaction tx = session.beginTransaction();
                    session.update(g);
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

    public boolean updateSubjects(Group entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Group> list = session.createQuery("FROM Group ").list();
        for (Group g:list){
            if(g.getIdGroup()==entity.getIdGroup()){
                g.setSubjects(entity.getSubjects());
                try {
                    Transaction tx = session.beginTransaction();
                    session.update(g);
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

    public boolean deleteEntity(Group entity){
        boolean isUpdated = false;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<Group> list = session.createQuery("FROM Group").list();
            for (Group g:list){
                if(g.getIdGroup()==entity.getIdGroup()){
                    Transaction tx = session.beginTransaction();
                    session.delete(g);
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
