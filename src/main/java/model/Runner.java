package model;

import compare.SortedCustomerListByName;
import factory.BetFactory;
import factory.CustomerFactory;
import factory.MatchFactory;
import org.joda.money.Money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Дом on 12.02.2016.
 */
public class Runner {
    public static void main(String[] args) {
        Service service = new Service();
        MatchFactory matchFactory = new MatchFactory();
        BetFactory betFactory = new BetFactory();
        CustomerFactory customerFactory = new CustomerFactory();

        Bookmaker bookmaker = new Bookmaker("Александер Васильевич");

        customerFactory.createCustomer("Петя");
        customerFactory.createCustomer("Вася");

        matchFactory.createMatch("Football", "14 of February", "Arsenal", 1.6, "Leicester", 2.3, 2, 4.3);

        bookmaker.customerList.get(0).bets.add(betFactory.createBetByWinSide(200, bookmaker.matchList.get(0), "Arsenal", bookmaker.customerList.get(0)));
        bookmaker.customerList.get(1).bets.add(betFactory.createBetByWinSide(400, bookmaker.matchList.get(0), "Leicester", bookmaker.customerList.get(1)));


        bookmaker.matchList.get(0).setResults("Leicester", 3);

        service.completeBet();


        for (Customer c : Bookmaker.customerList) {
            System.out.println(c.getName());

        }
        Collections.sort(Bookmaker.customerList, new SortedCustomerListByName());
        for (Customer c : Bookmaker.customerList) {
            System.out.println(c.getName());

        }


    /*
     Прикрутит джода моней
     Джава тайм для дата
     логи
     больше компораторов ?
     что делать с енумами?
    */

    }
}
