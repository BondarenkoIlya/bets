package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Condition;

public class ConditionDao extends Dao implements EntityDao<Condition> {
    @Override
    public Condition create(Condition condition) throws DaoException {
        return null;
    }

    @Override
    public Condition findById(int id) throws DaoException {
        return null;
    }

    @Override
    public void update(Condition condition) throws DaoException {

    }

    @Override
    public void delete(Condition condition) throws DaoException {

    }
}
