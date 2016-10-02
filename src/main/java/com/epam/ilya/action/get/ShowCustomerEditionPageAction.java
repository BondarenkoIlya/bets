package com.epam.ilya.action.get;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Customer;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class shows selected customer's customer-edit page for edition of the account by bookmaker.
 *
 * @author Bondarenko Ilya
 */

public class ShowCustomerEditionPageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowCustomerEditionPageAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String id = req.getParameter("id");
        PersonService service = new PersonService();
        Customer customer;
        if (id == null) {
            return null;
        }
        try {
            customer = service.findById(id);
            LOG.info("Get current customer - {} with purse - {}", customer, customer.getPersonsPurse());
        } catch (ServiceException e) {
            throw new ActionException("Cannot find by id", e);
        }
        if (customer.getId() == 0) {
            return null;
        }
        req.setAttribute("customer", customer);
        return new ActionResult("customer-edit");
    }
}
