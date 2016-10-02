package com.epam.ilya.action.get;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Match;
import com.epam.ilya.services.MatchService;
import com.epam.ilya.services.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class writes selected match in attributes for sum-up-result page.
 *
 * @author Bondarenko Ilya
 */

public class ShowSumUpMatchsResultPageAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        MatchService service = new MatchService();
        String id = req.getParameter("id");
        Match match;
        if (id == null) {
            return null;
        }
        try {
            match = service.getMatchById(id);
        } catch (ServiceException e) {
            throw new ActionException("Cannot get match by id", e);
        }
        if (match.getId() == 0) {
            return null;
        }

        req.setAttribute("match", match);
        return new ActionResult("sum-up-result");
    }
}
