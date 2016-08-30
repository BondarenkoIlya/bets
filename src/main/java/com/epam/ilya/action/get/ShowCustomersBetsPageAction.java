package com.epam.ilya.action.get;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Customer;
import com.epam.ilya.services.BetService;
import com.epam.ilya.services.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCustomersBetsPageAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Customer loggedCustomer = (Customer) req.getSession(false).getAttribute("loggedCustomer");
        BetService service = new BetService();

        List<Bet> activeBets;
        List<Bet> inactiveBets;
        try {
            activeBets = service.getAllActiveCustomersBets(loggedCustomer);
            inactiveBets = service.getAllInactiveCustomersBets(loggedCustomer);
        } catch (ServiceException e) {
            throw new ActionException("Cannot get all active and inactive customer's bets",e);
        }

        req.setAttribute("activeBets",activeBets);
        req.setAttribute("inactiveBets",inactiveBets);
        return new ActionResult("bets");
    }
}
