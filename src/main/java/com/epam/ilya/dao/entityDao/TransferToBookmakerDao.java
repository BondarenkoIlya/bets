package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Transfer;

public class TransferToBookmakerDao extends Dao implements EntityDao<Transfer>{
    @Override
    public Transfer create(Transfer transfer) throws DaoException {
        return null;
    }

    @Override
    public Transfer findById(int id) throws DaoException {
        return null;
    }

    @Override
    public void update(Transfer transfer) throws DaoException {

    }

    @Override
    public void delete(Transfer transfer) throws DaoException {

    }
}
