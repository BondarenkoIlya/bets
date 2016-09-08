package com.epam.ilya.dao;

import java.sql.Connection;

public abstract class Dao {
    private Connection connection;
    public static final boolean ACTIVE = true;
    public static final boolean INACTIVE = false;

    public Dao() {
    }

    public void enterConnectionToDao(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


}
