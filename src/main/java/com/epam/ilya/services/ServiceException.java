package com.epam.ilya.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceException extends Exception {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceException.class);

    public ServiceException(Exception e) {
        super(e);
        LOG.error("Catch service exception",e);
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
        LOG.error(message,e);
    }

}
