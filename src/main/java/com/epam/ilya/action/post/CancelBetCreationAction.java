package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bet;
import com.epam.ilya.services.BetService;
import com.epam.ilya.services.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelBetCreationAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Bet bet = (Bet) req.getSession(false).getAttribute("bet");
        BetService service = new BetService();
        try {
            service.cancelBetCreation(bet);
        } catch (ServiceException e) {
            throw new ActionException("Cannot cancel bet creation", e);
        }
        req.getSession().removeAttribute("bet");
        req.setAttribute("flash.cancelBet", "Customer cancel");
        return new ActionResult("home", true);
    }
}
