package com.epam.ilya.servlet;

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

@WebServlet(name = "ImageServlet", urlPatterns = "/image/*")
public class ImageServlet extends HttpServlet {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(ImageServlet.class));
    private static final int DEFAULT_BUFFER_SIZE = 10240;

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PersonService service = new PersonService();
        Customer loggedCustomer = (Customer) req.getSession(false).getAttribute("loggedCustomer");
        InputStream avatarStream;
        if (loggedCustomer != null) {
            log.debug("Get customer - {} from session to show avatar", loggedCustomer);
            avatarStream = loggedCustomer.getAvatar().getPicture();
        } else {
            String customer_id = req.getParameter("customer_id");
            Customer customer = null;
            try {
                customer = service.findById(customer_id);
            } catch (ServiceException e) {
                throw new ServletException("Cannot find customer by id", e);
            }
            log.debug("Get customer - {} from dao to show avatar", customer);
            avatarStream = customer.getAvatar().getPicture();
        }

        resp.setContentType("image/jpeg");

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
