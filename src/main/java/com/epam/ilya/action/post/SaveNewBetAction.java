package com.epam.ilya.action.post;

import com.epam.ilya.action.ActionException;
import com.epam.ilya.action.ActionResult;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Bookmaker;
import com.epam.ilya.model.Customer;
import com.epam.ilya.services.BetService;
import com.epam.ilya.services.PersonService;
import com.epam.ilya.services.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveNewBetAction implements com.epam.ilya.action.Action {
    static final Logger log = LoggerFactory.getLogger(SaveNewBetAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        BetService betService = new BetService();
        PersonService personService = new PersonService();
        Bet bet = (Bet) req.getSession(false).getAttribute("bet");
        Customer loggedCustomer = (Customer) req.getSession(false).getAttribute("loggedCustomer");
        if (bet.getConditions().isEmpty()){
            req.setAttribute("flash.emptyError","true");
            return new ActionResult("bet/edit", true);
        }

        try {
            Bookmaker bookmaker = (Bookmaker) personService.performUserLogin("qwe@mail.ru", "1234567");
            betService.completeBetsCreation(bet);
            log.debug("Logged customer's balance is - {}", loggedCustomer.getPersonsPurse().getBalance());
            personService.replaceBatsValueToBookmaker(loggedCustomer, bet.getValue(), bookmaker);
        } catch (ServiceException e) {
            throw new ActionException("Cannot complete bets creation", e);
        }
        req.getSession().removeAttribute("bet");
        req.setAttribute("flash.create_bet_successfully", "true");
        return new ActionResult("bets/active", true);
    }
}
