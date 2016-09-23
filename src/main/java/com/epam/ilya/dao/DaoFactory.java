package com.epam.ilya.dao;

import com.epam.ilya.connection.ConnectionPool;
import com.epam.ilya.connection.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory implements AutoCloseable {
    public static final Logger log = LoggerFactory.getLogger(DaoFactory.class);
    private ConnectionPool connectionPool;
    private Connection connection = null;

    public DaoFactory() {
        connectionPool = ConnectionPool.getInstance();
        try {
            connection = connectionPool.getConnection();
        } catch (ConnectionPoolException e) {
            log.error("Cannot get connection from pool", e);
        }
    }

    public <T extends Dao> T getDao(Class<T> clazz) throws DaoException {
        T t;
        try {
            t = clazz.newInstance();
            t.setConnection(connection);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new DaoException("Cannot make new instance of Dao", e);
        }
        return t;
    }

    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException("Cannot not start transaction", e);
        }
    }

    public void commitTransaction() throws DaoException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
            log.debug("Commit transaction changes");
        } catch (SQLException e) {
            throw new DaoException("Cannot not commit transaction transaction", e);
        }
    }

    public void rollbackTransaction() throws DaoException {
        try {
            connection.rollback();
            log.debug("Rollback transaction changes");
        } catch (SQLException e) {
            throw new DaoException("Cannot not rollback transaction changes", e);
        }
    }

    @Override
    public void close() throws DaoException {
        try {
            connectionPool.closeConnection(connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Cannot close connection", e);
        }
    }
}
