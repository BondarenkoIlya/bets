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

public class ConnectionPool {
    private static final Logger log = LoggerFactory.getLogger(String.valueOf(ConnectionPool.class));
    private String url;
    private String username;
    private String password;
    private int connectionsLimit;
    private int getConnectionTimeout;
    private BlockingQueue<Connection> freeConnections=null;
    private BlockingQueue<Connection> usedConnections=null;


    public ConnectionPool(){
        try {
            loadDBProperties();
            loadDriver();
            init();
        } catch (ConnectionPoolException e) {
            log.error("Cannot create new instance of connection pool. ",e);
        }
    }

    public static synchronized ConnectionPool getInstance() { // сделать инстенсхолдер
        return InstanceHolder.instance;
    }

    private void loadDBProperties() throws ConnectionPoolException {
        Properties properties = null;
        try {
            properties = new Properties();
            properties.load(ConnectionPool.class.getClassLoader().getResourceAsStream("database.properties"));
            log.info("Load property file with information about DB");
        } catch (IOException e) {
            throw new ConnectionPoolException("Cannot load properties",e);
        }
        if (!properties.isEmpty()) {
            log.info("Set information about DB to instance");
            setUrl(properties.getProperty("url"));
            setUsername(properties.getProperty("username"));
            setPassword(properties.getProperty("password"));
            setConnectionsLimit(Integer.parseInt(properties.getProperty("connections.limit")));
            setGetConnectionTimeout(Integer.parseInt(properties.getProperty("get.connection.timeout")));
        }else {
            log.error("Property have not any parameters");
        }
    }

    private void loadDriver() throws ConnectionPoolException {
        try {
            log.info("Create new driver and register it");
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            throw new ConnectionPoolException("Cannot get driver manager",e);
        }
    }

    private void init() throws ConnectionPoolException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Cannot found class",e);
        }
        freeConnections = new ArrayBlockingQueue<Connection>(connectionsLimit);
        usedConnections = new ArrayBlockingQueue<Connection>(connectionsLimit);
        while (freeConnections.size() != connectionsLimit) {
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                freeConnections.put(connection);
            } catch (SQLException | InterruptedException e) {
                throw new ConnectionPoolException("Cannot get connection or put in the connection list",e);
            }
        }
    }

    public synchronized Connection getConnection() throws ConnectionPoolException {
        Connection currentConnection = null;
        log.info("Free connections: " + freeConnections.size() + " Used connections: " + usedConnections.size());
        try {
            currentConnection = freeConnections.take();
            usedConnections.put(currentConnection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Cannot replace connection",e);
        }
        log.info("Free connections: " + freeConnections.size() + " Used connections: " + usedConnections.size());
        return currentConnection;
    }

    public synchronized void closeConnection(Connection connection) throws ConnectionPoolException {
        try {
            usedConnections.remove(connection);
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Cannot replace connection back",e);
        }
        log.info("Free connections: " + freeConnections.size() + " Used connections: " + usedConnections.size());
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

    public int getConnectionsLimit() {
        return connectionsLimit;
    }

    public void setConnectionsLimit(int connectionsLimit) {
        this.connectionsLimit = connectionsLimit;
    }

    public int getGetConnectionTimeout() {
        return getConnectionTimeout;
    }

    public void setGetConnectionTimeout(int getConnectionTimeout) {
        this.getConnectionTimeout = getConnectionTimeout;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void close() throws ConnectionPoolException {
        closeAllConnectionsInQueue(freeConnections);
        closeAllConnectionsInQueue(usedConnections);

    }

    private void closeAllConnectionsInQueue(BlockingQueue<Connection> connections) throws ConnectionPoolException {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new ConnectionPoolException("Could not close connection", e);
            }
        }
    }
    public static class InstanceHolder {
        static ConnectionPool instance ;

        public static void setInstance(ConnectionPool connectionPool){
            instance=connectionPool;
        }

    }
}