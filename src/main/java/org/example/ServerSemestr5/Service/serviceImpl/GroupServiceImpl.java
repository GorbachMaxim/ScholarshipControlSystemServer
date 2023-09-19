package org.example.ServerSemestr5.Service.serviceImpl;

import org.example.ServerSemestr5.DAO.DAOimpl.FacultyDaoImpl;
import org.example.ServerSemestr5.DAO.DAOimpl.GroupDaoImpl;
import org.example.ServerSemestr5.Model.entity.Faculty;
import org.example.ServerSemestr5.Model.entity.Group;
import org.example.ServerSemestr5.Service.Service;
import org.hibernate.HibernateError;

import java.util.List;

public class GroupServiceImpl implements Service<Group> {

    private GroupDaoImpl groupDao;

    public GroupServiceImpl() {
        this.groupDao = new GroupDaoImpl();
    }

    @Override
    public Group searchEntity(Group entity) {
        List<Group> groups = groupDao.findAllEntity();
        for (Group g : groups) {
            if (g.getName().equals(entity.getName())) {
                return g;
            }
        }
        return entity;
    }

    @Override
    public boolean isFindEntity(Group entity) {
        List<Group> groups = groupDao.findAllEntity();

        for (Group g : groups) {
            if (g.getName().equals(entity.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean save(Group entity) {
        try {
            groupDao.saveEntity(entity);
            return  true;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
            return false;
        }

    }

    @Override
    public boolean update(Group entity) {
        try {
            if(groupDao.updateEntity(entity))
                return true;
            else
                return false;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
            return false;
        }

    }

    public boolean updateSubjects(Group entity) {
        try {
            if(groupDao.updateSubjects(entity))
                return true;
            else
                return false;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
            return false;
        }

    }

    @Override
    public boolean delete(Group entity) {
        try {
            groupDao.deleteEntity(entity);
            return true;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
        }
        return false;
    }

    @Override
    public List<Group> getAllEntities() {
        return groupDao.findAllEntity();
    }
}

