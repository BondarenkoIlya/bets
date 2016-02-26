package com.epam.ilya;

import com.epam.ilya.compare.SortedBetsByPossibleGain;
import com.epam.ilya.factory.BetFactory;
import com.epam.ilya.factory.CustomerFactory;
import com.epam.ilya.factory.MatchFactory;
import com.epam.ilya.model.*;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Logger;

/**
 * Created by Дом on 12.02.2016.
 */
public class Runner {
    static final Logger log = Logger.getLogger(String.valueOf(Runner.class));
    public static void main(String[] args) {
        Service service = new Service();
        MatchFactory matchFactory = new MatchFactory();
        BetFactory betFactory = new BetFactory();
        CustomerFactory customerFactory = new CustomerFactory();



        Customer customerPetya = customerFactory.createCustomer("Петя");
        Customer customerVasya = customerFactory.createCustomer("Вася");



        Match match = matchFactory.createMatch("Football",new DateTime(2016,8,2,19,20,0) , "Arsenal", 1.6, "Leicester", 2.3, 4.3);

        Money val1 = Money.of(CurrencyUnit.of("KZT"), 400);
        Money val2 = Money.of(CurrencyUnit.of("KZT"), 250);
        Money val3 = Money.of(CurrencyUnit.of("KZT"), 700);
        Money val4 = Money.of(CurrencyUnit.of("KZT"), 600);

        ScoreBet bs1 = betFactory.createBetByScore(val1,match,2,customerPetya);
        customerPetya.getBets().add(bs1);

        WinSideBet bws1 = betFactory.createBetByWinSide(val2, match, "Arsenal", customerPetya);
        customerPetya.getBets().add(bws1);

        ScoreBet bs2 = betFactory.createBetByScore(val3,match,3,customerVasya);
        customerVasya.getBets().add(bs2);

        WinSideBet bws2 = betFactory.createBetByWinSide(val4,match, "Leicester", customerVasya);
        customerVasya.getBets().add(bws2);

        match.setResults("Leicester", 3);

        service.computeBetsResult(customerPetya,match);
        service.computeBetsResult(customerVasya,match);
        service.filterBetsResult(customerPetya,"Win bets");// должен сразу показывать ?


        //c1.getBets().sort(Comparator.comparing(Bet::getPossibleGain));

        for (Bet b : customerPetya.getBets()) {
            System.out.println(b.getPossibleGain());
        }

        //service.VALUE_COMPARE.compare()

        for (Bet b : customerPetya.getBets()) {
            System.out.println(b.getPossibleGain());
        }
       // Comparator.comparing(o ->

    /*
     и прикрутить аккаунт
     Джава тайм для дата
     логи
     больше компораторов ?
    */

    }
}
