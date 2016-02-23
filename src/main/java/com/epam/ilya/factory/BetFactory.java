package com.epam.ilya.factory;

import com.epam.ilya.model.ScoreBet;
import com.epam.ilya.model.WinSideBet;
import com.epam.ilya.model.Customer;
import com.epam.ilya.model.Match;
import org.joda.money.Money;

/**
 * Created by Дом on 18.02.2016.
 */
public class BetFactory {
    public ScoreBet createBetByScore(Money value, Match match, int differenceInScore, Customer customer) {
        ScoreBet scoreBet = new ScoreBet(value,match,differenceInScore,customer);
        return scoreBet;
    }

    public WinSideBet createBetByWinSide(Money value, Match match, String nameOfWinSide, Customer customer) {
        WinSideBet winSideBet = new WinSideBet(value, match, nameOfWinSide,customer);

        return winSideBet;
    }
}
