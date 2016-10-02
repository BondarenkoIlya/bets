package com.epam.ilya.action.get;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bookmaker;
import com.epam.ilya.model.CashAccount;
import com.epam.ilya.model.Customer;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class updates state of person's balance.
 *
 * @author Bondarenko Ilya
 */

public class RefreshPersonBalanceAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Customer loggedCustomer = (Customer) req.getSession().getAttribute("loggedCustomer");
        Bookmaker bookmaker = (Bookmaker) req.getSession().getAttribute("bookmaker");
        PersonService service = new PersonService();
        CashAccount purse;
        if (bookmaker != null) {
            purse = bookmaker.getPersonsPurse();
            try {
                purse = service.refreshCashAccount(purse);
            } catch (ServiceException e) {
                throw new ActionException("Cannot refresh bookmaker's balance", e);
            }
            bookmaker.setPersonsPurse(purse);
            req.getSession().setAttribute("bookmaker", bookmaker);
        }
        if (loggedCustomer != null) {
            purse = loggedCustomer.getPersonsPurse();
            try {
                purse = service.refreshCashAccount(purse);
            } catch (ServiceException e) {
                throw new ActionException("Cannot refresh customer's balance", e);
            }
            loggedCustomer.setPersonsPurse(purse);
            req.getSession().setAttribute("loggedCustomer", loggedCustomer);
        }
        return new ActionResult(req.getHeader("Referer"), true);
    }
}
