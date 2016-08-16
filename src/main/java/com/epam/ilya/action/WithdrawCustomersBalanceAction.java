package com.epam.ilya.action;

import com.epam.ilya.model.Customer;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class WithdrawCustomersBalanceAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        PersonService service = new PersonService();
        String parameter = req.getParameter("addToCustomerBalance");
        String id = req.getParameter("customersId");
        Properties properties = new Properties();
        Customer customer = null;
        try {
            customer = service.findById(id);
        } catch (ServiceException e) {
            throw new ActionException("Cannot find customer by id",e);
        }
        try {
            properties.load(RegisterAction.class.getClassLoader().getResourceAsStream("validation.properties"));
        } catch (IOException e) {
            throw new ActionException("Cannot load properties", e);
        }
        if (parameter.matches(properties.getProperty("notEmptyNumber.regex"))) {
            try {
                if (service.withdrawFromTheAccount(customer, Money.of(CurrencyUnit.of("KZT"), Double.parseDouble(parameter)))){
                    req.setAttribute("remove_message", "success");
                }else {
                    req.setAttribute("remove_massage", "remove_error");
                }
            } catch (ServiceException e) {
                throw new ActionException("Cannot deposit on cash account", e);
            }
        } else {
            req.setAttribute("remove_massage", "input_error");
        }
        return new ActionResult("customers-edit");
    }
}
