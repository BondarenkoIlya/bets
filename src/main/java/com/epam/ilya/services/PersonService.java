package com.epam.ilya.services;

import com.epam.ilya.dao.DaoException;
import com.epam.ilya.dao.DaoFactory;
import com.epam.ilya.dao.entityDao.BookmakerDao;
import com.epam.ilya.dao.entityDao.CashAccountDao;
import com.epam.ilya.dao.entityDao.CustomerDao;
import com.epam.ilya.dao.entityDao.TransferToBookmakerDao;
import com.epam.ilya.model.*;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

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

    public boolean transferMoney(Person sender, Money kzt, Person recipient){
        DaoFactory daoFactory = new DaoFactory();
        

    }

    public void depositOnAccount(Money kzt,Person recipient) throws ServiceException {
        depositOnAccount(null,kzt,recipient);
    }

    public void depositOnAccount(Person sender, Money kzt, Person recipient) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        try {
            CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
            CashAccount cashAccount = recipient.getPersonsPurse();
            cashAccount.addCash(kzt);
            cashAccountDao.update(cashAccount);
            daoFactory.closeConnection();
            creatreTransfer(sender,kzt,recipient);
        } catch (DaoException e) {
            throw new ServiceException("Cannot create cash account dao for updating purse", e);
        }
        log.info(recipient.getFirstName() + " add on cash account " + kzt);
        log.info(recipient.getFirstName() + "'s balance become :" + recipient.getPersonsPurse());
    }

    public boolean withdrawFromTheAccount(Person sender, Money kzt) throws ServiceException {
        withdrawFromTheAccount(sender,kzt,null);
    }


    public boolean withdrawFromTheAccount(Person sender, Money kzt,Person recipient) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        boolean result;
        try {
            CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
            CashAccount cashAccount = sender.getPersonsPurse();
            result = cashAccount.removeCash(kzt);
            cashAccountDao.update(cashAccount);
            daoFactory.closeConnection();
            creatreTransfer(sender, kzt, recipient);
        } catch (DaoException e) {
            throw new ServiceException("Cannot create cash account dao for updating purse", e);
        }
        log.info(sender.getFirstName() + " withdraw from account" + kzt);
        return result;
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
            customer.setPersonsPurse(registeredCashAccount);
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
        DaoFactory daoFactory = new DaoFactory();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("password", password);
        try {
            CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
            BookmakerDao bookmakerDao = daoFactory.getDao(BookmakerDao.class);
            daoFactory.startTransaction();
            Bookmaker bookmaker = bookmakerDao.getBookmaker(email, password);
            if (bookmaker != null) {
                CashAccount cashAccount = cashAccountDao.findByPerson(bookmaker);
                bookmaker.setPersonsPurse(cashAccount);
                daoFactory.commitTransaction();
                return bookmaker;
            } else {
                daoFactory.rollbackTransaction();
                daoFactory = new DaoFactory();
                CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
                daoFactory.startTransaction();
                log.debug("Try to find customer by email - {} and password - {}", email, password);
                List<Customer> customers = customerDao.findByParameters(parameters);
                if (customers.size() == 1) {
                    Customer customer = customers.get(0);
                    CashAccount cashAccount = cashAccountDao.findByPerson(customer);
                    log.debug("Try to find cash account for customer - {}", customer);
                    customer.setPersonsPurse(cashAccount);
                    log.debug("Set purse - {} to customer - {}", cashAccount, customer);
                    daoFactory.commitTransaction();
                    return customer;
                } else {
                    daoFactory.rollbackTransaction();
                    log.warn("In database more then one customer with same email and password or no one");
                    return null;
                }
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao for finding customer", e);
        }
    }

    public List<Customer> getAllCustomersFromDao() throws ServiceException {
        List<Customer> customers;
        DaoFactory daoFactory = new DaoFactory();
        try {
            CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
            daoFactory.startTransaction();
            customers = customerDao.getAllCustomers();
            setPurseToAllCustomers(customers);
            daoFactory.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("Cannot create list of all customers", e);
        }
        return customers;
    }

    private void setPurseToAllCustomers(List<Customer> customers) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        try {
            CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
            for (Customer customer : customers) {
                CashAccount purse = cashAccountDao.findByPerson(customer);
                customer.setPersonsPurse(purse);
            }
            daoFactory.closeConnection();
        } catch (DaoException e) {
            throw new ServiceException("Cannot get cashAccountDao", e);
        }
    }

    public boolean checkEmailAvailable(String email) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        boolean result = true;
        try {
            CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
            Map<String, String> parameters = new HashMap<>();
            parameters.put("email", email);
            List<Customer> customers = customerDao.findByParameters(parameters);
            log.debug("Find {} customers with email = {}", customers.size(), email);
            if (!customers.isEmpty()) {
                log.debug("Data base has customer with email - {}", email);
                result = false;
            }
            BookmakerDao bookmakerDao = daoFactory.getDao(BookmakerDao.class);
            Bookmaker bookmaker = bookmakerDao.getBookmaker(email);
            if (bookmaker != null) {
                log.debug("Email - {} is the same as bookmaker's email ", email);
                result = false;
            }

            daoFactory.closeConnection();
        } catch (DaoException e) {
            throw new ServiceException("Cannot find by email", e);
        }
        return result;
    }

    public List<Customer> searchByParameter(String parameter) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        List<Customer> customers = new ArrayList<>();
        List<String> parameterNames = asList("firstName", "lastName", "email");
        Map<String, String> parameters = new HashMap<>();
        try {
            CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
            for (String parameterName : parameterNames) {
                parameters.put(parameterName, parameter);
                List<Customer> byParameters = customerDao.findByParameters(parameters);
                customers.addAll(byParameters);
                parameters.clear();
            }
            daoFactory.closeConnection();
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao for search by parameter", e);
        }
        return customers;
    }

    public Customer findById(String id) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        Customer customer = null;
        try {
            CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
            CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
            daoFactory.startTransaction();
            customer = customerDao.findById(Integer.valueOf(id));
            CashAccount cashAccount = cashAccountDao.findByPerson(customer);
            customer.setPersonsPurse(cashAccount);
            daoFactory.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao for finding by id", e);
        }
        return customer;
    }

    public void creatreTransfer(Person sender, Money amount, Person recipirnt) {
        DaoFactory daoFactory = new DaoFactory();

        daoFactory.getDao(TransferToBookmakerDao)
    }
}
