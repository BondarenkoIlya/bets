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
        //Как понять что именно от сущьности букмейкера будут происходить действия

        Customer customer1 = new Customer("Вася");

        service.createBetByWinSide(Money.of("KZT",200),service.createMatch("Football", "14 of February", "Arsenal", 1.6, "Leicester", 2.3, 2, 4.3),"Arsenal")//Я же как бы создал матч который называется матч

        Bookmaker.customerList.add(customer1);//Существует кастомер лист и без существующего букмейкера???


    }
}
