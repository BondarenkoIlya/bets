package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Condition;
import com.epam.ilya.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class BetDao extends Dao implements EntityDao<Bet> {
    public static final boolean ACTIVE = true;
    public static final boolean INACTIVE = false;
    static final Logger log = LoggerFactory.getLogger(String.valueOf(BetDao.class));
    private String FIND_BY_ID = "SELECT * FROM bets WHERE id = ?";
    private String UPDATE_BET = "UPDATE bets SET possibleGain = ? , finalCoefficient=? WHERE id = ?";
    private String DELETE_BET = "DELETE FROM bets WHERE id = ?";
    private String INSERT_BET = "Insert INTO bets VALUES (id,?,?,?,?,?,?,FALSE)";
    private String ADD_CONDITION_TO_BET = "INSERT INTO bets_conditions VALUES (?,?)";
    private String BETS_STATUS = "update bets set  active = ?  where id=?";
    private String ADD_BET_TO_CUSTOMER = "INSERT INTO customers_bets VALUES (?,?)";

    @Override
    public Bet create(Bet bet) throws DaoException {
        try {
            PreparedStatement statement = getConnection().prepareStatement(INSERT_BET, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, bet.getValue().getAmount().doubleValue());
            statement.setDouble(2, bet.getCustomer().getId());
            statement.setDouble(3, bet.getPossibleGain().getAmount().doubleValue());
            statement.setDouble(4, bet.getFinalCoefficient());
            statement.setBoolean(5, bet.isFinalResult());
            statement.setTimestamp(6, new Timestamp(bet.getDate().getMillis()));
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
            statement.setDouble(1,bet.getPossibleGain().getAmount().doubleValue());
            statement.setDouble(2,bet.getFinalCoefficient());
            statement.setInt(3,bet.getId());
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
            statement.setInt(1,customer.getId());
            statement.setInt(2,bet.getId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create stetement for adding bet to customer",e);
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


}
