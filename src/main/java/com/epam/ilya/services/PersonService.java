package com.epam.ilya.services;

import com.epam.ilya.exceptions.CashAccountBalanceExceptions;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.CashAccount;
import com.epam.ilya.model.Customer;
import com.epam.ilya.model.Person;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonService {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(PersonService.class));

    public void addPurseToPerson(Person person) {
        CashAccount account = new CashAccount();
        person.setPersonsPurse(account);
        account.setPerson(person);
        log.info(person.getFirstName() + " add cash account");
    }

    public void addBetToCustomer(Customer customer, Bet bet) {
        customer.getBets().add(bet);
        bet.setCustomer(customer);
        try {
            bet.removeMoneyFromCustomerToBet();
        } catch (CashAccountBalanceExceptions cashAccountBalanceExceptions) {
            cashAccountBalanceExceptions.printStackTrace();
        }
        log.info("Customer " + customer.getFirstName() + " make bet " + bet);
    }

    public void depositOnPersonsAccount(Person person, Money kzt) {
        person.getPersonsPurse().addCash(kzt);
        log.info(person.getFirstName() + " add on cash account " + kzt);
        log.info(person.getFirstName() + "'s balance become :" + person.getPersonsPurse());
    }

    public void withdrawFromTheAccount(Person person, Money kzt) {
        try {
            person.getPersonsPurse().removeCash(kzt);
            log.info(person.getFirstName() + " withdraw from account" + kzt);
        } catch (CashAccountBalanceExceptions cashAccountBalanceExceptions) {
            cashAccountBalanceExceptions.printStackTrace();
        }
    }

    public void showPersonsBalance(Person person) {
        log.info("Person" + person + " has " + person.getPersonsPurse());
    }
}
