package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransferToCustomerDao extends Dao implements EntityDao<Transfer>{
    static final Logger log = LoggerFactory.getLogger(TransferToBookmakerDao.class);
    private String INSERT_TRANSFER = "INSERT INTO transfers_to_customers VALUES (id,?,?,?,?)";

    @Override
    public Transfer create(Transfer transfer) throws DaoException {
        try {
            log.debug("Write transfer to database - {}", transfer);
            PreparedStatement statement = getConnection().prepareStatement(INSERT_TRANSFER, PreparedStatement.RETURN_GENERATED_KEYS);
            if (transfer.getSender() == null) {
                statement.setInt(1, 0);
            } else {
                statement.setInt(1, transfer.getSender().getId());
            }
            statement.setDouble(2, transfer.getAmount().getAmount().doubleValue());
            statement.setInt(3, transfer.getRecipient().getId());
            statement.setTimestamp(4, new Timestamp(transfer.getTime().getMillis()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                transfer.setId(id);
                log.debug("Set id - {} to transfer",id);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for insert transfer", e);
        }

        return transfer;
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