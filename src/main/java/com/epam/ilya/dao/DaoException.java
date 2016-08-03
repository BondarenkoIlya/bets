package com.epam.ilya.dao;

public class DaoException extends Exception {
    public DaoException(Exception e) {
        super(e);
    }

    public DaoException(String message, Exception e) {
        super(message, e);
    }
}
