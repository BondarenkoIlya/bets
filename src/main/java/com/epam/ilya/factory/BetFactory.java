package com.epam.ilya.factory;

import com.epam.ilya.model.Bet;
import org.joda.money.Money;

import java.util.List;

import static java.util.Arrays.asList;


public class BetFactory {
    private List<String> footballTeams = asList("Leicester City","Tottenham Hotspur","Arsenal","Manchester City","West Ham United","Manchester United","Southampton","Stoke City","Liverpool","Watford");

    public Bet createBet(Money value){
        Bet bet = new Bet(value);

        return bet;
    }

}
