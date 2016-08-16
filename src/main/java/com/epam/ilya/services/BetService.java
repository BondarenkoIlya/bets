package com.epam.ilya.services;

import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Bookmaker;
import com.epam.ilya.model.Condition;
import com.epam.ilya.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetService {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(BetService.class));

    public void addConditionToBet(Condition condition, Bet bet) {
        bet.addCondition(condition);
        log.info("Add condition " + condition + " to bet " + bet);
        bet.calculateFinalCoefficient();
        log.info("Bet's final coefficient become " + bet.getFinalCoefficient());
        bet.calculatePossibleGain();
        log.info("Bet's possible gain is " + bet.getPossibleGain());
    }

    public void putDownBetsResult(Customer customer, Bet bet, Bookmaker bookmaker) {
        if (customer.getBets().contains(bet)) {
            bet.calculateFinalResult();
            log.info("Customer's " + customer.getFirstName() + " bet's " + bet + " result is " + bet.isFinalResult());
            if (bet.isFinalResult()) {
                bookmaker.getPersonsPurse().removeCash(bet.getPossibleGain().minus(bet.getValue()));
                customer.getPersonsPurse().addCash(bet.getPossibleGain());
                log.info("Customer " + customer.getFirstName() + " win: " + bet.getPossibleGain() + ". Customer's balance: " + customer.getPersonsPurse().getBalance());
            } else {
                bookmaker.getPersonsPurse().addCash(bet.getValue());
                log.info("Customer " + customer.getFirstName() + " lose: " + bet.getValue() + ". Customer's balance" + customer.getPersonsPurse().getBalance());
            }
        }
    }


}
