package com.epam.ilya.model;

import com.epam.ilya.Runner;
import com.epam.ilya.exceptions.CashAccountBalanceExceptions;
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
                    WinSideBet winSideBet = (WinSideBet) bet;// или здесь должны проводиться все операции по передачи денег?? сделать ее булеаном ?
                    if (((WinSideBet) bet).setFinalResult()) {
                        bet.getCustomer().getPersonsPurse().addCash(bet.getPossibleGain());
                    }else {
                        Bookmaker.bookmaker.getPersonsPurse().addCash(bet.getValue());
                    }
                    log.info(String.valueOf(bet.isResult()));
                } else if (bet instanceof ScoreBet) {
                    ScoreBet scoreBet = (ScoreBet) bet;
                    if (((ScoreBet) bet).setFinalResult()) {
                        bet.getCustomer().getPersonsPurse().addCash(bet.getPossibleGain());
                    }else {
                        Bookmaker.bookmaker.getPersonsPurse().addCash(bet.getValue());
                    }
                    log.info(String.valueOf(bet.isResult()));
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

    public List<Bet> sort(List<Bet> bets) {
        ArrayList<Bet> betsCopy = new ArrayList<>(bets);
        Collections.sort(betsCopy, Bet.VALUE_ORDER);
        return betsCopy;
    }


    public void showBetsList(List<Bet> bets) {
        if (bets != null) {
            for (Bet bet : bets) {
                log.info(String.valueOf(bet));
            }
        } else {
            log.info("This collection contains no elements");
        }
    }

    public void depositOnPersonsAccount(Person person, Money kzt) {
        person.getPersonsPurse().addCash(kzt);
    }

    public  void withdrawFromTheAccount(Person person, Money kzt){
        try {
            person.getPersonsPurse().removeCash(kzt);
        } catch (CashAccountBalanceExceptions cashAccountBalanceExceptions) {
            cashAccountBalanceExceptions.printStackTrace();
        }
    }

    public void showPersonsBalance(Person person){
        log.info("Person"+person+" has "+person.getPersonsPurse());
    }
}