package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Customer;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FindCustomersAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        PersonService service = new PersonService();
        String parameter = req.getParameter("search");
        List<Customer> customers = null;
        try {
            customers = service.searchByParameter(parameter);
        } catch (ServiceException e) {
            throw new ActionException("Cannot find by parameter", e);
        }
        if (customers.size()==0) {
            req.setAttribute("searchError", true);
        }
        req.setAttribute("customers", customers);
        return new ActionResult("bookmaker/home");
    }
}
