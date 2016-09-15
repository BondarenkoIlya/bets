package com.epam.ilya.action.post;

import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Match;
import com.epam.ilya.services.MatchService;
import com.epam.ilya.services.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelMatchCreationAction implements com.epam.ilya.action.Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Match match  =(Match) req.getSession(false).getAttribute("match");
        MatchService service = new MatchService();

        try {
            service.cancelMatchCreation(match);
        } catch (ServiceException e) {
            throw new ActionException("Cannot cancel match creation",e);
        }
        req.getSession().removeAttribute("match");
        req.setAttribute("flash.cancelMatch","Bookmaker cancel");
        return new ActionResult("matches/edit/active",true);
    }
}
