package com.epam.ilya.dao;

import com.epam.ilya.connection.ConnectionPool;
import com.epam.ilya.connection.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory {
    public static final Logger log = LoggerFactory.getLogger(DaoFactory.class);
    private ConnectionPool connectionPool;
    private Connection connection = null;

    public DaoFactory() {
        connectionPool = ConnectionPool.getInstance();
    }

    public <T extends Dao> T getDao(Class<T> clazz) throws DaoException {
        if (connection == null){
            try {
                connection = connectionPool.getConnection();
            } catch (ConnectionPoolException e) {
                throw new DaoException("Cannot get connection from pool", e);
            }
        }
        T t = null;
        try {
            t = clazz.newInstance();
            t.enterConnectionToDao(connection);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new DaoException("Cannot make new instance of Dao", e);
        }
        return t;
    }

    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            log.debug("Transaction starts. Transaction level - {}", Connection.TRANSACTION_SERIALIZABLE);
        } catch (SQLException e) {
            throw new DaoException("Cannot not start transaction", e);
        }
    }

    public void commitTransaction() throws DaoException {
        try {
            connection.commit();
            connectionPool.closeConnection(connection);
            log.debug("Commit transaction changes");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Cannot not commit transaction transaction", e);
        }
    }

    public void rollbackTransaction() throws DaoException {
        try {
            connection.rollback();
            connectionPool.closeConnection(connection);
            log.debug("Rollback transaction changes");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Cannot not rollback transaction changes", e);
        }
    }

    public void closeConnection() throws DaoException {

        try {
            connectionPool.closeConnection(connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Cannot not close connection", e);
        }
    }

}
