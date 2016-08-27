package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Customer;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterAction implements Action {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(RegisterAction.class));
    private boolean invalid = false;
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        PersonService service = new PersonService();
        Customer customer = new Customer();
        Properties properties = new Properties();


        try {
            properties.load(RegisterAction.class.getClassLoader().getResourceAsStream("validation.properties"));
        } catch (IOException e) {
            throw new ActionException("Cannot load properties",e);
        }

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");
        try {
            if (!service.checkEmailAvailable(email)){
                req.setAttribute("emailError","busy");
                invalid=true;
            }  else {
                checkParameterBeRegex(email,"email",properties.getProperty("email.regex"),req);
            }
        } catch (ServiceException e) {
            throw new ActionException("Cannot check email available",e);
        }
        checkParameterBeRegex(firstName,"firstName",properties.getProperty("notEmptyText.regex"),req);
        checkParameterBeRegex(lastName,"lastName",properties.getProperty("notEmptyText.regex"),req);
        if (!password.equals(repeatPassword)){
            req.setAttribute("passwordError","wrong repeat");
        }else {
            checkParameterBeRegex(password,"password",properties.getProperty("password.regex"),req);
        }
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);
        log.info("Get customer {} from view",customer);
        if (invalid){
            invalid=false;
            return new ActionResult("register");
        }else {
            try {
                service.registerCustomer(customer);
            } catch (ServiceException e) {
                throw new ActionException("Register action cannot register", e);
            }
            req.getSession(false).setAttribute("loggedCustomer", customer);
            req.setAttribute("flash.message","success");
            return new ActionResult("home", true);
        }
    }

    private void checkParameterBeRegex(String parameter, String parameterName, String regex,HttpServletRequest req) {
        log.debug("Check parameter '{}' with value '{}' by regex '{}'",parameterName,parameter,regex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parameter);
        if (!matcher.matches()){
            log.debug("Parameter '{}' with value '{}' is unsuitable.",parameterName,parameter);
            req.setAttribute(parameterName+"Error","true");
            invalid=true;
        }
    }
}
