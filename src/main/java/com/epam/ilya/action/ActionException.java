package com.epam.ilya.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionException extends Exception {
    private static final Logger LOG = LoggerFactory.getLogger(ActionException.class);

    public ActionException(Exception e) {
        super(e);
        LOG.error("Catch action exception");
    }

    public ActionException(String message, Exception e) {
        super(message, e);
        LOG.error(message,e);
    }
}
