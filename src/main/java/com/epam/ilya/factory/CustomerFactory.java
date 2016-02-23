package com.epam.ilya.factory;

import com.epam.ilya.model.Customer;

/**
 * Created by Дом on 18.02.2016.
 */
public class CustomerFactory {
    public Customer createCustomer(String name){
        Customer customer = new Customer(name);

        return customer;
    }
}
