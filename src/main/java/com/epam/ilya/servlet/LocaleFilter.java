package com.epam.ilya.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "LocaleFilter", urlPatterns = "/do/*")
public class LocaleFilter implements Filter {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(LocaleFilter.class));
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("locale")) {
                Locale locale = new Locale(cookie.getValue());
                log.debug("Create new locale - {} and change on current language",locale);
                Config.set(req.getSession(),Config.FMT_LOCALE,locale);
            }
        }
        filterChain.doFilter(req,resp);
    }



    @Override
    public void destroy() {

    }
}