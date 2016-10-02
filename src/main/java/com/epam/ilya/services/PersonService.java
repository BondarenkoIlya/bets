package com.epam.ilya.services;

import com.epam.ilya.dao.DaoException;
import com.epam.ilya.dao.DaoFactory;
import com.epam.ilya.dao.entity.*;
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
 *
 * @author Bondarenko Ilya
 */

public class PersonService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

    private DaoFactory daoFactory;

    /**
     * Method make money transfer to person from outside (not in system)
     *
     * @param kzt       transfer's value
     * @param recipient person who get money
     * @return transfer result
     * @throws ServiceException
     */
    public boolean transferMoney(Money kzt, Person recipient) throws ServiceException {
        return transferMoney(null, kzt, recipient);
    }

    /**
     * Method make transfer from one person to another
     *
     * @param sender    person who give money
     * @param kzt       transfer's value
     * @param recipient person who get money
     * @return transfer result
     * @throws ServiceException
     */
    public boolean transferMoney(Person sender, Money kzt, Person recipient) throws ServiceException {
        boolean result;
        try {
            CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
            daoFactory.startTransaction();
            CashAccount recipientPurse = recipient.getPersonsPurse();
            if (sender != null) {
                LOG.debug("Begin transfer amount - {} , from person - {} to person -{}", kzt, sender, recipient);
                CashAccount senderPurse = sender.getPersonsPurse();
                LOG.debug("Sender purse - {}", senderPurse);
                if (senderPurse.balanceAvailabilityFor(kzt)) {
                    LOG.debug("Sender purse with balance - {} available for - {}", senderPurse.getBalance(), kzt);
                    senderPurse.removeCash(kzt);
                    LOG.debug("Remove from {} value - {}", senderPurse, kzt);
                    recipientPurse.addCash(kzt);
                    LOG.debug("Add to {} value - {}", recipientPurse, kzt);
                    cashAccountDao.update(senderPurse);
                    cashAccountDao.update(recipientPurse);
                    createTransfer(sender, kzt, recipient);
                    result = true;
                } else {
                    LOG.debug("Person - {} have not enough money to remove amount - {} to person - {}", sender, kzt, recipient);
                    result = false;
                }
            } else {
                LOG.debug("Person - {} get amount - {} from outside", recipient, kzt);
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

    /**
     * Method register new customer in data base
     *
     * @param customer not created in data base customer
     * @return registered customer
     * @throws ServiceException
     */
    public Customer registerCustomer(Customer customer) throws ServiceException {
        LOG.info("Try to register person {}", customer);
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
                LOG.info("Register customer {} with id = {}", registeredCustomer, registeredCustomer.getId());
                LOG.info("Add to customer {} cash account {}", registeredCustomer, registeredCashAccount);
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

    /**
     * Method find current person, bookmakers or customer, bu login and password
     *
     * @param email    customer's email
     * @param password customer's password
     * @return found person
     * @throws ServiceException
     */
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
                    LOG.debug("Try to find customer by email - {} and password", email);
                    List<Customer> customers = customerDao.findByParameters(parameters);
                    if (customers.size() == 1) {
                        Customer customer = customers.get(0);
                        CashAccount cashAccount = cashAccountDao.findByPerson(customer);
                        LOG.debug("Try to find cash account for customer - {}", customer);
                        customer.setPersonsPurse(cashAccount);
                        LOG.debug("Set purse - {} to customer - {}", cashAccount, customer);
                        Avatar avatar = avatarDao.findByPerson(customer);
                        if (avatar != null) {
                            customer.setAvatar(avatar);
                        }
                        daoFactory.commitTransaction();
                        return customer;
                    } else {
                        LOG.warn("In database more then one customer with same email and password or no one");
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

    /**
     * Method give customers from data base in range for creation pagination list
     *
     * @param pageNumber count of current page
     * @param pageSize   quantity of customers on one page
     * @return paginated list of customers
     * @throws ServiceException
     */
    public PaginatedList<Customer> getAllCustomers(int pageNumber, int pageSize) throws ServiceException {
        PaginatedList<Customer> customers;
        try (DaoFactory daoFactory = new DaoFactory()) {
            this.daoFactory = daoFactory;
            try {
                CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
                daoFactory.startTransaction();
                customers = (PaginatedList<Customer>) customerDao.getAllActiveCustomers(pageNumber, pageSize);
                LOG.debug("Get customers paginated list list with {} customers", customers.size());
                int customersCount = customerDao.getCustomersCount();
                LOG.debug("{} customers at all", customersCount);
                int pageCount = countUpPages(pageSize, customersCount);
                LOG.debug("{} pages by {} customers on one page", pageCount, pageSize);
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

    /**
     * Method find and set purse to list of customers
     *
     * @param customers list of customers without purse
     * @throws DaoException
     */
    private void setPurseToAllCustomers(List<Customer> customers) throws DaoException {
        CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
        for (Customer customer : customers) {
            CashAccount purse = cashAccountDao.findByPerson(customer);
            customer.setPersonsPurse(purse);
        }
    }

    /**
     * Method try to find usage of current email and return true if email free
     *
     * @param email that must to be check
     * @return result of checking
     * @throws ServiceException
     */
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
                LOG.debug("Find {} customers with email = {}", customers.size(), email);
                if (!customers.isEmpty()) {
                    LOG.debug("Data base has customer with email - {}", email);
                    result = false;
                }
                Bookmaker bookmaker = bookmakerDao.getBookmaker(email);
                if (bookmaker != null) {
                    LOG.debug("Email - {} is the same as bookmaker's email ", email);
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

    /**
     * Method find and return customer by id
     *
     * @param id of some customers
     * @return found customer
     * @throws ServiceException
     */
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

    /**
     * Write transfer in data base
     *
     * @param sender    person who give money
     * @param amount    transfer's value
     * @param recipient person who get money
     * @throws ServiceException
     */
    private void createTransfer(Person sender, Money amount, Person recipient) throws ServiceException {
        Transfer transfer = new Transfer(sender, recipient, amount);
        try {
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

    /**
     * Method do work by making transfer by bets result
     *
     * @param bet       finished bet
     * @param bookmaker transfer participant
     * @throws ServiceException
     */
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

    /**
     * Method create new or update customer's avatar
     *
     * @param avatar   image of customer
     * @param customer usage of image
     * @throws ServiceException
     */
    public void setAvatarToCustomer(Avatar avatar, Customer customer) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                AvatarDao avatarDao = daoFactory.getDao(AvatarDao.class);
                CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
                daoFactory.startTransaction();
                Avatar byPerson = avatarDao.findByPerson(customer);
                avatar = avatarDao.create(avatar);
                customer.setAvatar(avatar);
                customerDao.updateAvatar(customer);
                if (byPerson == null) {
                    LOG.debug("Have no old avatar, create new one - {}", avatar);
                } else {
                    avatarDao.delete(byPerson);
                    LOG.debug("Have old avatar, take it from base - {}", avatar);
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

    /**
     * Method return customer's avatar if avatar's date of creation later then modify date
     *
     * @param loggedCustomer image user
     * @param modifyDate     last avatar creation date
     * @return new avatar
     * @throws ServiceException
     */
    public Avatar getCustomersAvatar(Customer loggedCustomer, long modifyDate) throws ServiceException {
        Avatar avatar;
        try (DaoFactory daoFactory = new DaoFactory()) {
            AvatarDao avatarDao = daoFactory.getDao(AvatarDao.class);
            if (modifyDate != -1) {
                DateTime date = new DateTime(modifyDate);
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = formatter.print(date);
                LOG.debug("Try to find avatar by modified date - {}", formattedDate);
                avatar = avatarDao.findByPersonAndDate(loggedCustomer, formattedDate);
                LOG.debug("Find avatar - {} just by customer and modified date", avatar);
            } else {
                avatar = avatarDao.findByPerson(loggedCustomer);
                LOG.debug("Find avatar - {} just by customer", avatar);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory", e);
        }
        return avatar;
    }

    /**
     * Method add money to bookmaker's or customer's purse and do transaction work
     *
     * @param kzt    amount
     * @param person recipient of money
     * @throws ServiceException
     */
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

    /**
     * Method do work by replacing money from customer, whom make bet, to bookmaker
     *
     * @param loggedCustomer customer whom make bet
     * @param value          value of bet
     * @throws ServiceException
     */
    public void replaceBatsValueToBookmaker(Customer loggedCustomer, Money value) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            this.daoFactory = daoFactory;
            try {
                daoFactory.startTransaction();
                Bookmaker bookmaker = getBookmaker();
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

    public CashAccount refreshCashAccount(CashAccount purse) throws ServiceException {
        CashAccount byId;
        try (DaoFactory daoFactory = new DaoFactory()) {
            CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
            byId = cashAccountDao.findById(purse.getId());
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory", e);
        }
        return byId;
    }


    public Bookmaker getBookmaker() throws ServiceException {
        Bookmaker bookmaker;
        try {
            BookmakerDao bookmakerDao = daoFactory.getDao(BookmakerDao.class);
            bookmaker = bookmakerDao.getBookmaker(Bookmaker.EMAIL);
            CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
            CashAccount cashAccount = cashAccountDao.findByPerson(bookmaker);
            bookmaker.setPersonsPurse(cashAccount);
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao for working with bookmaker or cash account", e);
        }
        return bookmaker;
    }
}
