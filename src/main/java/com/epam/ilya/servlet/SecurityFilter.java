package com.epam.ilya.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "SecurityFilter", urlPatterns = "/do/*")
public class SecurityFilter implements Filter {
    static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);
    List<String> customersPages = new ArrayList<>();
    List<String> bookmakersPages = new ArrayList<>();
    List<String> guestsPages = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        customersPages.add("/home");
        customersPages.add("/bets/");
        customersPages.add("/bet/");
        customersPages.add("/avatar/");
        customersPages.add("/cabinet");
        bookmakersPages.add("/bookmaker/");
        bookmakersPages.add("/customer/");
        bookmakersPages.add("/matches/");
        bookmakersPages.add("/match/");
        guestsPages.add("/welcome");
        guestsPages.add("/register");
        guestsPages.add("/login");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String path = req.getPathInfo();
        log.debug("Servlet path - {}", path);
        for (String customersPagePath : customersPages) {
            if (path.startsWith(customersPagePath)) {
                if (req.getSession().getAttribute("loggedCustomer") != null) {
                    log.debug("Logged in like customer. Have permission to access this page");
                    filterChain.doFilter(req, resp);
                    return;
                } else {
                    log.debug("Do not logged in like customer. Send redirect on logout page");
                    resp.sendRedirect(req.getContextPath() + "/do/logout?role=customer");
                    return;
                }
            }
        }
        for (String bookmakersPagePath : bookmakersPages) {
            if (path.startsWith(bookmakersPagePath)) {
                if (req.getSession().getAttribute("bookmaker") != null) {
                    log.debug("Logged in like bookmaker. Have permission to access this page");
                    filterChain.doFilter(req, resp);
                    return;
                } else {
                    log.debug("Do not logged in like bookmaker. Send redirect on logout page");
                    resp.sendRedirect(req.getContextPath() + "/do/logout?role=bookmaker");
                    return;
                }
            }
        }
        for (String guestPagePath : guestsPages) {
            if (path.startsWith(guestPagePath)) {
                log.debug("Try to access on guest page");
                if (req.getSession().getAttribute("bookmaker") == null && req.getSession().getAttribute("loggedCustomer") == null) {
                    log.debug("Do not logged in like anyone");
                    filterChain.doFilter(req, resp);
                    return;
                } else {
                    log.debug("Logged in like customer or bookmaker. Log out for access.");
                    resp.sendRedirect(req.getContextPath() + "/do/logout");
                    return;
                }
            }
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
