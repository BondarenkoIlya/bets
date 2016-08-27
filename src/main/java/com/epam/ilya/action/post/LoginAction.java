package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bookmaker;
import com.epam.ilya.model.Customer;
import com.epam.ilya.model.Person;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction implements Action {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(LoginAction.class));

    public LoginAction() {
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String email = req.getParameter("login");
        String password = req.getParameter("password");
        log.info("Get email- {} and password- {}", email, password);
        Person person;
        PersonService service = new PersonService();

        try {
            person = service.performUserLogin(email, password);
            log.debug("Get customer - {} to Login action");
        } catch (ServiceException e) {
            throw new ActionException("Cannot get customer from dao", e);
        }

        if (person instanceof Bookmaker) {
            Bookmaker bookmaker = (Bookmaker) person;
            req.getSession(false).setAttribute("bookmaker", bookmaker);
            log.info("{} logged in", bookmaker);
            log.info("Action result - bookmaker-home redirect");
            return new ActionResult("bookmaker/home", true);
        } else if (person instanceof Customer) {
            Customer customer = (Customer) person;
            req.getSession(false).setAttribute("loggedCustomer", customer);
            log.info("{} logged in", customer);
            log.info("Action result - home redirect");
            return new ActionResult("home", true);
        } else {
            req.setAttribute("loginError", "Invalid Login or Password");
            log.info("Wrong login ({}) or password ({})", email, password);
            log.info("Action result - welcome forward");
            return new ActionResult("welcome");
        }
    }
}
