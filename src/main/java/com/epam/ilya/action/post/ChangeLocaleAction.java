package com.epam.ilya.action.post;

import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLocaleAction implements com.epam.ilya.action.Action {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(ChangeLocaleAction.class));
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String language = req.getParameter("locale");
        if (language.equals("ru")||language.equals("en")){
            Cookie locale = new Cookie("locale",language);
            log.debug("Create cookie with language for locale - {}",language);
            resp.addCookie(locale);
        }
        return new ActionResult(req.getHeader("Referer"),true);
    }
}
