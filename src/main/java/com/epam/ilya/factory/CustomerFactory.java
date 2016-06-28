package com.epam.ilya.factory;

import com.epam.ilya.model.Customer;
import com.epam.ilya.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CustomerFactory {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(CustomerFactory.class));

    public Customer createCustomer(String name){
        Customer customer = new Customer(name);
        log.info("Create new person " + customer.getFirstName() + " with empty purse");
        PersonService personService = new PersonService();
        personService.addPurseToPerson(customer);
        return customer;
    }
}
