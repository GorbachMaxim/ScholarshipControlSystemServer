package org.example.ServerSemestr5.Service.serviceImpl;

import org.example.ServerSemestr5.DAO.DAOimpl.GroupDaoImpl;
import org.example.ServerSemestr5.DAO.DAOimpl.StudentDaoImpl;
import org.example.ServerSemestr5.Model.entity.Group;
import org.example.ServerSemestr5.Model.entity.Student;
import org.example.ServerSemestr5.Service.Service;
import org.hibernate.HibernateError;

import java.util.List;

public class StudentServiceImpl implements Service<Student> {

    private StudentDaoImpl studentDao;

    public StudentServiceImpl() {
        this.studentDao = new StudentDaoImpl();
    }

    @Override
    public Student searchEntity(Student entity) {
        List<Student> students = studentDao.findAllEntity();
        for (Student s : students) {
            if (s.getIdStudent() == entity.getIdStudent()) {
                return s;
            }
        }
        return entity;
    }

    @Override
    public boolean isFindEntity(Student entity) {
        List<Student> students = studentDao.findAllEntity();

        for (Student s : students) {
            if (s.getIdStudent() == (entity.getIdStudent())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean save(Student entity) {
        try {
            studentDao.saveEntity(entity);
            return true;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
            return false;
        }

    }

    @Override
    public boolean update(Student entity) {
        try {
            if (studentDao.updateEntity(entity))
                return true;
            else
                return false;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
            return false;
        }

    }

    @Override
    public boolean delete(Student entity) {
        try {
            studentDao.deleteEntity(entity);
            return true;
        } catch (HibernateError e) {
            System.out.println("Exception:" + e);
        }
        return false;
    }

    @Override
    public List<Student> getAllEntities() {
        return studentDao.findAllEntity();
    }
}
