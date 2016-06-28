package com.epam.ilya.connection;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionPool {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(ConnectionPool.class));
    private static ConnectionPool instance;// можно и стоит ли делать их volatile
    private String url;
    private String username;
    private String password;
    private int connectionsLimit;
    private int getConnectionTimeout;
    private List<Connection> freeConnections = new ArrayList<>();// можно и стоит ли делать их volatile
    private List<Connection> usedConnections = new ArrayList<>();

    public ConnectionPool() {
        loadDBProperties();
        loadDriver();
        freeConnections = new ArrayList<>(connectionsLimit);
        usedConnections = new ArrayList<>(connectionsLimit);
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
            log.info("Create new instance of connection pool ");
        }
        return instance;
    }

    public synchronized void loadDBProperties() {
        Properties properties = null;
        try {
            properties = new Properties();
            properties.load(ConnectionPool.class.getClassLoader().getResourceAsStream("database.properties"));
            log.info("Load property file with information about DB");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (properties != null) {
            log.info("Set information about DB to instance");
            setUrl(properties.getProperty("url"));
            setUsername(properties.getProperty("username"));
            setPassword(properties.getProperty("password"));
            setConnectionsLimit(Integer.parseInt(properties.getProperty("connections.limit")));
            setGetConnectionTimeout(Integer.parseInt(properties.getProperty("get.connection.timeout")));
        }
    }

    public synchronized void loadDriver() {
        try {
            log.info("Create new driver and register it");
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnection() {
        Connection currentConnection = null;
//        if (freeConnections.isEmpty() && usedConnections.size()==connectionsLimit){
//            //wait написать цикл ожидания который будет крутиться пока свободный конекшн не появится
//        }
        log.info("Free connections: " + freeConnections.size() + " Used connections: " + usedConnections.size());

        while (currentConnection == null) {
            if (freeConnections.isEmpty() && usedConnections.size() < connectionsLimit) {
                log.info("Have not free connections, but used connections not ful ");
                try {

                    currentConnection = DriverManager.getConnection(url, username, password);
                    usedConnections.add(currentConnection);
                    log.info("Create new connection and add it to used connections");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (!freeConnections.isEmpty()) {
                log.info("Have free connection");
                currentConnection = freeConnections.get(0);// потому что все элементы находящиеся правее смещаются в лево
                freeConnections.remove(currentConnection);
                usedConnections.add(currentConnection);
                log.info("Take one of free connection");
            }
        }
        log.info("Free connections: " + freeConnections.size() + " Used connections: " + usedConnections.size());
        return currentConnection;
    }

    public synchronized void closeConnection(Connection connection) {
        try {
            if (!connection.isClosed() && freeConnections.size() != connectionsLimit) {
                log.info("Put connection back to free connections");
                usedConnections.remove(connection);
                freeConnections.add(connection);
                log.info("Free connections: " + freeConnections.size() + " Used connections: " + usedConnections.size());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

    public List<Connection> getFreeConnections() {
        return freeConnections;
    }

    public void setFreeConnections(List<Connection> freeConnections) {
        this.freeConnections = freeConnections;
    }
}