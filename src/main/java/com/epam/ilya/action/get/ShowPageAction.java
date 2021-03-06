package com.epam.ilya.action.get;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class shows the page sent in constructor.
 *
 * @author Bondarenko Ilya
 */

public class ShowPageAction implements Action {
    private ActionResult result;

    public ShowPageAction(String page) {
        result = new ActionResult(page);
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        return result;
    }
}
