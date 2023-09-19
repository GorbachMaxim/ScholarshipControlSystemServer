package org.example.ServerSemestr5.Service.serviceImpl;

import org.example.ServerSemestr5.DAO.DAO;
import org.example.ServerSemestr5.DAO.DAOimpl.FacultyDaoImpl;
import org.example.ServerSemestr5.Model.entity.Faculty;
import org.example.ServerSemestr5.Service.Service;
import org.hibernate.HibernateError;

import java.util.List;

public class FacultyServiceImpl implements Service<Faculty> {

    private FacultyDaoImpl facultyDAO;

    public FacultyServiceImpl() {
        this.facultyDAO = new FacultyDaoImpl();
    }

    @Override
    public Faculty searchEntity(Faculty entity) {
        List<Faculty> faculties = facultyDAO.findAllEntity();
        for (Faculty f : faculties) {
            if (f.getFacultyName().equals(entity.getFacultyName())) {
                return f;
            }
        }
        return entity;
    }

    @Override
    public boolean isFindEntity(Faculty entity) {
        List<Faculty> faculties = facultyDAO.findAllEntity();

        for (Faculty f : faculties) {
            if (f.getFacultyName().equals(entity.getFacultyName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean save(Faculty entity) {
        try {
            facultyDAO.saveEntity(entity);
            return  true;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
            return false;
        }

    }

    @Override
    public boolean update(Faculty entity) {
        try {
            if(facultyDAO.updateEntity(entity))
                return true;
            else
                return false;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
            return false;
        }

    }

    @Override
    public boolean delete(Faculty entity) {
        try {
            facultyDAO.deleteEntity(entity);
            return true;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
        }
        return false;
    }

    @Override
    public List<Faculty> getAllEntities() {
        return facultyDAO.findAllEntity();
    }

//    @Override
//    public boolean isFindEntity(int id) {
//        return false;
//    }


}
