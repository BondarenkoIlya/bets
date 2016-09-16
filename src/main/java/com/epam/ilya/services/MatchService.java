package com.epam.ilya.services;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.dao.DaoFactory;
import com.epam.ilya.dao.entityDao.ConditionDao;
import com.epam.ilya.dao.entityDao.MatchDao;
import com.epam.ilya.model.Condition;
import com.epam.ilya.model.Match;
import com.epam.ilya.model.PaginatedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This class do all works with matches
 * @author Bondarenko Ilya
 */

public class MatchService {
    static final Logger log = LoggerFactory.getLogger(MatchService.class);

    public PaginatedList<Match> getAllActiveMatch(int pageNumber, int pageSize) throws ServiceException {
        return getAllMatch(Dao.ACTIVE,pageNumber, pageSize);
    }

    public PaginatedList<Match> getAllInactiveMatch(int pageNumber, int pageSize) throws ServiceException {
        return getAllMatch(Dao.INACTIVE,pageNumber, pageSize);
    }

    /**
     * Method return matches in range for pagination, and pick all nested entities
     *
     * @param status status of match activities
     * @param pageNumber count of current page
     * @param pageSize   quantity of match on one page
     * @return paginated list of match
     * @throws ServiceException
     */
    private PaginatedList<Match> getAllMatch(boolean status, int pageNumber, int pageSize) throws ServiceException {//как доставать соответствующие кондишены
        PaginatedList<Match> matches;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                MatchDao matchDao = daoFactory.getDao(MatchDao.class);
                ConditionDao conditionDao = daoFactory.getDao(ConditionDao.class);
                daoFactory.startTransaction();
                matches = matchDao.getAllMatches(status,pageNumber, pageSize);
                int matchesCount = matchDao.getMatchCount(status);
                log.debug("{} matches at all", matchesCount);
                int pageCount = countUpPages(pageSize, matchesCount);
                log.debug("{} pages by {} matches on one page", pageCount, pageSize);//TODO мысль: логировать только сервисы
                matches.setPageCount(pageCount);
                for (Match match : matches) {
                    List<Condition> conditions = conditionDao.getMatchsConditions(match);
                    match.setConditionList(conditions);
                }
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                throw new ServiceException("Cannot get all match",e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao for matches", e);
        }
        return matches;
    }

    private int countUpPages(int pageSize, int matchesCount) {
        int pageCount;
        if (matchesCount % pageSize == 0) {
            pageCount = matchesCount / pageSize;
        } else {
            pageCount = (matchesCount / pageSize) + 1;
        }
        return pageCount;
    }

    /**
     * Method create match without conditions
     *
     * @param match match without conditions
     * @return new match
     * @throws ServiceException
     */
    public Match createEmptyMatch(Match match) throws ServiceException {
        Match registeredMatch;
        try (DaoFactory daoFactory = new DaoFactory()) {
            MatchDao matchDao = daoFactory.getDao(MatchDao.class);
            registeredMatch = matchDao.create(match);
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao for register match", e);
        }
        return registeredMatch;
    }

    /**
     * Method add conditions to match and make it active
     *
     * @param match match with conditions
     * @throws ServiceException
     */
    public void completeMatchsCreation(Match match) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                ConditionDao conditionDao = daoFactory.getDao(ConditionDao.class);
                MatchDao matchDao = daoFactory.getDao(MatchDao.class);
                daoFactory.startTransaction();
                for (Condition condition : match.getConditionList()) {
                    conditionDao.create(condition);
                    conditionDao.addConditionToMatch(condition, match);
                }
                matchDao.setStatus(match, MatchDao.ACTIVE);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao for add conditions to match", e);
        }
    }

    public Condition getConditionById(String id) throws ServiceException {
        Condition condition;
        try (DaoFactory daoFactory = new DaoFactory()) {
            ConditionDao conditionDao = daoFactory.getDao(ConditionDao.class);
            condition = conditionDao.findById(Integer.parseInt(id));
        } catch (DaoException e) {
            throw new ServiceException("Cannot get dao for get condition", e);
        }
        return condition;
    }

    public Match getMatchById(String id) throws ServiceException {
        Match match = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                MatchDao matchDao = daoFactory.getDao(MatchDao.class);
                ConditionDao conditionDao = daoFactory.getDao(ConditionDao.class);
                daoFactory.startTransaction();
                match = matchDao.findById(Integer.parseInt(id));
                List<Condition> conditions = conditionDao.getMatchsConditions(match);
                match.setConditionList(conditions);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                throw new ServiceException("Cannot get match by id",e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot get dao for finding match by id", e);
        }

        return match;
    }

    /**
     * Method write result of current condition to data base
     *
     * @param condition not finished condition
     * @param result result of condition
     * @throws ServiceException
     */
    public void sumUpConditionsResult(Condition condition, Boolean result) throws ServiceException {
        condition.setResult(result);
        try (DaoFactory daoFactory = new DaoFactory()) {
            ConditionDao conditionDao = daoFactory.getDao(ConditionDao.class);
            conditionDao.update(condition);
        } catch (DaoException e) {
            throw new ServiceException("Cannot get dao for sum up conditions result", e);
        }
    }

    /**
     * Method make match inactive
     *
     * @param match active match
     * @throws ServiceException
     */
    public void deactivateMatch(Match match) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            MatchDao matchDao = daoFactory.getDao(MatchDao.class);
            matchDao.setStatus(match, Dao.INACTIVE);
        } catch (DaoException e) {
            throw new ServiceException("Cannot get match dao", e);
        }
    }

    /**
     * Method make emergency deleting empty match without condition
     *
     * @param match not finished match
     * @throws ServiceException
     */
    public void cancelMatchCreation(Match match) throws ServiceException {
        try(DaoFactory daoFactory = new DaoFactory()) {
            MatchDao matchDao = daoFactory.getDao(MatchDao.class);
            matchDao.delete(match);
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory for cancel match creation",e);
        }
    }
}
