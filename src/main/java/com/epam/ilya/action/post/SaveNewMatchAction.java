package com.epam.ilya.action.post;

import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Match;
import com.epam.ilya.services.MatchService;
import com.epam.ilya.services.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveNewMatchAction implements com.epam.ilya.action.Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Match match = (Match) req.getSession(false).getAttribute("match");
        MatchService service = new MatchService();

        try {
            service.completeMatchsCreation(match);
        } catch (ServiceException e) {
            throw new ActionException("Cannot add conditions to match", e);
        }
        req.getSession(false).removeAttribute("match");
        req.getSession(false).setAttribute("flash.successAddMatch", true);
        return new ActionResult("/matches/edit/active", true);
    }
}
