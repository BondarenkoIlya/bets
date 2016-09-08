package com.epam.ilya.services;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.dao.DaoFactory;
import com.epam.ilya.dao.entityDao.BetDao;
import com.epam.ilya.dao.entityDao.CashAccountDao;
import com.epam.ilya.dao.entityDao.ConditionDao;
import com.epam.ilya.dao.entityDao.CustomerDao;
import com.epam.ilya.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BetService {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(BetService.class));
    private DaoFactory daoFactory = new DaoFactory();

    public Bet registerCustomersBet(Bet bet, Customer customer) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            BetDao betDao = daoFactory.getDao(BetDao.class);
            daoFactory.startTransaction();
            bet = betDao.create(bet);
            betDao.addBetToCustomer(bet, customer);
            daoFactory.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("Cannot get do for register bet", e);
        }
        return bet;
    }


    public void completeBetsCreation(Bet bet) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            BetDao betDao = daoFactory.getDao(BetDao.class);
            daoFactory.startTransaction();
            betDao.update(bet);
            for (Condition condition : bet.getConditions()) {
                betDao.addConditionToBet(condition, bet);
            }
            betDao.setStatus(bet, Dao.ACTIVE);

            daoFactory.commitTransaction();
        } catch (DaoException e) {
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
        List<Bet> bets;
        try (DaoFactory daoFactory = new DaoFactory()) {
            BetDao betDao = daoFactory.getDao(BetDao.class);
            ConditionDao conditionDao = daoFactory.getDao(ConditionDao.class);
            daoFactory.startTransaction();
            bets = betDao.getAllCustomersBets(status, customer);
            if (!bets.isEmpty()) {
                for (Bet bet : bets) {
                    log.debug("Found bet - {}", bet);
                    List<Condition> conditions = conditionDao.getBetsConditions(bet);
                    bet.setCustomer(customer);
                    bet.setConditions(conditions);
                }
            }
            daoFactory.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("Cannot get dao for getting all bets", e);
        }
        return bets;
    }

    public List<Bet> sumUpBetsResultByFinishedMatch(Match match) throws ServiceException {
        List<Bet> playedBets = new ArrayList<>();
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                BetDao betDao = daoFactory.getDao(BetDao.class);
                daoFactory.startTransaction();
                for (Condition condition : match.getConditionList()) {
                    List<Bet> bets = getBetsWithCondition(condition);
                    for (Bet bet : bets) {
                        log.debug("Sum up bet's result - {}",bet);
                        if (bet.getConditions().size() == 1) {
                            playedBets.add(bet);
                            bet.setFinalResult(condition.getResult());
                            betDao.update(bet);
                            betDao.setStatus(bet, Dao.INACTIVE);
                        } else {
                            boolean completeBet = true;
                            boolean betsResult = true;
                            for (Condition betsCondition : bet.getConditions()) {
                                if (betsCondition.getResult() != null) {
                                    if (!betsCondition.getResult()) {
                                        betsResult = false;
                                    }
                                } else {
                                    completeBet = false;
                                }
                            }
                            if (completeBet) {
                                playedBets.add(bet);
                                bet.setFinalResult(betsResult);
                                betDao.update(bet);
                                betDao.setStatus(bet, Dao.INACTIVE);
                            }
                        }
                    }
                }
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot get bet dao", e);
        }
        return playedBets;
    }

    private List<Bet> getBetsWithCondition(Condition condition) throws ServiceException {
        List<Bet> betsWithCondition = new ArrayList<>();
        try (DaoFactory daoFactory = new DaoFactory()) {
            BetDao betDao = daoFactory.getDao(BetDao.class);
            ConditionDao conditionDao = daoFactory.getDao(ConditionDao.class);
            CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
            CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
            betsWithCondition = betDao.getBetsByCondition(condition);
            for (Bet bet : betsWithCondition) {
                log.debug("Pick bet - {}",bet);
                List<Condition> conditions = conditionDao.getBetsConditions(bet);
                log.debug("Set conditions");
                bet.setConditions(conditions);
                Customer customer = customerDao.getBetsCustomer(bet);
                log.debug("Get bet's customer - {}",customer);
                CashAccount cashAccount = cashAccountDao.findByPerson(customer);
                log.debug("Get customer's purse - {}",cashAccount);
                customer.setPersonsPurse(cashAccount);
                bet.setCustomer(customer);
                log.debug("Set customer and purse to bet");
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot get bet dao", e);
        }
        return betsWithCondition;
    }
}
