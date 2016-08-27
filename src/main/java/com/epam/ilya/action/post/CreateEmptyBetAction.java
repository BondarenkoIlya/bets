package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Customer;
import com.epam.ilya.services.BetService;
import com.epam.ilya.services.ServiceException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class CreateEmptyBetAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        BetService service = new BetService();
        Properties properties = new Properties();
        String parameter = req.getParameter("value");
        Customer loggedCustomer = (Customer) req.getSession(false).getAttribute("loggedCustomer");

        try {
            properties.load(CreateEmptyBetAction.class.getClassLoader().getResourceAsStream("validation.properties"));
        } catch (IOException e) {
            throw new ActionException("Cannot load properties for validation money", e);
        }

        if (parameter.matches(properties.getProperty("notEmptyNumber.regex"))) {
            Money value = Money.of(CurrencyUnit.of("KZT"), Double.parseDouble(parameter));
            if (loggedCustomer.getPersonsPurse().balanceAvailabilityFor(value)) {
                Bet bet = new Bet();
                bet.setCustomer(loggedCustomer);
                bet.setValue(value);
                bet.calculateFinalCoefficient();
                bet.calculatePossibleGain();
                try {
                    service.registerCustomersBet(bet,loggedCustomer);
                } catch (ServiceException e) {
                    throw new ActionException("cannot register bet",e);
                }
                req.getSession(false).setAttribute("bet", bet);
                return new ActionResult("bet/edit", true);
            } else {
                req.setAttribute("valueError", "notAvailable");
                return new ActionResult("create-bet");
            }
        } else {
            req.setAttribute("valueError", "incorrect");
            return new ActionResult("create-bet");
        }
    }
}
