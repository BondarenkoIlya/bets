package com.epam.ilya.services;

import com.epam.ilya.dao.DaoException;
import com.epam.ilya.dao.DaoFactory;
import com.epam.ilya.dao.entityDao.*;
import com.epam.ilya.model.*;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class do all works with bookmaker and customers
 * @author Bondarenko Ilya
 */

public class PersonService {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(PersonService.class));
    DaoFactory daoFactory;

    //Method make money transfer to person from outside (not in system)
    public boolean transferMoney(Money kzt, Person recipient) throws ServiceException {
        return transferMoney(null, kzt, recipient);
    }

    //Method make transfer from one person to another
    public boolean transferMoney(Person sender, Money kzt, Person recipient) throws ServiceException {
        boolean result = false;
        try {
            CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
            daoFactory.startTransaction();
            CashAccount recipientPurse = recipient.getPersonsPurse();
            if (sender != null) {
                log.debug("Begin transfer amount - {} , from person - {} to person -{}", kzt, sender, recipient);
                CashAccount senderPurse = sender.getPersonsPurse();
                log.debug("Sender purse - {}", senderPurse);
                if (senderPurse.balanceAvailabilityFor(kzt)) {
                    log.debug("Sender purse with balance - {} available for - {}", senderPurse.getBalance(), kzt);
                    senderPurse.removeCash(kzt);
                    log.debug("Remove from {} value - {}", senderPurse, kzt);
                    recipientPurse.addCash(kzt);
                    log.debug("Add to {} value - {}", recipientPurse, kzt);
                    cashAccountDao.update(senderPurse);
                    cashAccountDao.update(recipientPurse);
                    createTransfer(sender, kzt, recipient);
                    result = true;
                } else {
                    log.debug("Person - {} have not enough money to remove amount - {} to person - {}", sender, kzt, recipient);
                    result = false;
                }
            } else {
                log.debug("Person - {} get amount - {} from outside", recipient, kzt);
                recipientPurse.addCash(kzt);
                cashAccountDao.update(recipientPurse);
                createTransfer(sender, kzt, recipient);
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create cash account dao", e);
        }
        return result;
    }

    //Method register new customer in data base
    public Customer registerCustomer(Customer customer) throws ServiceException {
        log.info("Try to register person {}", customer);
        Customer registeredCustomer;
        CashAccount cashAccount = new CashAccount();
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
                CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
                daoFactory.startTransaction();
                CashAccount registeredCashAccount = cashAccountDao.create(cashAccount);
                customer.setPersonsPurse(registeredCashAccount);
                registeredCustomer = customerDao.create(customer);
                log.info("Register customer {} with id = {}", registeredCustomer, registeredCustomer.getId());
                log.info("Add to customer {} cash account {}", registeredCustomer, registeredCashAccount);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                throw new ServiceException("Cannot register customer", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot register customer and create cash account", e);
        }
        return registeredCustomer;
    }

    //Method find current person, bookmakers or customer, bu login and password
    public Person performUserLogin(String email, String password) throws ServiceException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("password", password);
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
                BookmakerDao bookmakerDao = daoFactory.getDao(BookmakerDao.class);
                Bookmaker bookmaker = bookmakerDao.getBookmaker(email, password);
                daoFactory.startTransaction();
                if (bookmaker != null) {
                    CashAccount cashAccount = cashAccountDao.findByPerson(bookmaker);
                    bookmaker.setPersonsPurse(cashAccount);
                    return bookmaker;
                } else {
                    CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
                    AvatarDao avatarDao = daoFactory.getDao(AvatarDao.class);
                    log.debug("Try to find customer by email - {} and password - {}", email, password);
                    List<Customer> customers = customerDao.findByParameters(parameters);
                    if (customers.size() == 1) {
                        Customer customer = customers.get(0);
                        CashAccount cashAccount = cashAccountDao.findByPerson(customer);
                        log.debug("Try to find cash account for customer - {}", customer);
                        customer.setPersonsPurse(cashAccount);
                        log.debug("Set purse - {} to customer - {}", cashAccount, customer);
                        Avatar avatar = avatarDao.findByPerson(customer);
                        if (avatar != null) {
                            customer.setAvatar(avatar);
                        }
                        daoFactory.commitTransaction();
                        return customer;
                    } else {
                        log.warn("In database more then one customer with same email and password or no one");
                        daoFactory.commitTransaction();
                        return null;
                    }
                }
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                throw new ServiceException("Cannot find any account with this login and password", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao for finding customer", e);
        }
    }

    //Method give customers from data base in range for creation pagination list
    public PaginatedList<Customer> getAllCustomers(int pageNumber, int pageSize) throws ServiceException {
        PaginatedList<Customer> customers;
        try (DaoFactory daoFactory = new DaoFactory()) {
            this.daoFactory = daoFactory;
            try {
                CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
                daoFactory.startTransaction();
                customers = (PaginatedList<Customer>) customerDao.getAllActiveCustomers(pageNumber, pageSize);
                log.debug("Get customers paginated list list with {} customers", customers.size());
                int customersCount = customerDao.getCustomersCount();
                log.debug("{} customers at all", customersCount);
                int pageCount = countUpPages(pageSize, customersCount);
                log.debug("{} pages by {} customers on one page", pageCount, pageSize);
                customers.setPageCount(pageCount);
                customers.setPageNumber(pageNumber);
                customers.setPageSize(pageSize);
                setPurseToAllCustomers(customers);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                throw new ServiceException("Cannot get customers in range", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create list of all customers", e);
        }
        return customers;
    }

    private int countUpPages(int pageSize, int customersCount) {
        int pageCount;
        if (customersCount % pageSize == 0) {
            pageCount = customersCount / pageSize;
        } else {
            pageCount = (customersCount / pageSize) + 1;
        }
        return pageCount;
    }

    //Method find and set purse to list of customers
    private void setPurseToAllCustomers(List<Customer> customers) throws DaoException {
        CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
        for (Customer customer : customers) {
            CashAccount purse = cashAccountDao.findByPerson(customer);
            customer.setPersonsPurse(purse);
        }
    }

    //Method try to find usage of current email and return true if email free
    public boolean checkEmailAvailable(String email) throws ServiceException {
        boolean result = true;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
                BookmakerDao bookmakerDao = daoFactory.getDao(BookmakerDao.class);
                daoFactory.startTransaction();
                Map<String, String> parameters = new HashMap<>();
                parameters.put("email", email);
                List<Customer> customers = customerDao.findByParameters(parameters);
                log.debug("Find {} customers with email = {}", customers.size(), email);
                if (!customers.isEmpty()) {
                    log.debug("Data base has customer with email - {}", email);
                    result = false;
                }
                Bookmaker bookmaker = bookmakerDao.getBookmaker(email);
                if (bookmaker != null) {
                    log.debug("Email - {} is the same as bookmaker's email ", email);
                    result = false;
                }
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                throw new ServiceException("Cannot check email available", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot find by email", e);
        }
        return result;
    }

    //Method find and return customer by id
    public Customer findById(String id) throws ServiceException {
        Customer customer;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
                CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
                AvatarDao avatarDao = daoFactory.getDao(AvatarDao.class);
                daoFactory.startTransaction();
                customer = customerDao.findById(Integer.valueOf(id));
                CashAccount cashAccount = cashAccountDao.findByPerson(customer);
                customer.setPersonsPurse(cashAccount);
                Avatar avatar = avatarDao.findByPerson(customer);
                customer.setAvatar(avatar);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                throw new ServiceException("Cannot get customer dao", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory", e);
        }
        return customer;
    }

    //Write transfer in data base
    private void createTransfer(Person sender, Money amount, Person recipient) throws ServiceException {

        Transfer transfer = new Transfer(sender, recipient, amount);
        try (DaoFactory daoFactory = new DaoFactory()) {
            if (recipient instanceof Bookmaker) {
                TransferToBookmakerDao transferToBookmakerDao = daoFactory.getDao(TransferToBookmakerDao.class);
                transferToBookmakerDao.create(transfer);
            } else {
                TransferToCustomerDao transferToCustomerDao = daoFactory.getDao(TransferToCustomerDao.class);
                transferToCustomerDao.create(transfer);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao for work with transfers", e);
        }
    }

    //Method do work by making transfer by bets result
    public void summarizeBet(Bet bet, Bookmaker bookmaker) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            this.daoFactory = daoFactory;
            try {
                daoFactory.startTransaction();
                if (bet.getFinalResult()) {
                    transferMoney(bookmaker, bet.getPossibleGain(), bet.getCustomer());
                }
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                throw new ServiceException("Cannot transfer money", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory for summarise bet", e);
        }

    }

    //Method create new or update customer's avatar
    public void setAvatarToCustomer(Avatar avatar, Customer customer) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                AvatarDao avatarDao = daoFactory.getDao(AvatarDao.class);
                CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
                daoFactory.startTransaction();
                Avatar byPerson = avatarDao.findByPerson(customer);
                if (byPerson == null) {
                    avatar = avatarDao.create(avatar);
                    log.debug("Have no old avatar, create new one - {}", avatar);
                    customer.setAvatar(avatar);
                    customerDao.updateAvatar(customer);
                } else {
                    avatar.setId(byPerson.getId());
                    avatarDao.update(avatar);
                    log.debug("Have old avatar, take it from base - {}", avatar);
                }
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                throw new ServiceException("Cannot set avatar to customer", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory", e);
        }

    }

    //Method return customer's avatar if avatar's date of creation later then modify date
    public Avatar getCustomersAvatar(Customer loggedCustomer, String modifyDate) throws ServiceException {
        Avatar avatar;
        try (DaoFactory daoFactory = new DaoFactory()) {
            AvatarDao avatarDao = daoFactory.getDao(AvatarDao.class);
            if (modifyDate != null) {
                DateTime date = DateTime.parse(modifyDate);
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = formatter.print(date);
                log.debug("Try to find avatar by modified date - {}", formattedDate);
                avatar = avatarDao.findByPersonAndDate(loggedCustomer, formattedDate);
                log.debug("Find avatar - {} just by customer and modified date", avatar);
            } else {
                avatar = avatarDao.findByPerson(loggedCustomer);
                log.debug("Find avatar - {} just by customer", avatar);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory", e);
        }
        return avatar;
    }

    //Method add money to bookmaker's or customer's purse and do transaction work
    public void replenishPersonsBalance(Money kzt, Person person) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            this.daoFactory = daoFactory;
            try {
                daoFactory.startTransaction();
                transferMoney(kzt, person);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                throw new ServiceException("Cannot transfer money", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory for replenish person's balance", e);
        }
    }

    //Method do work by replacing money from customer, whom make bet, to bookmaker
    public void replaceBatsValueToBookmaker(Customer loggedCustomer, Money value, Bookmaker bookmaker) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            this.daoFactory = daoFactory;
            try {
                daoFactory.startTransaction();
                transferMoney(loggedCustomer, value, bookmaker);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                throw new ServiceException("Cannot transfer money", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory for replace money to bookmaker", e);
        }
    }
}
