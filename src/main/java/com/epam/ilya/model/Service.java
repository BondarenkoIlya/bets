package com.epam.ilya.model;

import com.epam.ilya.Runner;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Supplier;

/**
 * Created by Дом on 15.02.2016.
 */
public class Service {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(Runner.class));

    public void computeBetsResult(Customer customer, Match match) {

        for (Bet bet : customer.getBets()) {
            if (bet.getMatch() == match) {
                if (bet instanceof WinSideBet) {
                    WinSideBet winSideBet = (WinSideBet) bet;
                    ((WinSideBet) bet).setFinalResult();
                    log.info( String.valueOf(bet.isResult()));
                } else if (bet instanceof ScoreBet) {
                    ScoreBet scoreBet = (ScoreBet) bet;
                    ((ScoreBet) bet).setFinalResult();

                }
            } else {
                log.error("Customer " + customer + " have not " + match);
            }
        }
    }

    public List<Bet> filterWonBets(Customer customer) {
            List<Bet> winBets = new ArrayList<>();
            for (Bet bet : customer.getBets()) {
                if (bet.getMatch().getNameOfWinSide() != null) {
                    if (bet.isResult()) {
                        winBets.add(bet);
                    }
                }
            }
            return winBets;
    }

   public List<Bet> sort (List<Bet> bets ){
       ArrayList<Bet> betsCopy = new ArrayList<>(bets);
       Collections.sort(betsCopy,Bet.VALUE_ORDER);
       return betsCopy;
   }


    public void showBetsList(List<Bet> bets) {
        for (Bet bet:bets) {
            log.info(String.valueOf(bet));
        }
    }
}
