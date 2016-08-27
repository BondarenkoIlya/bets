package com.epam.ilya.action.get;

import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bet;
import com.epam.ilya.services.MatchService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowBetEditPageAction implements com.epam.ilya.action.Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        MatchService service = new MatchService();
        Bet bet = (Bet) req.getSession(false).getAttribute("bet");
        bet.calculateFinalCoefficient();
        bet.calculatePossibleGain();
        req.getSession(false).setAttribute("bet",bet);
        return new ActionResult("bet-edit");
    }
}
