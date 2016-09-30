package com.epam.ilya.action.get;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Match;
import com.epam.ilya.model.PaginatedList;
import com.epam.ilya.services.MatchService;
import com.epam.ilya.services.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class take certain amount of active match with conditions and write it in attribute for customer when
 * he create new bet.
 *
 */

public class ShowAddConditionToBetPageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowAddConditionToBetPageAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        MatchService service = new MatchService();
        PaginatedList<Match> matches;
        String pageNumberParam = req.getParameter("pageNumber");
        int pageSize = 3;
        int pageNumber;
        if (pageNumberParam == null) {
            LOG.debug("Do not get page number parameter. Set page number 1");
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(pageNumberParam);
        }

        try {
            matches = service.getAllActiveMatch(pageNumber, pageSize);
        } catch (ServiceException e) {
            throw new ActionException("Cannot get all matches", e);
        }
        req.setAttribute("matches", matches);

        return new ActionResult("bet-add-condition");
    }
}
