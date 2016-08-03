package com.epam.ilya.action;

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
        log.info("Get email- {} and password- {}",email,password);
        Person person;

        PersonService service = new PersonService();
        Customer customer = null;
        try {
            customer = (Customer) service.performUserLogin(email, password);
        } catch (ServiceException e) {
            throw new ActionException("Cannot get customer from dao",e);
        }

        if (email.equals(Bookmaker.bookmaker.getEmail()) && password.equals(Bookmaker.bookmaker.getPassword())) {
            req.getSession(false).setAttribute("bookmaker",Bookmaker.bookmaker);
            log.info("{} logged in", Bookmaker.bookmaker);
            return new ActionResult("bookmaker-home",true);
        }else if (customer!=null){
            req.getSession(false).setAttribute("loggedCustomer", customer);
            log.info("{} logged in", customer);
            return new ActionResult("home", true);
        } else {
            req.setAttribute("loginError", "Invalid Login or Password");
            log.info("Wrong login ({}) or password ({})", email, password);
            return new ActionResult("welcome");
        }
    }
}
