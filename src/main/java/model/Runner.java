package model;

import org.joda.money.Money;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Дом on 12.02.2016.
 */
public class Runner {
    public static void main(String[] args) {
        Bookmaker bookmaker = new Bookmaker("Александер Васильевич");
        Service service = new Service();


        Customer customer1 = new Customer("Вася");
        bookmaker.customerList.add(customer1);

        service.createMatch("Football", "14 of February", "Arsenal", 1.6, "Leicester", 2.3, 2, 4.3);//Как понять что именно от сущьности(имени) определенного букмейкера будут происходить действия

        customer1.betsByWinSide.add(service.createBetByWinSide(200,bookmaker.matchList.get(1) ,"Arsenal"));

        bookmaker.matchList.get(1).setResults("Arsenal",3);

        customer1.betsByWinSide.get(1).setFinalPossibleGain();
        customer1.betsByWinSide.get(1).setFinalResult();




    }
}
