package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Bet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class BetDao extends Dao implements EntityDao<Bet> {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(BetDao.class));
    private String FIND_BY_ID = "SELECT * FROM bets WHERE id = ?";
    private String UPDATE_BET = "UPDATE bets SET first_name = ? , last_name = ? ,password=?, email = ?   WHERE id = ?";
    private String DELETE_BET = "DELETE FROM bets WHERE id = ?";
    private String INSERT_BET = "Insert INTO bets VALUES (id,?,?,?,?,?,?)";

    @Override
    public Bet create(Bet bet) throws DaoException {
        try {
            PreparedStatement statement = getConnection().prepareStatement(INSERT_BET, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, bet.getCustomer().getId());
            statement.setDouble(2, bet.getValue().getAmount().doubleValue());
            statement.setDouble(3, bet.getPossibleGain().getAmount().doubleValue());
            statement.setDouble(4, bet.getFinalCoefficient());
            statement.setBoolean(5, bet.isFinalResult());
            statement.setTimestamp(6, new Timestamp(bet.getDate().getMillis()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);
            log.info("Set generated id = {} to customer", id);
            bet.setId(id);

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

    }

    @Override
    public void delete(Bet bet) throws DaoException {

    }

}
