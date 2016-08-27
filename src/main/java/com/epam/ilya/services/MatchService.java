package com.epam.ilya.services;

import com.epam.ilya.dao.DaoException;
import com.epam.ilya.dao.DaoFactory;
import com.epam.ilya.dao.entityDao.ConditionDao;
import com.epam.ilya.dao.entityDao.MatchDao;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Condition;
import com.epam.ilya.model.Customer;
import com.epam.ilya.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MatchService {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(MatchService.class));

    public void removeConditionFromMatch(Condition condition) {
        //Match match = condition.getMatch();
        //match.getConditionList().remove(condition);
        //log.info("Remove condition - " + condition + " from match - " + match);
    }

    public void fillResultOfCondition(Condition condition, boolean result) {
        condition.setResult(result);
        log.info("Set result '" + result + "' to condition " + condition);
    }

    public void calculateAllBetsResultForCustomer(Match match, Customer customer) {
        for (Bet bet : customer.getBets()) {
            bet.calculateFinalResult();
        }
    }

    public List<Match> getAllMatchFromDao() throws ServiceException {//как доставать соответствующие кондишены
        List<Match> matches = new ArrayList<>();
        DaoFactory daoFactory = new DaoFactory();
        try {
            MatchDao matchDao = daoFactory.getDao(MatchDao.class);
            ConditionDao conditionDao = daoFactory.getDao(ConditionDao.class);
            matches = matchDao.getAllMatches();
            for (Match match : matches) {
                List<Condition> conditions = conditionDao.getMatchsCondition(match);
                match.setConditionList(conditions);
            }
            daoFactory.closeConnection();
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao for matches", e);
        }
        return matches;
    }

    public Match createEmptyMatch(Match match) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        Match registeredMatch;
        try {
            MatchDao matchDao = daoFactory.getDao(MatchDao.class);
            registeredMatch = matchDao.create(match);
            daoFactory.closeConnection();
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao for register match", e);
        }
        return registeredMatch;
    }

    public void completeMatchsCreation(Match match) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        try {
            ConditionDao conditionDao = daoFactory.getDao(ConditionDao.class);
            for (Condition condition : match.getConditionList()) {
                conditionDao.create(condition);
                conditionDao.addConditionToMatch(condition, match);
            }
            daoFactory.closeConnection();
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao for add conditions to match", e);
        }
    }

    public Condition getConditionById(String id) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        Condition condition;
        try {
            ConditionDao conditionDao = daoFactory.getDao(ConditionDao.class);
            condition = conditionDao.findById(Integer.parseInt(id));
            daoFactory.closeConnection();
        } catch (DaoException e) {
            throw new ServiceException("Cannot get dao for get condition", e);
        }
        return condition;
    }

    public Match getMatchById(String id) throws ServiceException {
        Match match = null;
        DaoFactory daoFactory = new DaoFactory();
        try {
            MatchDao matchDao = daoFactory.getDao(MatchDao.class);
            ConditionDao conditionDao = daoFactory.getDao(ConditionDao.class);
            daoFactory.startTransaction();
            match = matchDao.findById(Integer.parseInt(id));
            List<Condition> conditions = conditionDao.getMatchsCondition(match);
            match.setConditionList(conditions);
            daoFactory.commitTransaction();
        } catch (DaoException e) {
            try {
                daoFactory.rollbackTransaction();
            } catch (DaoException e1) {
                throw new ServiceException("Cannot rollback transaction",e);
            }
            throw new ServiceException("Cannot get dao for finding match by id", e);
        }

        return match;
    }
}
