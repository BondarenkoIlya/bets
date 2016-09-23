package com.epam.ilya.dao;

import java.sql.Connection;

public abstract class Dao {
    public static final boolean ACTIVE = true;
    public static final boolean INACTIVE = false;
    private Connection connection;

    public Dao() {
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


}
