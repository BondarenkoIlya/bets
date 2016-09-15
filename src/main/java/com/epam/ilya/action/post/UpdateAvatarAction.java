package com.epam.ilya.action.post;

import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Avatar;
import com.epam.ilya.model.Customer;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.joda.time.DateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

public class UpdateAvatarAction implements com.epam.ilya.action.Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Customer customer = (Customer) req.getSession(false).getAttribute("loggedCustomer");
        PersonService service = new PersonService();
        try {
            Part avatar = req.getPart("avatar");
            if (avatar!=null){
                Avatar avatarPic = new Avatar();
                avatarPic.setPicture(avatar.getInputStream());
                avatarPic.setCreationDate(DateTime.now());
                service.setAvatarToCustomer(avatarPic,customer);
            }
        } catch (IOException | ServletException e) {
            throw new ActionException("Cannot get part with avatar",e);
        } catch (ServiceException e) {
            throw new ActionException("Cannot set Avatar to customer",e);
        }
        return new ActionResult("cabinet",true);

    }
}
