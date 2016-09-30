package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bookmaker;
import com.epam.ilya.model.CashAccount;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * Class add money to bookmaker's balance if amount is correct
 *
 * @author Bondarenko Ilya
 */

public class ReplenishBookmakersBalanceAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        PersonService service = new PersonService();
        String parameter = req.getParameter("addToBookmakerBalance");
        Properties properties = new Properties();
        try {
            properties.load(RegisterAction.class.getClassLoader().getResourceAsStream("validation.properties"));
        } catch (IOException e) {
            throw new ActionException("Cannot load properties", e);
        }
        if (parameter.matches(properties.getProperty("number.regex"))) {
            Bookmaker bookmaker = (Bookmaker) req.getSession().getAttribute("bookmaker");
            try {
                service.replenishPersonsBalance(Money.of(CurrencyUnit.of(CashAccount.CURRENCY), Double.parseDouble(parameter)), bookmaker);
            } catch (ServiceException e) {
                throw new ActionException("Cannot deposit on cash account", e);
            }
            req.getSession(false).setAttribute("bookmaker", bookmaker);
            req.setAttribute("flash.message", "success");
        } else {
            req.setAttribute("flash.message", "error");
        }
        return new ActionResult("bookmaker/home", true);
    }
}
