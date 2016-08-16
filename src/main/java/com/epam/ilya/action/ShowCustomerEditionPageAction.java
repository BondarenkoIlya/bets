package com.epam.ilya.action;

import com.epam.ilya.model.Customer;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowCustomerEditionPageAction implements Action {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(ShowCustomerEditionPageAction.class));

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String id = req.getParameter("id");
        PersonService service = new PersonService();
        Customer customer;
        try {
            customer = service.findById(id);
            log.info("Get current customer - {} with purse - {}",customer,customer.getPersonsPurse());
        } catch (ServiceException e) {
            throw new ActionException("Cannot find by id",e);
        }
        req.setAttribute("customer",customer);
        return new ActionResult("customer-edit");
    }
}
