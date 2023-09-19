package org.example.ServerSemestr5.Utility;


import org.example.ServerSemestr5.Model.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Person.class);
                configuration.addAnnotatedClass(Scholarship.class);
                configuration.addAnnotatedClass(Faculty.class);
                configuration.addAnnotatedClass(Group.class);
                configuration.addAnnotatedClass(Student.class);
                configuration.addAnnotatedClass(Subject.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                System.out.println("Исключение в HibernateUtil! " + e);
            }
        }
        return sessionFactory;
    }
}