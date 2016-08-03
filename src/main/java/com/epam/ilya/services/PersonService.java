package com.epam.ilya.services;

import com.epam.ilya.dao.DaoException;
import com.epam.ilya.dao.DaoFactory;
import com.epam.ilya.dao.entityDao.CashAccountDao;
import com.epam.ilya.dao.entityDao.CustomerDao;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.CashAccount;
import com.epam.ilya.model.Customer;
import com.epam.ilya.model.Person;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonService {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(PersonService.class));

    public void addPurseToPerson(Person person) {// должен сразу сам в базе создавать новый кашелёк пустой
        CashAccount account = new CashAccount();
        person.setPersonsPurse(account);
        account.setPerson(person);
        log.info(person.getFirstName() + " add cash account");
    }

    public void addBetToCustomer(Customer customer, Bet bet) {
        DaoFactory daoFactory = new DaoFactory();
        customer.add(bet);
        bet.setCustomer(customer);


        bet.removeMoneyFromCustomerToBet();

        log.info("Customer " + customer.getFirstName() + " make bet " + bet);
    }

    public void depositOnPersonsAccount(Person person, Money kzt) {
        person.getPersonsPurse().addCash(kzt);
        log.info(person.getFirstName() + " add on cash account " + kzt);
        log.info(person.getFirstName() + "'s balance become :" + person.getPersonsPurse());
    }

    public void withdrawFromTheAccount(Person person, Money kzt) {
        person.getPersonsPurse().removeCash(kzt);
        log.info(person.getFirstName() + " withdraw from account" + kzt);

    }

    public void showPersonsBalance(Person person) {
        log.info("Person" + person + " has " + person.getPersonsPurse());
    }

    public void registerCustomer(Customer customer) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        log.info("Try to register person {}", customer);
        try {
            CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
            CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
            daoFactory.startTransaction();
            Customer registeredCustomer = customerDao.create(customer);
            log.info("Register customer {} with id = {}", registeredCustomer, registeredCustomer.getId());
            CashAccount registeredCashAccount = cashAccountDao.createPurseForPerson(registeredCustomer);
            log.info("Add to customer {} cash account {}", registeredCustomer, registeredCashAccount);
            daoFactory.commitTransaction();
        } catch (DaoException e) {
            try {
                daoFactory.rollbackTransaction();
            } catch (DaoException e1) {
                throw new ServiceException("Cannot rollback transaction", e1);
            }
            throw new ServiceException("Cannot register customer and create cash account", e);
        }

    }

    public Person performUserLogin(String email, String password) throws ServiceException {
        Customer customer = null;
        DaoFactory daoFactory = new DaoFactory();
        try {
            CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
            CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
            daoFactory.startTransaction();
            customer = customerDao.findByEmailAndPassword(email, password);
            CashAccount cashAccount = cashAccountDao.findByCustomers(customer);
            customer.setPersonsPurse(cashAccount);
            daoFactory.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao for finding customer", e);
        }
        return customer;
    }
}
