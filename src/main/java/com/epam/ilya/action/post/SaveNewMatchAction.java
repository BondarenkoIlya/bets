package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Match;
import com.epam.ilya.services.MatchService;
import com.epam.ilya.services.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class check match for completeness and in case everything right save new match and write message for bookmaker
 * about success creation
 *
 * @author Bondarenko Ilya
 */

public class SaveNewMatchAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Match match = (Match) req.getSession(false).getAttribute("match");
        MatchService service = new MatchService();
        if (match.getConditionList().isEmpty()) {
            req.setAttribute("flash.emptyError", "true");
            return new ActionResult("match/new/edit", true);
        }
        try {
            service.completeMatchsCreation(match);
        } catch (ServiceException e) {
            throw new ActionException("Cannot add conditions to match", e);
        }
        req.getSession(false).removeAttribute("match");
        req.getSession(false).setAttribute("flash.successAddMatch", true);
        return new ActionResult("matches/edit/active", true);
    }
}
