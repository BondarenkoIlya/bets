package com.epam.ilya.dao.entity;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Class works with transfers to customers.
 *
 * @author Bondarenko Ilya
 */

public class TransferToCustomerDao extends Dao implements EntityDao<Transfer> {
    private static final Logger LOG = LoggerFactory.getLogger(TransferToBookmakerDao.class);
    private static final String INSERT_TRANSFER = "INSERT INTO transfers_to_customers VALUES (id,?,?,?,?)";

    @Override
    public Transfer create(Transfer transfer) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_TRANSFER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            LOG.debug("Write transfer to database - {}", transfer);
            if (transfer.getSender() == null) {
                statement.setNull(1, Types.INTEGER);
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
                LOG.debug("Set id - {} to transfer", id);
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
