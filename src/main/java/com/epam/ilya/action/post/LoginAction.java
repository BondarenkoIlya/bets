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
import java.io.IOException;
import java.util.Properties;

public class LoginAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(LoginAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String email = req.getParameter("login");
        String password = req.getParameter("password");
        LOG.info("Get email- {} and password- {}", email, password);
        Person person;
        PersonService service = new PersonService();
        Properties properties = new Properties();
        try {
            properties.load(LoginAction.class.getClassLoader().getResourceAsStream("validation.properties"));
        } catch (IOException e) {
            throw new ActionException("Cannot get properties", e);
        }
        if (!email.matches(properties.getProperty("email.regex")) || !password.matches(properties.getProperty("password.regex"))) {
            req.setAttribute("loginError", "Invalid Login or Password");
            LOG.info("Wrong login ({}) or password ({})", email, password);
            return new ActionResult("welcome");
        }

        try {
            person = service.performUserLogin(email, password);
            LOG.debug("Get customer - {} to Login action");
        } catch (ServiceException e) {
            throw new ActionException("Cannot get customer from dao", e);
        }

        if (person instanceof Bookmaker) {
            Bookmaker bookmaker = (Bookmaker) person;
            req.getSession(false).setAttribute("bookmaker", bookmaker);
            LOG.info("{} logged in", bookmaker);
            LOG.info("Action result - bookmaker/home redirect");
            return new ActionResult("bookmaker/home", true);
        } else if (person instanceof Customer) {
            Customer customer = (Customer) person;
            req.getSession(false).setAttribute("loggedCustomer", customer);
            LOG.info("{} logged in", customer);
            LOG.info("Action result - home redirect");
            return new ActionResult("home", true);
        } else {
            req.setAttribute("loginError", "Invalid Login or Password");
            LOG.info("Wrong login ({}) or password ({})", email, password);
            LOG.info("Action result - welcome forward");
            return new ActionResult("welcome");
        }
    }
}
