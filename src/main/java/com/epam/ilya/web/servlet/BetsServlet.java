package com.epam.ilya.web.servlet;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionFactory;
import com.epam.ilya.action.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class handle and doing the necessary work with all application requests and responses except those connected
 * with images
 *
 * @author Bondarenko Ilya
 */

@WebServlet(name = "BetsServlet", urlPatterns = "/do/*")
@MultipartConfig(maxFileSize = 16_177_215)// 16 mb
public class BetsServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(BetsServlet.class);

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionName = req.getMethod() + req.getPathInfo();
        LOG.info("Action name - " + actionName);
        Action action = ActionFactory.getAction(actionName);
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
            return;
        }
        LOG.debug("{} init by key: '{}'", action.getClass().getSimpleName(), actionName);
        ActionResult result;
        try {
            result = action.execute(req, resp);
            LOG.debug("Action result view: {}. Redirect: {}", result.getView(), result.isRedirect());
        } catch (ActionException e) {
            throw new ServletException("Cannot execute action", e);
        }
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        doForwardOrRedirect(result, req, resp);

    }

    private void doForwardOrRedirect(ActionResult result, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (result.isRedirect()) {
            String location = req.getContextPath() + "/do/" + result.getView();
            if (result.getView().startsWith("http://")) {
                location = result.getView();
            }
            LOG.info("Location for 'redirect' - " + location);
            resp.sendRedirect(location);
        } else {
            String path = String.format("/WEB-INF/jsp/" + result.getView() + ".jsp");
            LOG.info("Path for 'forward' - " + path);
            req.getRequestDispatcher(path).forward(req, resp);
        }
    }
}

