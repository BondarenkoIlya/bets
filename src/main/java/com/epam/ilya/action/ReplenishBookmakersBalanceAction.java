package com.epam.ilya.action;

import com.epam.ilya.model.Bookmaker;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

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
        if (parameter.matches(properties.getProperty("notEmptyNumber.regex"))) {
            Bookmaker bookmaker = (Bookmaker) req.getSession().getAttribute("bookmaker");
            try {
                service.depositOnAccount(Money.of(CurrencyUnit.of("KZT"), Double.parseDouble(parameter)),bookmaker);
            } catch (ServiceException e) {
                throw new ActionException("Cannot deposit on cash account", e);
            }
            req.getSession(false).setAttribute("bookmaker", bookmaker);
            req.setAttribute("flash.message","success");
        }else {
            req.setAttribute("flash.massage","error");
        }
        return new ActionResult("bookmaker-home", true);
    }
}
