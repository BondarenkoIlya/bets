package com.epam.ilya.services;

import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Condition;
import com.epam.ilya.model.Customer;
import com.epam.ilya.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchService {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(MatchService.class));

    public void addConditionToMatch(Condition condition , Match match){
        match.addCondition(condition);
        condition.setMatch(match);
        log.info("Add condition - "+ condition + " to match - "+match);
    }

    public void removeConditionFromMatch(Condition condition){
        Match match = condition.getMatch();
        match.getConditionList().remove(condition);
        log.info("Remove condition - "+ condition + " from match - "+match);
    }

    public void fillResultOfCondition(Condition condition, boolean result){
        condition.setResult(result);
        log.info("Set result '" + result+"' to condition "+condition );
    }

    public void calculateAllBetsResultForCustomer(Match match, Customer customer){
        for (Bet bet:customer.getBets() ) {
            bet.calculateFinalResult();
        }
    }

}
