package com.epam.ilya.factory;

import com.epam.ilya.model.Customer;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 * Created by Дом on 18.02.2016.
 */
public class CustomerFactory {
    /*
    Creation of person with fill CashAccount
     */
    public Customer createCustomer(String name, double balance){
        Customer customer = new Customer(name);// нужно что бы он ловил баланс
        Money kzt = Money.of(CurrencyUnit.of("KZT"), 1000);// or this is gonna be in factory
        customer.getPersonsPurse().addCash(kzt);// или это должно создаваться и добавляться в конструкторе Кастомера
        return customer;
    }
    /*
    Creation of person with empty CashAccount
     */
    public Customer createCustomer(String name){
        Customer customer = new Customer(name);
        return customer;
    }
}
