package com.epam.ilya.action;

public class ActionException extends Exception {
    public ActionException(Exception e) {
        super(e);
    }

    public ActionException(String message, Exception e) {
        super(message, e);
    }
}
