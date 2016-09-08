package com.epam.ilya.action.get;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements Action {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(LogoutAction.class));

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String role = req.getParameter("role");
        req.getSession().removeAttribute("bookmaker");
        req.getSession().removeAttribute("loggedCustomer");

        if (role != null) {
            if (role.equals("customer")) {
                req.setAttribute("flash.authorizationError", "customer");
            }else {
                req.setAttribute("flash.authorizationError", "bookmaker");
            }
        }
        return new ActionResult("welcome", true);
    }
}
