package com.epam.ilya.services;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.dao.DaoFactory;
import com.epam.ilya.dao.entityDao.BetDao;
import com.epam.ilya.dao.entityDao.ConditionDao;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Condition;
import com.epam.ilya.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BetService {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(BetService.class));

   /* public void addConditionToBet(Condition condition, Bet bet) {
        bet.addCondition(condition);
        log.info("Add condition " + condition + " to bet " + bet);
        bet.calculateFinalCoefficient();
        log.info("Bet's final coefficient become " + bet.getFinalCoefficient());
        bet.calculatePossibleGain();
        log.info("Bet's possible gain is " + bet.getPossibleGain());
    }

    public void putDownBetsResult(Customer customer, Bet bet, Bookmaker bookmaker) {
        if (customer.getBets().contains(bet)) {
            bet.calculateFinalResult();
            log.info("Customer's " + customer.getFirstName() + " bet's " + bet + " result is " + bet.isFinalResult());
            if (bet.isFinalResult()) {
                bookmaker.getPersonsPurse().removeCash(bet.getPossibleGain().minus(bet.getValue()));
                customer.getPersonsPurse().addCash(bet.getPossibleGain());
                log.info("Customer " + customer.getFirstName() + " win: " + bet.getPossibleGain() + ". Customer's balance: " + customer.getPersonsPurse().getBalance());
            } else {
                bookmaker.getPersonsPurse().addCash(bet.getValue());
                log.info("Customer " + customer.getFirstName() + " lose: " + bet.getValue() + ". Customer's balance" + customer.getPersonsPurse().getBalance());
            }
        }
    }*/


    public Bet registerCustomersBet(Bet bet, Customer customer) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        try {
            BetDao betDao = daoFactory.getDao(BetDao.class);
            daoFactory.startTransaction();
            bet = betDao.create(bet);
            betDao.addBetToCustomer(bet, customer);
            daoFactory.commitTransaction();
        } catch (DaoException e) {
            try {
                daoFactory.rollbackTransaction();
            } catch (DaoException e1) {
                throw new ServiceException("Cannot rollback transaction", e);
            }
            throw new ServiceException("Cannot get do for register bet", e);
        }
        return bet;
    }


    public void completeBetsCreation(Bet bet) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        try {
            BetDao betDao = daoFactory.getDao(BetDao.class);
            daoFactory.startTransaction();
            betDao.update(bet);
            for (Condition condition : bet.getConditions()) {
                betDao.addConditionToBet(condition, bet);
            }
            betDao.setStatus(bet, Dao.ACTIVE);
            daoFactory.commitTransaction();
        } catch (DaoException e) {
            try {
                daoFactory.rollbackTransaction();
            } catch (DaoException e1) {
                throw new ServiceException("Cannot rollback transaction", e);
            }
            throw new ServiceException("Cannot get dao for complete bet", e);
        }
    }

    public List<Bet> getAllActiveCustomersBets(Customer customer) throws ServiceException {
        return getAllCustomersBets(customer, Dao.ACTIVE);
    }

    public List<Bet> getAllInactiveCustomersBets(Customer customer) throws ServiceException {
        return getAllCustomersBets(customer, Dao.INACTIVE);
    }

    private List<Bet> getAllCustomersBets(Customer customer, boolean status) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        List<Bet> bets;
        try {
            BetDao betDao = daoFactory.getDao(BetDao.class);
            ConditionDao conditionDao = daoFactory.getDao(ConditionDao.class);
            daoFactory.startTransaction();
            bets = betDao.getAllCustomersBets(status,customer);
            if(bets!=null){
                for (Bet bet: bets) {
                    List<Condition> conditions= conditionDao.getBetsConditions(bet);
                    bet.setCustomer(customer);
                    bet.setConditions(conditions);
                }
            }
            daoFactory.commitTransaction();
        } catch (DaoException e) {
            try {
                daoFactory.rollbackTransaction();
            } catch (DaoException e1) {
                throw new ServiceException("Cannot rollback transaction",e);
            }
            throw new ServiceException("Cannot get dao for getting all bets", e);
        }
        return bets;
    }
}
