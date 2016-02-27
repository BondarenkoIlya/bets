package com.epam.ilya.factory;

import com.epam.ilya.model.ScoreBet;
import com.epam.ilya.model.WinSideBet;
import com.epam.ilya.model.Customer;
import com.epam.ilya.model.Match;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by Дом on 18.02.2016.
 */
public class BetFactory {
    private List<String> footballTeams = asList("Leicester City","Tottenham Hotspur","Arsenal","Manchester City","West Ham United","Manchester United","Southampton","Stoke City","Liverpool","Watford");

    public ScoreBet createBetByScore(Match match, Customer customer) {
        Money value = Money.of(CurrencyUnit.of("KZT"), ((int)(Math.random()*10)+1)*100);
        int differenceInScore = (int) (Math.random() * 6);
        return new ScoreBet(value,match,differenceInScore,customer);
    }

    public WinSideBet createBetByWinSide( Match match, Customer customer) {
        Money value = Money.of(CurrencyUnit.of("KZT"), ((int)(Math.random()*10)+1)*100);
        String nameOfWinSide = footballTeams.get((int) ((Math.random() * 10)));
        return new WinSideBet(value, match, nameOfWinSide,customer);
    }
}
