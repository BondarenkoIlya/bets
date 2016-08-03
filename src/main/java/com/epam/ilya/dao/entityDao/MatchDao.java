package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Match;

public class MatchDao extends Dao implements EntityDao<Match>{
    @Override
    public Match create(Match match) throws DaoException {
        return null;
    }

    @Override
    public Match findById(int id) throws DaoException {
        return null;
    }

    @Override
    public void update(Match match) throws DaoException {

    }

    @Override
    public void delete(Match match) throws DaoException {

    }
}
