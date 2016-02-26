package com.epam.ilya.model;

import com.epam.ilya.Runner;
import org.joda.money.Money;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * Created by Дом on 15.02.2016.
 */
public class Service {
    static final Logger log = Logger.getLogger(String.valueOf(Runner.class));

    public void computeBetsResult(Customer customer, Match match) {

        for (Bet bet : customer.getBets()) {
            if (bet.getMatch() == match) {
                if (bet instanceof WinSideBet) {// он оставляет прокастеные ставки в том же массиве BETS
                    WinSideBet winSideBet = (WinSideBet) bet;
                    ((WinSideBet) bet).fillFinalResult();
                    //log.info( String.valueOf(bet.isResult()));//как сделать что он выводил на экран?
                } else if (bet instanceof ScoreBet) {
                    ScoreBet scoreBet = (ScoreBet) bet;
                    ((ScoreBet) bet).fillFinalResult();
                    //log.info(String.valueOf(bet.isResult()));
                }
            } else {
                log.warning("Customer " + customer + " have not " + match);
            }
        }
    }

    public List<Bet> filterBetsResult(Customer customer, String filterType) {//должен возвращать список или выводить на экран ? Если возвращать список то как сделать выбор ? через файнал поля?
        if (filterType == "Win bets") {
            List<Bet> winBets = new ArrayList<>();
            for (Bet bet : customer.getBets()) {
                if (bet.getMatch().getNameOfWinSide() != null) {
                    if (bet.isResult() == true) {
                        winBets.add(bet);
                    }
                }
            }
            return winBets;
        } else if (filterType == "Lose bets") {
            List<Bet> loseBets = new ArrayList<>();
            for (Bet bet : customer.getBets()) {
                if (bet.getMatch().getNameOfWinSide() != null) {
                    if (bet.isResult() == false) {
                        loseBets.add(bet);
                    }
                }
            }
            return loseBets;
        }
    } //обязательно должен вернуть в корне ?

    public static final Comparator<Bet> VALUE_COMPARE = new ValueCompare();

    private static class ValueCompare implements Comparator<Bet> {

        @Override
        public int compare(Bet o1, Bet o2) {
            Money val1 = o1.getValue();
            Money val2 = o2.getValue();
            return val1.compareTo(val2);
        }
    }
}
