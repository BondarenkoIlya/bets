package com.epam.ilya.action;

import com.epam.ilya.model.Customer;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterAction implements Action {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(RegisterAction.class));
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        PersonService ps = new PersonService();

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);
        log.info("Get customer {} from view",customer);
        req.getSession(false).setAttribute("loggedCustomer",customer);
        req.setAttribute("successRegister","true");

        try {
            ps.registerCustomer(customer);
        } catch (ServiceException e) {
            throw new ActionException("Register action cannot register",e);
        }

        return new ActionResult("home",true);
    }
}
