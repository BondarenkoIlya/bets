package com.epam.ilya.web.servlet;

import com.epam.ilya.model.Avatar;
import com.epam.ilya.model.Customer;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class handles and does the necessary work with all image's requests and responses.
 *
 * @author Bondarenko Ilya
 */

@WebServlet(name = "ImageServlet", urlPatterns = "/image/*")
public class ImageServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ImageServlet.class);
    private static final int DEFAULT_BUFFER_SIZE = 10240;

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PersonService service = new PersonService();
        Customer loggedCustomer = (Customer) req.getSession(false).getAttribute("loggedCustomer");
        InputStream avatarStream;
        LOG.info("Work with avatar images");
        if (loggedCustomer != null) {
            LOG.debug("Get customer - {} from session to show avatar", loggedCustomer);
            try {
                long modifyDate = req.getDateHeader("If-Modified-Since");
                LOG.debug("Header 'if-modified-since' contain - {} date", modifyDate);
                Avatar avatar = service.getCustomersAvatar(loggedCustomer, modifyDate);
                if (avatar == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_MODIFIED);
                    return;
                } else {
                    avatarStream = avatar.getPicture();
                    long lastModified = avatar.getCreationDate().getMillis();
                    LOG.debug("Set - {} date to Last-Modified header", lastModified);
                    resp.setDateHeader("Last-Modified", lastModified);
                }
            } catch (ServiceException e) {
                LOG.error("Cannot get customer's avatar", e);
                throw new ServletException("Cannot get customer's avatar", e);
            }

        } else {
            String customer_id = req.getParameter("customer_id");
            Customer customer;
            try {
                customer = service.findById(customer_id);
            } catch (ServiceException e) {
                LOG.error("Cannot find customer by id", e);
                throw new ServletException("Cannot find customer by id", e);
            }
            LOG.debug("Get customer - {} from dao to show avatar", customer);
            avatarStream = customer.getAvatar().getPicture();
        }
        resp.setContentType("image/jpeg");
        if (avatarStream != null) {
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(avatarStream, DEFAULT_BUFFER_SIZE);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(resp.getOutputStream(), DEFAULT_BUFFER_SIZE)) {

                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int length;
                while ((length = bufferedInputStream.read(buffer)) > 0) {
                    bufferedOutputStream.write(buffer, 0, length);
                }
            }
        }
    }


}
