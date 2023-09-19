package org.example.ServerSemestr5.Service.serviceImpl;

import org.example.ServerSemestr5.DAO.DAO;
import org.example.ServerSemestr5.DAO.DAOimpl.PersonDaoImpl;
import org.example.ServerSemestr5.Model.entity.Person;
import org.example.ServerSemestr5.Service.Service;
import org.hibernate.HibernateError;

import java.util.List;

public class PersonServiceImpl implements Service<Person> {

    private PersonDaoImpl personDAO;

    public PersonServiceImpl() {
        this.personDAO = new PersonDaoImpl();
    }


    @Override
    public Person searchEntity(Person entity) {
        List<Person> persons = personDAO.findAllEntity();

        for (Person p : persons) {
            if (p.getFirstname().equals(entity.getFirstname())
                    && p.getLastname().equals(entity.getLastname())
                    && p.getPatronymic().equals(entity.getPatronymic())) {
                return p;
            }
        }
        return entity;
    }

    @Override
    public boolean isFindEntity(Person entity) {
        List<Person> persons = personDAO.findAllEntity();

        for (Person p : persons) {
            if (p.getFirstname().equals(entity.getFirstname())
                    && p.getLastname().equals(entity.getLastname())
                    && p.getPatronymic().equals(entity.getPatronymic())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean save(Person entity) {
        try {
            personDAO.saveEntity(entity);
            return  true;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
        }
        return false;
    }

    @Override
    public boolean update(Person entity) {
        return false;
    }

    @Override
    public boolean delete(Person entity) {
        return false;
    }


    public List<Person> getAllEntities() {
        return null;
    }

//    @Override
//    public boolean isFindEntity(int id) {
//        return false;
//    }


}
