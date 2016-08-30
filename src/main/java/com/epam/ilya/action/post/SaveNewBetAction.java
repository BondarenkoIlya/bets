package com.epam.ilya.action.post;

import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bet;
import com.epam.ilya.services.BetService;
import com.epam.ilya.services.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveNewBetAction implements com.epam.ilya.action.Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        BetService service = new BetService();
        Bet bet = (Bet) req.getSession(false).getAttribute("bet");

        try {
            service.completeBetsCreation(bet);
        } catch (ServiceException e) {
            throw new ActionException("Cannot complete bets creation",e);
        }
        req.getSession().removeAttribute("bet");
        req.setAttribute("flash.create_bet_successfully","true");
        return new ActionResult("bets",true);
    }
}
