package org.example.ServerSemestr5.Service.serviceImpl;

import org.example.ServerSemestr5.DAO.DAO;
import org.example.ServerSemestr5.DAO.DAOimpl.FacultyDaoImpl;
import org.example.ServerSemestr5.DAO.DAOimpl.SubjectDaoImpl;
import org.example.ServerSemestr5.Model.entity.Faculty;
import org.example.ServerSemestr5.Model.entity.Subject;
import org.example.ServerSemestr5.Service.Service;
import org.hibernate.HibernateError;

import java.util.List;

public class SubjectServiceImpl implements Service<Subject> {

    private SubjectDaoImpl subjectDao;

    public SubjectServiceImpl() {
        this.subjectDao = new SubjectDaoImpl();
    }

    @Override
    public Subject searchEntity(Subject entity) {
        List<Subject> subjects = subjectDao.findAllEntity();
        for (Subject s : subjects) {
            if (s.getIdSubject()==entity.getIdSubject()) {
                return s;
            }
        }
        return entity;
    }

    @Override
    public boolean isFindEntity(Subject entity) {
        List<Subject> subjects = subjectDao.findAllEntity();
        for (Subject s : subjects) {
            if (s.getName().equals(entity.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean save(Subject entity) {
        try {
            subjectDao.saveEntity(entity);
            return  true;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
            return false;
        }

    }

    @Override
    public boolean update(Subject entity) {
        try {
            if(subjectDao.updateEntity(entity))
                return true;
            else
                return false;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
            return false;
        }

    }

    @Override
    public boolean delete(Subject entity) {
        try {
            subjectDao.deleteEntity(entity);
            return true;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
        }
        return false;
    }

    @Override
    public List<Subject> getAllEntities() {
        return subjectDao.findAllEntity();

    }


//    @Override
//    public boolean isFindEntity(int id) {
//        return false;
//    }


}
