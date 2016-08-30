package com.epam.ilya.action.get;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Match;
import com.epam.ilya.services.MatchService;
import com.epam.ilya.services.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowMatchesEditionPageAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        MatchService service = new MatchService();

        List<Match> activeMatches;
        List<Match> inactiveMatches;
        try {
            activeMatches = service.getAllActiveMatch();
            inactiveMatches = service.getAllInactiveMatch();
        } catch (ServiceException e) {
            throw new ActionException("Cannot get matches list in action", e);
        }
        req.setAttribute("activeMatches", activeMatches);
        req.setAttribute("inactiveMatches", inactiveMatches);
        return new ActionResult("matches-edit");
    }
}
