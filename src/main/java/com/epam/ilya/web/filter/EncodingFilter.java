package com.epam.ilya.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Class-filter for work with view's character encoding.
 *
 * @author Bondarenko Ilya
 */

@WebFilter(filterName = "EncodingFilter", urlPatterns = "/do/*")
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Method sets character encoding to all request.
     *
     * @param servletRequest  request that come from view
     * @param servletResponse response that go to view
     * @param filterChain     parameter for work with next filters
     * @throws IOException
     * @throws ServletException
     */

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
