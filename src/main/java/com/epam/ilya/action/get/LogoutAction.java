package com.epam.ilya.action.get;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class deletes logged person(bookmaker or customer) from session and make
 * redirect on welcome page.
 *
 * @author Bondarenko Ilya
 */

public class LogoutAction implements Action {

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String role = req.getParameter("role");
        req.getSession().removeAttribute("bookmaker");
        req.getSession().removeAttribute("loggedCustomer");

        if (role != null) {
            if (role.equals("customer")) {
                req.setAttribute("flash.authorizationError", "customer");
            } else {
                req.setAttribute("flash.authorizationError", "bookmaker");
            }
        }
        return new ActionResult("welcome", true);
    }
}
