package com.epam.ilya.services;

import com.epam.ilya.model.Bet;
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

    public void putDownBetsResult(Customer customer, Bet bet) {
        if (customer.getBets().contains(bet)) {
            bet.calculateFinalResult();
            log.info("Customer's " + customer.getName() + " bet's " + bet + " result is " + bet.isFinalResult());
            if (bet.isFinalResult()) {
                bet.removeGainToCustomer();
                log.info("Customer " + customer.getName() + " win: " + bet.getPossibleGain() + ". Customer's balance: " + customer.getPersonsPurse().getBalance());
            } else {
                bet.removeMoneyToBookmaker();
                log.info("Customer " + customer.getName() + " lose: " + bet.getValue() + ". Customer's balance" + customer.getPersonsPurse().getBalance());
            }
        }
    }


}
