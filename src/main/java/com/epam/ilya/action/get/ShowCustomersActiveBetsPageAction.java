package com.epam.ilya.action.get;

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

public class ShowCustomersActiveBetsPageAction implements com.epam.ilya.action.Action {
    static final Logger log = LoggerFactory.getLogger(ShowCustomersActiveBetsPageAction.class);
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Customer loggedCustomer = (Customer) req.getSession(false).getAttribute("loggedCustomer");
        BetService service = new BetService();
        PaginatedList<Bet> activeBets;
        String pageNumberParam =req.getParameter("pageNumber");

        int pageSize=5;
        int pageNumber;
        if (pageNumberParam==null){
            log.debug("Do not get page number parameter. Set page number 1");
            pageNumber = 1;
        }else {
            pageNumber = Integer.parseInt(pageNumberParam);
        }
        try {
            activeBets = service.getAllActiveCustomersBets(loggedCustomer,pageNumber,pageSize);
            for (Bet bet: activeBets) {
                log.debug("Active bet contain - {}",bet);
            }
        } catch (ServiceException e) {
            throw new ActionException("Cannot get all active and inactive customer's bets", e);
        }

        req.setAttribute("activeBets", activeBets);
        return  new ActionResult("active-bets");
    }
}
