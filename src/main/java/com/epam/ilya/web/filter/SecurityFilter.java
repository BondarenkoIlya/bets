package com.epam.ilya.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class-filter for work with person's authentication.
 *
 * @author Bondarenko Ilya
 */

@WebFilter(filterName = "SecurityFilter", urlPatterns = "/do/*")
public class SecurityFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);

    private static final List<String> CUSTOMERS_PAGES = new ArrayList<>();
    private static final List<String> BOOKMAKERS_PAGES = new ArrayList<>();
    private static final List<String> GUESTS_PAGES = new ArrayList<>();

    /**
     * Static block to create an access lists for application's users
     */
    static {
        CUSTOMERS_PAGES.add("/home");
        CUSTOMERS_PAGES.add("/bets/");
        CUSTOMERS_PAGES.add("/bet/");
        CUSTOMERS_PAGES.add("/avatar/");
        CUSTOMERS_PAGES.add("/cabinet");
        BOOKMAKERS_PAGES.add("/bookmaker/");
        BOOKMAKERS_PAGES.add("/customer/");
        BOOKMAKERS_PAGES.add("/matches/");
        BOOKMAKERS_PAGES.add("/match/");
        GUESTS_PAGES.add("/welcome");
        GUESTS_PAGES.add("/register");
        GUESTS_PAGES.add("/login");
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Method checks person's authentication.
     *
     * @param servletRequest  request that come from view
     * @param servletResponse response that go to view
     * @param filterChain     parameter for work with next filters
     * @throws IOException
     * @throws ServletException
     */

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String path = req.getPathInfo();
        LOG.debug("Servlet path - {}", path);
        for (String customersPagePath : CUSTOMERS_PAGES) {
            if (path.startsWith(customersPagePath)) {
                if (req.getSession().getAttribute("loggedCustomer") != null) {
                    LOG.debug("Logged in like customer. Have permission to access this page");
                    filterChain.doFilter(req, resp);
                    return;
                } else {
                    LOG.debug("Do not logged in like customer. Send redirect on logout page");
                    resp.sendRedirect(req.getContextPath() + "/do/logout?role=customer");
                    return;
                }
            }
        }
        for (String bookmakersPagePath : BOOKMAKERS_PAGES) {
            if (path.startsWith(bookmakersPagePath)) {
                if (req.getSession().getAttribute("bookmaker") != null) {
                    LOG.debug("Logged in like bookmaker. Have permission to access this page");
                    filterChain.doFilter(req, resp);
                    return;
                } else {
                    LOG.debug("Do not logged in like bookmaker. Send redirect on logout page");
                    resp.sendRedirect(req.getContextPath() + "/do/logout?role=bookmaker");
                    return;
                }
            }
        }
        for (String guestPagePath : GUESTS_PAGES) {
            if (path.startsWith(guestPagePath)) {
                LOG.debug("Try to access on guest page");
                if (req.getSession().getAttribute("bookmaker") == null && req.getSession().getAttribute("loggedCustomer") == null) {
                    LOG.debug("Do not logged in like anyone");
                    filterChain.doFilter(req, resp);
                    return;
                } else {
                    LOG.debug("Logged in like customer or bookmaker. Log out for access.");
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
