package com.epam.ilya.action.post;

import com.epam.ilya.action.Action;
import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Customer;
import com.epam.ilya.services.BetService;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class checks the bet for completeness. If everything is right then class makes transfer from bet creator to bookmaker and
 * saves the new bet.
 *
 * @author Bondarenko Ilya
 */

public class SaveNewBetAction implements Action {

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        BetService betService = new BetService();
        PersonService personService = new PersonService();
        Bet bet = (Bet) req.getSession(false).getAttribute("bet");
        Customer loggedCustomer = (Customer) req.getSession(false).getAttribute("loggedCustomer");
        if (bet.getConditions().isEmpty()) {
            req.setAttribute("emptyError", "true");
            return new ActionResult("bet-edit");
        }

        try {
            betService.completeBetsCreation(bet);
            personService.replaceBatsValueToBookmaker(loggedCustomer, bet.getValue());
        } catch (ServiceException e) {
            throw new ActionException("Cannot complete bets creation", e);
        }
        req.getSession().removeAttribute("bet");
        req.setAttribute("flash.create_bet_successfully", "true");
        return new ActionResult("bets/active", true);
    }
}
