package com.epam.ilya.factory;

import com.epam.ilya.exceptions.CashAccountBalanceExceptions;
import com.epam.ilya.model.Bet;
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

    public Bet createBet(Money value){
        Bet bet = new Bet(value);

        return bet;
    }

}
