package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Match;
import com.epam.ilya.model.PaginatedList;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MatchDao extends Dao implements EntityDao<Match> {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(Match.class));
    private static final String FIND_ALL_IN_RANGE = "SELECT * FROM matches WHERE active=? limit ?,?";
    private static final String FIND_BY_ID = "SELECT * FROM matches WHERE id=?";
    private static final String INSERT_MATCH = "INSERT INTO matches VALUES (id,?,?,?,?,?,0)";
    private static final String MATCHS_STATUS = "update matches set active = ?  where id=?";
    private static final String MATCH_COUNT = "SELECT count(*) FROM bets.matches where active =?";

    @Override
    public Match create(Match match) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_MATCH, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, match.getSportsName());
            statement.setString(2, match.getLeaguesName());
            statement.setTimestamp(3, new Timestamp(match.getDate().getMillis()));
            statement.setString(4, match.getFirstSidesName());
            statement.setString(5, match.getSecondSidesName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int id;
            id = resultSet.getInt(1);
            log.debug("Set id - {} to match - {}", id, match);
            match.setId(id);
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for insert match", e);
        }
        return match;
    }

    @Override
    public Match findById(int id) throws DaoException {
        Match match = null;
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                match = pickMatchFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for finding by id", e);
        }

        return match;
    }

    @Override
    public void update(Match match) throws DaoException {

    }

    @Override
    public void delete(Match match) throws DaoException {

    }

    public PaginatedList<Match> getAllMatches(boolean status, int pageNumber, int pageSize) throws DaoException {
        PaginatedList<Match> matches = new PaginatedList<>(pageNumber,pageSize);
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_ALL_IN_RANGE)) {
            statement.setBoolean(1, status);
            statement.setInt(2,((pageNumber-1)*pageSize));
            statement.setInt(3,pageSize);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                matches.add(pickMatchFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for finding all matches", e);
        }
        return matches;
    }

    public void setStatus(Match match, boolean status) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(MATCHS_STATUS)) {
            statement.setBoolean(1, status);
            statement.setInt(2, match.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for making match active", e);
        }
    }

    private Match pickMatchFromResultSet(ResultSet resultSet) throws DaoException {
        Match match = new Match();
        try {
            match.setId(resultSet.getInt(1));
            match.setSportsName(resultSet.getString(2));
            match.setLeaguesName(resultSet.getString(3));
            match.setDate(new DateTime(resultSet.getTimestamp(4)));
            match.setFirstSidesName(resultSet.getString(5));
            match.setSecondSidesName(resultSet.getString(6));
        } catch (SQLException e) {
            throw new DaoException("Cannot create match from result set", e);
        }
        return match;
    }

    public int getMatchCount(boolean status) throws DaoException {
        int count = 0;
        try(PreparedStatement statement=  getConnection().prepareStatement(MATCH_COUNT)) {
            statement.setBoolean(1,status);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                count = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for counting matches",e);
        }
        return count;
    }
}
