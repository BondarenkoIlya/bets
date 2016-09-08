package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.DaoException;

public interface EntityDao<T> {
    T create(T t) throws DaoException;// сделать их boolean для возрата результата операции

    T findById(int id) throws DaoException;

    void update(T t) throws DaoException;

    void delete(T t) throws DaoException;
}
