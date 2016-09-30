package com.epam.ilya.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bondarenko Ilya
 */

public interface Action {
    ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException;
}
