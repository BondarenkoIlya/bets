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
 * Class takes certain amount of inactive matches and sets it in attribute for inactive-matches-edit page.
 *
 * @author Bondarenko Ilya
 */

public class ShowInactiveMatchesEditionPageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowInactiveMatchesEditionPageAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        MatchService service = new MatchService();
        PaginatedList<Match> matches;
        String pageNumberParam = req.getParameter("pageNumber");
        int pageSize = 5;
        int pageNumber;
        if (pageNumberParam == null) {
            LOG.debug("Do not get page number parameter. Set page number 1");
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(pageNumberParam);
        }
        try {
            matches = service.getAllInactiveMatch(pageNumber, pageSize);
            LOG.debug("Get paginated list of matches with {} pageNumber, {} pageCount, {} page size and size - {}", matches.getPageNumber(), matches.getPageCount(), matches.getPageSize(), matches.size());
        } catch (ServiceException e) {
            throw new ActionException("Cannot get matches list in action", e);
        }
        req.setAttribute("inactiveMatches", matches);
        return new ActionResult("inactive-matches-edit");
    }
}
