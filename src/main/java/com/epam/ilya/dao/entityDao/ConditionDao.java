package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Condition;
import com.epam.ilya.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConditionDao extends Dao implements EntityDao<Condition> {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(ConditionDao.class));
    private static String INSERT_CONDITION = "INSERT INTO conditions VALUES (id,?,?,NULL)";
    private static String SET_CONDITION_TO_MATCH = "INSERT INTO matches_conditions VALUES (?,?)";
    private static String FIND_BY_ID = "SELECT * FROM conditions WHERE id=?";
    private static String GET_MATCHS_CONDITIONS ="SELECT id , conditionsName, coefficient FROM conditions JOIN matches_conditions on conditions.id = matches_conditions.condition_id WHERE matches_conditions.match_id=?";

    @Override
    public Condition create(Condition condition) throws DaoException {
        try {
            PreparedStatement statement = getConnection().prepareStatement(INSERT_CONDITION, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, condition.getConditionsName());
            statement.setDouble(2, condition.getCoefficient());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);
            log.debug("Set id = {} to condition - {}", id, condition);
            condition.setId(id);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for insert condition", e);
        }
        return condition;
    }

    @Override
    public Condition findById(int id) throws DaoException {
        Condition condition = null;
        try {
            PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                condition = pickConditionFromResultSet(resultSet);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create dao for finding by id", e);
        }
        return condition;
    }

    @Override
    public void update(Condition condition) throws DaoException {

    }

    @Override
    public void delete(Condition condition) throws DaoException {

    }

    public void addConditionToMatch(Condition condition, Match match) throws DaoException {
        try {
            PreparedStatement statement = getConnection().prepareStatement(SET_CONDITION_TO_MATCH);
            statement.setInt(1, match.getId());
            statement.setInt(2, condition.getId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for set condition to match", e);
        }

    }

    public List<Condition> getMatchsCondition(Match match) throws DaoException {
        List<Condition> conditions = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(GET_MATCHS_CONDITIONS);
            statement.setInt(1, match.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                conditions.add(pickConditionFromResultSet(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for get conditions",e);
        }
        return conditions;
    }


    private Condition pickConditionFromResultSet(ResultSet resultSet) throws DaoException {
        Condition condition = new Condition();
        try {
            condition.setId(resultSet.getInt(1));
            condition.setConditionsName(resultSet.getString(2));
            condition.setCoefficient(resultSet.getDouble(3));
        } catch (SQLException e) {
            throw new DaoException("Cannot pick condition from result set", e);
        }
        return condition;
    }

}
