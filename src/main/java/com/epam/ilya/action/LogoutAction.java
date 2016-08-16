package com.epam.ilya.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements Action {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(LogoutAction.class));

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        return new ActionResult("welcome",true);
    }
}
