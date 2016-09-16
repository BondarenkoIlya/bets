package com.epam.ilya.servlet;

import com.epam.ilya.connection.ConnectionPool;
import com.epam.ilya.connection.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebAppListener implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(WebAppListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ConnectionPool pool = new ConnectionPool();
        log.info("Create new singleton instance of connection pool");
        ConnectionPool.InstanceHolder.setInstance(pool);
        servletContext.setAttribute("pool", pool);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ConnectionPool pool = (ConnectionPool) servletContext.getAttribute("pool");
        try {
            pool.close();
        } catch (ConnectionPoolException e) {
            log.error("Cannot close all connection in pool", e);
        }

    }
}
