package com.epam.ilya.connection;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Class work with all connections to database
 *
 * @author Bondarenko Ilya
 */

public class ConnectionPool {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPool.class);

    private String url;
    private String username;
    private String password;
    private String driver;
    private int connectionsLimit;
    private BlockingQueue<Connection> freeConnections = null;
    private BlockingQueue<Connection> usedConnections = null;


    public ConnectionPool() {
        try {
            loadDBProperties();
            loadDriver();
            init();
        } catch (ConnectionPoolException e) {
            LOG.error("Cannot create new instance of connection pool. ", e);
        }
    }

    /**
     * Method to return always the same entity of connection pool
     *
     * @return new instance of pool
     */
    public static synchronized ConnectionPool getInstance() { // сделать инстенсхолдер
        return InstanceHolder.instance;
    }

    /**
     * Method to get all information about database and connection pool from properties
     *
     * @throws ConnectionPoolException
     */

    private void loadDBProperties() throws ConnectionPoolException {
        Properties properties = new Properties();
        try {
            properties.load(ConnectionPool.class.getClassLoader().getResourceAsStream("database/database.properties"));
            LOG.info("Load property file with information about DB");
        } catch (IOException e) {
            throw new ConnectionPoolException("Cannot load properties", e);
        }
        if (!properties.isEmpty()) {
            LOG.info("Set information about DB to instance");
            setUrl(properties.getProperty("url"));
            setUsername(properties.getProperty("username"));
            setPassword(properties.getProperty("password"));
            setDriver(properties.getProperty("driver"));
            setConnectionsLimit(Integer.parseInt(properties.getProperty("connections.limit")));
        } else {
            LOG.error("Property have not any parameters");
        }
    }


    /**
     * Method for register MySQL driver
     *
     * @throws ConnectionPoolException
     */
    private void loadDriver() throws ConnectionPoolException {
        try {
            LOG.info("Create new driver and register it");
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            throw new ConnectionPoolException("Cannot get driver manager", e);
        }
    }

    /**
     * Method initialize fields and fill connection pool
     *
     * @throws ConnectionPoolException
     */

    private void init() throws ConnectionPoolException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Cannot found class", e);
        }
        freeConnections = new ArrayBlockingQueue<>(connectionsLimit);
        usedConnections = new ArrayBlockingQueue<>(connectionsLimit);
        while (freeConnections.size() != connectionsLimit) {
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                freeConnections.put(connection);
            } catch (SQLException | InterruptedException e) {
                throw new ConnectionPoolException("Cannot get connection or put in the connection list", e);
            }
        }
    }

    /**
     * Method return connection if in free connection's queue there is at least one free connection.
     * Otherwise wait until anyone become free.
     *
     * @return free connection
     * @throws ConnectionPoolException
     */

    public synchronized Connection getConnection() throws ConnectionPoolException {
        Connection currentConnection;
        LOG.info("Free connections: " + freeConnections.size() + " Used connections: " + usedConnections.size());
        try {
            currentConnection = freeConnections.take();
            usedConnections.put(currentConnection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Cannot replace connection", e);
        }
        LOG.info("Free connections: " + freeConnections.size() + " Used connections: " + usedConnections.size());
        return currentConnection;
    }

    /**
     * Replace connection from free connection's queue to used connection's queue
     *
     * @param connection that must to be replace
     * @throws ConnectionPoolException
     */
    public synchronized void closeConnection(Connection connection) throws ConnectionPoolException {
        try {
            usedConnections.remove(connection);
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Cannot replace connection back", e);
        }
        LOG.info("Free connections: " + freeConnections.size() + " Used connections: " + usedConnections.size());
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConnectionsLimit(int connectionsLimit) {
        this.connectionsLimit = connectionsLimit;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method close all connection in connection pool
     *
     * @throws ConnectionPoolException
     */

    public void close() throws ConnectionPoolException {
        closeAllConnectionsInQueue(freeConnections);
        closeAllConnectionsInQueue(usedConnections);

    }

    /**
     * Method close all connections in queue.
     *
     * @param connections that must to be closed
     * @throws ConnectionPoolException
     */

    private void closeAllConnectionsInQueue(BlockingQueue<Connection> connections) throws ConnectionPoolException {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new ConnectionPoolException("Could not close connection", e);
            }
        }
    }

    /**
     * Class for holding instance of.
     *
     * @author Bondarenko Ilya
     */

    public static class InstanceHolder {
        static ConnectionPool instance;

        public static void setInstance(ConnectionPool connectionPool) {
            instance = connectionPool;
        }

    }
}