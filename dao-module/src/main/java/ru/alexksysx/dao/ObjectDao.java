package ru.alexksysx.dao;

import java.util.List;

public interface ObjectDao<T> {
    T getOneById(Long id);
    List<T> getAll();
    boolean deleteOneById(Long id);
    boolean updateOne(T object);
    T createOne(T object);
}

