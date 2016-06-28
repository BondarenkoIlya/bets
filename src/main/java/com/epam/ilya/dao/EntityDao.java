package com.epam.ilya.dao;

public interface EntityDao<T> {
    void create(T t);// сделать их boolean для возрата результата операции
    T findById(int id);
    void update(T t);
    void delete(T t);
}
