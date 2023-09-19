package org.example.ServerSemestr5.Service.serviceImpl;


import org.example.ServerSemestr5.DAO.DAO;
import org.example.ServerSemestr5.DAO.DAOimpl.UserDaoImpl;
import org.example.ServerSemestr5.Model.entity.User;
import org.example.ServerSemestr5.Service.Service;
import org.hibernate.HibernateError;

import java.util.List;
import java.util.Set;

public class UserServiceImpl implements Service<User> {

    private UserDaoImpl userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDaoImpl();
    }


    public boolean searchLogin(User entity) {
        List<User> users = userDAO.findAllEntity();
        for (User u : users) {
            if (u.getLogin().equals(entity.getLogin())) {
                return true;
            }
        }
        return false;
    }



    @Override
    public User searchEntity(User entity) {
        List<User> users = userDAO.findAllEntity();

        for (User u : users) {
            if (u.getLogin().equals(entity.getLogin())
                    && u.getPassword() == entity.getPassword()) {
                return u;
            }
        }
        entity.setLogin("1");
        return entity;
    }

    @Override
    public boolean isFindEntity(User entity) {
        List<User> users = userDAO.findAllEntity();

        for (User u : users) {
            if (u.getLogin().equals(entity.getLogin())
                    && u.getPassword() == entity.getPassword()
                    && u.isAdmin() ==  entity.isAdmin()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean save(User entity) {
        try {
            userDAO.saveEntity(entity);
            return  true;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
        }
        return false;
    }

    @Override
    public boolean update(User entity) {
        try {
            userDAO.updateEntity(entity);
            return true;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
        }
        return false;
    }

    @Override
    public boolean delete(User entity) {
        try {
            userDAO.deleteEntity(entity);
            return true;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
        }
        return false;
    }

    public List<User> getAllEntities() {
        return userDAO.findAllEntity();
    }


//    @Override
//    public boolean isFindEntity(int id) {
//        try {
//            userDAO.findEntity(id);
//            return true;
//        } catch (HibernateError e) {
//            System.out.println("Exception:" + e);
//        }
//        return false;
//    }
}
