package com.epam.ilya.dao.entity;

import com.epam.ilya.dao.DaoException;

/**
 * Interface describe behavior of any dao class
 *
 * @author Bondarenko Ilya
 */

public interface EntityDao<T> {
    T create(T t) throws DaoException;

    T findById(int id) throws DaoException;

    void update(T t) throws DaoException;

    void delete(T t) throws DaoException;
}
