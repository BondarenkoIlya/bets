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

/**
 * This class do all works with bets
 *
 * @author Bondarenko Ilya
 */

public class BetService {
    static final Logger log = LoggerFactory.getLogger(BetService.class);

    /**
     * Method create bet for current customer and set communication
     *
     * @param bet      bet without conditions
     * @param customer bet maker
     * @return registered bet
     * @throws ServiceException
     */
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

    /**
     * Method add conditions to bet
     *
     * @param bet bet with conditions
     * @throws ServiceException
     */
    public void completeBetsCreation(Bet bet) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            BetDao betDao = daoFactory.getDao(BetDao.class);
            daoFactory.startTransaction();
            betDao.update(bet);
            for (Condition condition : bet.getConditions()) {
                betDao.addConditionToBet(condition, bet);
            }
            daoFactory.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("Cannot get dao for complete bet", e);
        }
    }

    public PaginatedList<Bet> getAllActiveCustomersBets(Customer customer, int pageNumber, int pageSize) throws ServiceException {
        return getAllCustomersBets(customer, Dao.ACTIVE, pageNumber, pageSize);
    }

    public PaginatedList<Bet> getAllInactiveCustomersBets(Customer customer, int pageNumber, int pageSize) throws ServiceException {
        return getAllCustomersBets(customer, Dao.INACTIVE, pageNumber, pageSize);
    }

    /**
     * Method return list of customers in range
     *
     * @param customer   owner of bets
     * @param status     status of bet(active or inactive)
     * @param pageNumber count of current page
     * @param pageSize   quantity of bet on one page
     * @return paginated list fo bet
     * @throws ServiceException
     */
    private PaginatedList<Bet> getAllCustomersBets(Customer customer, boolean status, int pageNumber, int pageSize) throws ServiceException {
        PaginatedList<Bet> bets;
        try (DaoFactory daoFactory = new DaoFactory()) {
            BetDao betDao = daoFactory.getDao(BetDao.class);
            ConditionDao conditionDao = daoFactory.getDao(ConditionDao.class);
            daoFactory.startTransaction();
            bets = betDao.getAllCustomersBets(status, customer, pageNumber, pageSize);
            int betsCount = betDao.getBetsCount(status, customer);
            log.debug("{} bets at all", betsCount);
            int pageCount = countUpPages(pageSize, betsCount);
            log.debug("{} pages by {} bets on one page", pageCount, pageSize);
            bets.setPageCount(pageCount);
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
     * Method check all bets that add current match's conditions and fill up results
     *
     * @param match finished match
     * @return list of finished bet with result
     * @throws ServiceException
     */
    public List<Bet> sumUpBetsResultByFinishedMatch(Match match) throws ServiceException {
        List<Bet> playedBets = new ArrayList<>();
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                BetDao betDao = daoFactory.getDao(BetDao.class);
                daoFactory.startTransaction();
                for (Condition condition : match.getConditionList()) {
                    List<Bet> bets = getBetsWithCondition(condition);
                    for (Bet bet : bets) {
                        log.debug("Sum up bet's result - {}", bet);
                        if (bet.getConditions().size() == 1) {
                            playedBets.add(bet);
                            bet.setFinalResult(condition.getResult());
                            betDao.update(bet);
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

    /**
     * Method return list of bet that contain current condition
     *
     * @param condition
     * @return list of bet that contain current condition
     * @throws ServiceException
     */
    private List<Bet> getBetsWithCondition(Condition condition) throws ServiceException {
        List<Bet> betsWithCondition;
        try (DaoFactory daoFactory = new DaoFactory()) {
            BetDao betDao = daoFactory.getDao(BetDao.class);
            ConditionDao conditionDao = daoFactory.getDao(ConditionDao.class);
            CustomerDao customerDao = daoFactory.getDao(CustomerDao.class);
            CashAccountDao cashAccountDao = daoFactory.getDao(CashAccountDao.class);
            betsWithCondition = betDao.getBetsByCondition(condition);
            for (Bet bet : betsWithCondition) {
                log.debug("Pick bet - {}", bet);
                List<Condition> conditions = conditionDao.getBetsConditions(bet);
                log.debug("Set conditions");
                bet.setConditions(conditions);
                Customer customer = customerDao.getBetsCustomer(bet);
                log.debug("Get bet's customer - {}", customer);
                CashAccount cashAccount = cashAccountDao.findByPerson(customer);
                log.debug("Get customer's purse - {}", cashAccount);
                customer.setPersonsPurse(cashAccount);
                bet.setCustomer(customer);
                log.debug("Set customer and purse to bet");
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot get bet dao", e);
        }
        return betsWithCondition;
    }

    /**
     * Method emergency cancel creation of bet without condition
     *
     * @param bet not finished bet
     * @throws ServiceException
     */
    public void cancelBetCreation(Bet bet) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            BetDao betDao = daoFactory.getDao(BetDao.class);
            betDao.deleteCommunication(bet);
            betDao.delete(bet);
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory for cancel bet creation", e);
        }
    }
}
