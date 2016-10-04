package com.epam.ilya.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoException extends Exception {
    private static final Logger LOG = LoggerFactory.getLogger(DaoException.class);

    public DaoException(Exception e) {
        super(e);
        LOG.error("Catch dao exception", e);
    }

    public DaoException(String message, Exception e) {
        super(message, e);
        LOG.error(message, e);
    }
}
