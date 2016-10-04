package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Avatar;
import com.epam.ilya.model.Customer;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * Class loads the file and validates it. If the file content type is image and its size is not more than 16Mb then
 * class saves this picture as avatar of logged customer, otherwise it writes a message about an error in attribute.
 *
 * @author Bondarenko Ilya
 */

public class UploadAvatarAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(UploadAvatarAction.class);
    private static final long MAX_IMAGE_SIZE = 16_177_215;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Customer customer = (Customer) req.getSession(false).getAttribute("loggedCustomer");
        PersonService service = new PersonService();
        boolean invalid = false;
        Part avatar;
        try {
            avatar = req.getPart("avatar");
            if (avatar.getSize() <= 0) {
                invalid = true;
                req.setAttribute("flash.avatarError", "empty");
            } else if (avatar.getSize() > MAX_IMAGE_SIZE) {
                req.setAttribute("flash.avatarError", "tooBig");
                invalid = true;
                LOG.warn("Try to upload too big file");
            } else {
                if (avatar.getContentType().equals("image/jpeg")) {
                    Avatar avatarPic = new Avatar();
                    avatarPic.setPicture(avatar.getInputStream());
                    avatarPic.setCreationDate(DateTime.now());
                    service.setAvatarToCustomer(avatarPic, customer);
                    req.getSession(false).setAttribute("loggedCustomer", customer);
                } else {
                    req.setAttribute("flash.avatarError", "notImage");
                }
            }
        } catch (IOException | ServletException e) {
            throw new ActionException("Cannot get part with avatar", e);
        } catch (ServiceException e) {
            throw new ActionException("Cannot set Avatar to customer", e);
        } catch (IllegalStateException e) {
            req.setAttribute("flash.avatarError", "tooBig");
            invalid = true;
            LOG.warn("Try to upload too big file", e);
        }
        if (req.getHeader("Referer").endsWith("cabinet")) {
            invalid = false;
            return new ActionResult("cabinet", true);
        } else {
            if (!invalid) {
                req.setAttribute("flash.registerMessage", "success");
                return new ActionResult("home", true);
            } else {
                invalid = false;
                return new ActionResult("upload/avatar", true);
            }
        }
    }
}
