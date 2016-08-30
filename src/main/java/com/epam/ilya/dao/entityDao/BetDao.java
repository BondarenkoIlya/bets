package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Condition;
import com.epam.ilya.model.Customer;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BetDao extends Dao implements EntityDao<Bet> {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(BetDao.class));
    private String FIND_BY_ID = "SELECT * FROM bets WHERE id = ?";
    private String UPDATE_BET = "UPDATE bets SET possibleGain = ? , finalCoefficient=? WHERE id = ?";
    private String DELETE_BET = "DELETE FROM bets WHERE id = ?";
    private String INSERT_BET = "Insert INTO bets VALUES (id,?,?,?,?,NULL,?,FALSE)";
    private String ADD_CONDITION_TO_BET = "INSERT INTO bets_conditions VALUES (?,?)";
    private String BETS_STATUS = "update bets set  active = ?  where id=?";
    private String ADD_BET_TO_CUSTOMER = "INSERT INTO customers_bets VALUES (?,?)";
    private String GET_CUSTOMERS_BETS = "SELECT * FROM bets WHERE active=? AND customer_id=?";

    @Override
    public Bet create(Bet bet) throws DaoException {
        try {
            PreparedStatement statement = getConnection().prepareStatement(INSERT_BET, PreparedStatement.RETURN_GENERATED_KEYS);
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
            statement.close();
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
        try {
            PreparedStatement statement = getConnection().prepareStatement(UPDATE_BET);
            statement.setDouble(1, bet.getPossibleGain().getAmount().doubleValue());
            statement.setDouble(2, bet.getFinalCoefficient());
            statement.setInt(3, bet.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for updating bet", e);
        }
    }

    @Override
    public void delete(Bet bet) throws DaoException {

    }

    public void addBetToCustomer(Bet bet, Customer customer) throws DaoException {
        try {
            PreparedStatement statement = getConnection().prepareStatement(ADD_BET_TO_CUSTOMER);
            statement.setInt(1, customer.getId());
            statement.setInt(2, bet.getId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create stetement for adding bet to customer", e);
        }
    }

    public void addConditionToBet(Condition condition, Bet bet) throws DaoException {
        try {
            PreparedStatement statement = getConnection().prepareStatement(ADD_CONDITION_TO_BET);
            statement.setInt(1, bet.getId());
            statement.setInt(2, condition.getId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for adding condition to bet", e);
        }
    }

    public void setStatus(Bet bet, boolean status) throws DaoException {
        try {
            PreparedStatement statement = getConnection().prepareStatement(BETS_STATUS);
            statement.setBoolean(1, status);
            statement.setInt(2, bet.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for baking bet active", e);
        }
    }


    public List<Bet> getAllCustomersBets(boolean status, Customer customer) throws DaoException {
        List<Bet> bets = null;
        try {
            PreparedStatement statement = getConnection().prepareStatement(GET_CUSTOMERS_BETS);
            statement.setBoolean(1, status);
            statement.setInt(2, customer.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bets = new ArrayList<>();
                bets.add(pickBetFromResultSet(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for getting customer's bets", e);
        }
        return bets;
    }

    private Bet pickBetFromResultSet(ResultSet resultSet) throws DaoException {
        Bet bet = new Bet();
        try {
            bet.setId(resultSet.getInt(1));
            bet.setValue(Money.of(CurrencyUnit.of("KZT"), resultSet.getDouble(2)));
            bet.setPossibleGain(Money.of(CurrencyUnit.of("KZT"), resultSet.getDouble(4)));
            bet.setFinalCoefficient(resultSet.getDouble(5));
            bet.setFinalResult(resultSet.getBoolean(6));
            bet.setDate(new DateTime(resultSet.getTimestamp(7)));
        } catch (SQLException e) {
            throw new DaoException("Cannot pick bet from result set", e);
        }
        return bet;
    }
}
