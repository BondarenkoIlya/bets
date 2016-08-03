package com.epam.ilya.servlet;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionFactory;
import com.epam.ilya.action.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Servlet", urlPatterns = "/do/*")
public class Servlet extends HttpServlet {
    private Logger log = LoggerFactory.getLogger(Servlet.class);
    private ActionFactory actionFactory;

    @Override
    public void init() throws ServletException {
        actionFactory= new ActionFactory();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionName = req.getMethod() + req.getPathInfo();
        log.info("Action name - "+ actionName);
        Action action = actionFactory.getAction(actionName);
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
            return;
        }
        log.debug("{} init by key: '{}'", action.getClass().getSimpleName(), actionName);
        ActionResult result = null;
        try {
            result = action.execute(req, resp);
            log.debug("Action result view: {}. Redirect: {}", result.getView(), result.isRedirect());
        } catch (ActionException e) {
            throw new ServletException("Cannot execute action",e);
        }
        doForwardOrRedirect(result,req,resp);

    }

    private void doForwardOrRedirect(ActionResult result, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (result.isRedirect()){
            String location = req.getContextPath() + "/do/" + result.getView();
            log.info("Location for 'redirect' - "+ location);
            resp.sendRedirect(location);
        } else {
            String path = String.format("/WEB-INF/jsp/" + result.getView() + ".jsp");
            log.info("Path for 'forward' - "+ path);
            req.getRequestDispatcher(path).forward(req, resp);
        }
    }
}
