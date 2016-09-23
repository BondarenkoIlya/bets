package com.epam.ilya.web.listener;

import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Match;
import com.epam.ilya.services.BetService;
import com.epam.ilya.services.MatchService;
import com.epam.ilya.services.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger LOG = LoggerFactory.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        Bet bet = (Bet) httpSessionEvent.getSession().getAttribute("bet");
        if (bet != null) {
            BetService betService = new BetService();
            try {
                betService.cancelBetCreation(bet);
            } catch (ServiceException e) {
                LOG.error("Cannot cancel bet creation after sessions invalidating", e);
            }
        }
        Match match = (Match) httpSessionEvent.getSession().getAttribute("match");
        if (match != null) {
            MatchService matchService = new MatchService();
            try {
                matchService.cancelMatchCreation(match);
            } catch (ServiceException e) {
                LOG.error("Cannot cancel match creation after session invalidating ", e);
            }
        }
    }
}
