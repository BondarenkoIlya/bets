package com.epam.ilya;

//import compare.SortedBetsByPossibleGain;
import com.epam.ilya.factory.BetFactory;
import com.epam.ilya.factory.CustomerFactory;
import com.epam.ilya.factory.MatchFactory;
import com.epam.ilya.model.*;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 * Created by Дом on 12.02.2016.
 */
public class Runner {
    public static void main(String[] args) {
        Service service = new Service();
        MatchFactory matchFactory = new MatchFactory();
        BetFactory betFactory = new BetFactory();
        CustomerFactory customerFactory = new CustomerFactory();

        Customer c1 = customerFactory.createCustomer("Петя");
        Customer c2 = customerFactory.createCustomer("Вася");

        Match m1 = matchFactory.createMatch("Football", "14 of February", "Arsenal", 1.6, "Leicester", 2.3, 4.3);

        Money val1 = Money.of(CurrencyUnit.of("KZT"), 400);
        Money val2 = Money.of(CurrencyUnit.of("KZT"), 250);
        Money val3 = Money.of(CurrencyUnit.of("KZT"), 700);
        Money val4 = Money.of(CurrencyUnit.of("KZT"), 600);

        ScoreBet bs1 = betFactory.createBetByScore(val1,m1,2,c1);
        c1.getBets().add(bs1);

        WinSideBet bws1 = betFactory.createBetByWinSide(val2, m1, "Arsenal", c1);
        c1.getBets().add(bws1);

        ScoreBet bs2 = betFactory.createBetByScore(val3,m1,3,c2);
        c2.getBets().add(bs2);

        WinSideBet bws2 = betFactory.createBetByWinSide(val4,m1, "Leicester", c2);
        c1.getBets().add(bws2);

        m1.setResults("Leicester", 3);

        service.completeBet(c1);
        service.completeBet(c2);


       /* for (Bet b : c1.getBets()) {
            System.out.println(b.getPossibleGain());

        }
        Collections.sort(c1.getBets, new SortedBetsByPossibleGain());
        for (Bet b : c1.getBets()) {
            System.out.println(b.getPossibleGain());

        }*/


    /*
     Прикрутит джода моней
     и прикрутить аккаунт
     Джава тайм для дата
     логи
     больше компораторов ?
     что делать с енумами?
    */

    }
}
