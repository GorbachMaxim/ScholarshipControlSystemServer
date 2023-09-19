package org.example.ServerSemestr5.Service;


import org.example.ServerSemestr5.Model.entity.User;

import java.util.List;

public interface Service<T> {
   // boolean SearchEntitiesToOne(T entity);

    T searchEntity(T entity);

    boolean isFindEntity(T entity);

    boolean save(T entity);

    boolean update(T entity);

    boolean delete(T entity);

    public List<T> getAllEntities();

    //boolean isFindEntity(int id);
}
