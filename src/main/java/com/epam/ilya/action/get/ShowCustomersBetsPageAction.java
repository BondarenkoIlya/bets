package com.epam.ilya.action.get;

import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Customer;
import com.epam.ilya.services.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowCustomersBetsPageAction implements com.epam.ilya.action.Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Customer loggedCustomer = (Customer) req.getSession(false).getAttribute("loggedCustomer");
        PersonService service = new PersonService();

        //List<Bet> activeBets= service.getAllActiveBets(loggedCustomer);
        //List<Bet> inactiveBets= service.getAllInactiveBets(loggedCustomer);
        return new ActionResult("bets");
    }
}
