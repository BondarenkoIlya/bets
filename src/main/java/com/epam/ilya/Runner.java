package com.epam.ilya;


import com.epam.ilya.factory.BetFactory;
import com.epam.ilya.factory.CustomerFactory;
import com.epam.ilya.factory.MatchFactory;
import com.epam.ilya.model.*;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Дом on 12.02.2016.
 */
public class Runner {
    static final Logger log = Logger.getLogger(String.valueOf(Runner.class));
    static  Service service = new Service();
    static MatchFactory matchFactory = new MatchFactory();
    static BetFactory betFactory = new BetFactory();
    static CustomerFactory customerFactory = new CustomerFactory();


    public static void main(String[] args) {

        Customer customerPetya = customerFactory.createCustomer("Петя");
        Customer customerVasya = customerFactory.createCustomer("Вася");

        Match match = matchFactory.createMatch();

        ScoreBet bs1 = betFactory.createBetByScore(match,customerPetya);
        customerPetya.add(bs1);

        WinSideBet bws1 = betFactory.createBetByWinSide(match,customerPetya);
        customerPetya.add(bws1);

        ScoreBet bs2 = betFactory.createBetByScore(match,customerVasya);
        customerVasya.add(bs2);

        WinSideBet bws2 = betFactory.createBetByWinSide(match,customerVasya);
        customerVasya.add(bws2);

        match.setRandomResults();

        service.computeBetsResult(customerPetya,match);
        service.computeBetsResult(customerVasya,match);

        List<Bet> wonBets = service.filterWonBets(customerPetya);
        service.showBetsList(wonBets);
        service.sort(customerPetya.getBets());
        //c1.getBets().sort(Comparator.comparing(Bet::getPossibleGain));


       // Comparator.comparing(o ->

    /*
     и прикрутить аккаунт
     прикрутить ремув
    */

    }
}