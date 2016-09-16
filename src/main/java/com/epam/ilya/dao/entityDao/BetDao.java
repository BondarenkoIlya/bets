package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Condition;
import com.epam.ilya.model.Customer;
import com.epam.ilya.model.PaginatedList;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BetDao extends Dao implements EntityDao<Bet> {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(BetDao.class));
    private String UPDATE_BET = "UPDATE bets SET possibleGain = ? , finalCoefficient=? , finalResult = ? WHERE id = ?";
    private String DELETE_BET = "DELETE FROM bets WHERE id = ?";
    private String INSERT_BET = "Insert INTO bets VALUES (id,?,?,?,?,NULL,?)";
    private String ADD_CONDITION_TO_BET = "INSERT INTO bets_conditions VALUES (?,?)";
    private String ADD_BET_TO_CUSTOMER = "INSERT INTO customers_bets VALUES (?,?)";
    private String GET_CUSTOMERS_ACTIVE_BETS = "SELECT id ,bets.value ,possibleGain, finalCoefficient,finalResult,betsDate FROM bets WHERE finalResult is null AND customer_id=? order by betsDate desc limit ?,?";
    private String GET_CUSTOMERS_INACTIVE_BETS = "SELECT id ,bets.value ,possibleGain, finalCoefficient,finalResult,betsDate FROM bets WHERE finalResult is not null AND customer_id=? order by betsDate desc limit ?,?";
    private String GET_BETS_BY_CONDITION = "SELECT id ,bets.value ,possibleGain, finalCoefficient,finalResult,betsDate FROM bets JOIN bets_conditions ON bets.id=bets_conditions.bets_id WHERE conditions_id=?";
    private String ACTIVE_BET_COUNT = "SELECT count(*) FROM bets.bets where bets.finalResult is null AND bets.customer_id=?";
    private String INACTIVE_BET_COUNT = "SELECT count(*) FROM bets.bets where bets.finalResult is not null AND bets.customer_id=?";
    private String DELETE_COMMUNICATION = "DELETE  FROM customers_bets WHERE bets_id=?";

    @Override
    public Bet create(Bet bet) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_BET, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, bet.getValue().getAmount().doubleValue());
            statement.setDouble(2, bet.getCustomer().getId());
            statement.setDouble(3, bet.getPossibleGain().getAmount().doubleValue());
            statement.setDouble(4, bet.getFinalCoefficient());
            statement.setTimestamp(5, new Timestamp(bet.getDate().getMillis()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);
            log.info("Set generated id = {} to bet", id);
            bet.setId(id);
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create customer in database", e);
        }
        return bet;
    }

    @Override
    public Bet findById(int id) throws DaoException {
        return null;
    }

    @Override
    public void update(Bet bet) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_BET)) {
            statement.setDouble(1, bet.getPossibleGain().getAmount().doubleValue());
            statement.setDouble(2, bet.getFinalCoefficient());
            if (bet.getFinalResult() != null) {
                statement.setBoolean(3, bet.getFinalResult());
            } else {
                statement.setNull(3, Types.BOOLEAN);
            }
            statement.setInt(4, bet.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for updating bet", e);
        }
    }

    @Override
    public void delete(Bet bet) throws DaoException {
        try(PreparedStatement statement = getConnection().prepareStatement(DELETE_BET)) {
            statement.setInt(1,bet.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for deleting bet",e);
        }

    }

    public void addBetToCustomer(Bet bet, Customer customer) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(ADD_BET_TO_CUSTOMER)) {
            statement.setInt(1, customer.getId());
            statement.setInt(2, bet.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for adding bet to customer", e);
        }
    }

    public void addConditionToBet(Condition condition, Bet bet) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(ADD_CONDITION_TO_BET)) {
            statement.setInt(1, bet.getId());
            statement.setInt(2, condition.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for adding condition to bet", e);
        }
    }

    public PaginatedList<Bet> getAllCustomersBets(boolean status, Customer customer, int pageNumber, int pageSize) throws DaoException {
        PaginatedList<Bet> bets = new PaginatedList<>(pageNumber, pageSize);
        String query;
        if (status) {
            query = GET_CUSTOMERS_ACTIVE_BETS;
        } else {
            query = GET_CUSTOMERS_INACTIVE_BETS;
        }
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, customer.getId());
            statement.setInt(2, ((pageNumber - 1) * pageSize));
            statement.setInt(3, pageSize);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bet bet = pickBetFromResultSet(resultSet);
                bets.add(bet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for getting customer's bets", e);
        }
        log.debug("Bets size", bets.size());
        return bets;
    }

    public List<Bet> getBetsByCondition(Condition condition) throws DaoException {
        List<Bet> bets = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(GET_BETS_BY_CONDITION)) {
            statement.setInt(1, condition.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bet bet = pickBetFromResultSet(resultSet);
                log.debug("Pick bet from result set - {}", bet);
                bets.add(bet);
                log.debug("Add to bets");
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for finding bets by condition", e);
        }
        return bets;
    }

    private Bet pickBetFromResultSet(ResultSet resultSet) throws DaoException {
        Bet bet = new Bet();
        try {
            bet.setId(resultSet.getInt(1));
            bet.setValue(Money.of(CurrencyUnit.of("KZT"), resultSet.getDouble(2)));
            bet.setPossibleGain(Money.of(CurrencyUnit.of("KZT"), resultSet.getDouble(3)));
            bet.setFinalCoefficient(resultSet.getDouble(4));
            bet.setFinalResult(resultSet.getBoolean(5));
            if (resultSet.wasNull()) {
                bet.setFinalResult(null);
            }
            bet.setDate(new DateTime(resultSet.getTimestamp(6)));
        } catch (SQLException e) {
            throw new DaoException("Cannot pick bet from result set", e);
        }
        return bet;
    }

    public int getBetsCount(boolean status, Customer customer) throws DaoException {
        int count = 0;
        String query;
        if (status) {
            query = ACTIVE_BET_COUNT;
        } else {
            query = INACTIVE_BET_COUNT;
        }
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1,customer.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                count = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for counting bets", e);
        }
        return count;
    }

    public void deleteCommunication(Bet bet) throws DaoException {
        try(PreparedStatement statement = getConnection().prepareStatement(DELETE_COMMUNICATION)) {
            statement.setInt(1,bet.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for geleting communication",e);
        }
    }
}
