package com.epam.ilya.action.get;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Customer;
import com.epam.ilya.model.PaginatedList;
import com.epam.ilya.services.BetService;
import com.epam.ilya.services.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class takes certain amount of inactive customer's bets and writes it in inactive-bets page attributes.
 *
 * @author Bondarenko Ilya
 */

public class ShowCustomersInactiveBetsPageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowCustomersInactiveBetsPageAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Customer loggedCustomer = (Customer) req.getSession(false).getAttribute("loggedCustomer");
        BetService service = new BetService();
        PaginatedList<Bet> inactiveBets;
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
            inactiveBets = service.getAllInactiveCustomersBets(loggedCustomer, pageNumber, pageSize);
            for (Bet bet : inactiveBets) {
                LOG.debug("Active bet contain - {}", bet);
            }
        } catch (ServiceException e) {
            throw new ActionException("Cannot get all active and inactive customer's bets", e);
        }

        req.setAttribute("inactiveBets", inactiveBets);
        return new ActionResult("inactive-bets");
    }
}
