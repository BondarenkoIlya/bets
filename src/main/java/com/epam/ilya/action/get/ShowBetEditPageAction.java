package com.epam.ilya.action.get;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Take bet from session or create it if there isn't bet and make recount bet's fields and rewrite current
 * bet in session for showing it on bet-edit page
 *
 */

public class ShowBetEditPageAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Bet bet = (Bet) req.getSession(false).getAttribute("bet");
        bet.calculateFinalCoefficient();
        bet.calculatePossibleGain();
        req.getSession(false).setAttribute("bet", bet);
        return new ActionResult("bet-edit");
    }
}
