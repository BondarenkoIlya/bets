package com.epam.ilya.connection;

public class ConnectionPoolException extends Exception {
    public ConnectionPoolException(String message, Exception e) {
        super(message, e);
    }

    public ConnectionPoolException(Exception e) {
        super(e);
    }
}
