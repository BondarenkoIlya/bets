package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Customer;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class ReplenishCustomersBalanceAction implements Action {
    static final Logger log = LoggerFactory.getLogger(ReplenishCustomersBalanceAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        PersonService service = new PersonService();
        String money = req.getParameter("addToCustomerBalance");
        String id = req.getParameter("customersId");
        Customer customer;

        Properties properties = new Properties();
        try {
            properties.load(RegisterAction.class.getClassLoader().getResourceAsStream("validation.properties"));
        } catch (IOException e) {
            throw new ActionException("Cannot load properties", e);
        }
        try {
            customer = service.findById(id);
        } catch (ServiceException e) {
            throw new ActionException("Cannot find customer by id", e);
        }

        if (money.matches(properties.getProperty("notEmptyNumber.regex"))) {
            try {
                service.replenishPersonsBalance(Money.of(CurrencyUnit.of("KZT"), Double.parseDouble(money)), customer);
            } catch (ServiceException e) {
                throw new ActionException("Cannot deposit on cash account", e);
            }
            req.setAttribute("flash.add_message", "success");
        } else {
            req.setAttribute("flash.add_message", "error");
        }
        return new ActionResult("customer/edit?id="+id,true);
    }
}
