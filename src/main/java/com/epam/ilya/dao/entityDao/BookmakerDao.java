package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Bookmaker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookmakerDao extends Dao implements EntityDao<Bookmaker> {
    private static String GET_BOOKMAKER_BY_EMAIL = "SELECT * FROM bookmakers WHERE email = ?";
    private static String GET_BOOKMAKER_BY_EMAIL_PASSWORD = "SELECT * FROM bookmakers WHERE email=? AND  password=?";

    @Override
    public Bookmaker create(Bookmaker bookmaker) throws DaoException {
        return null;
    }

    @Override
    public Bookmaker findById(int id) throws DaoException {
        return null;
    }

    @Override
    public void update(Bookmaker bookmaker) throws DaoException {

    }

    @Override
    public void delete(Bookmaker bookmaker) throws DaoException {

    }

    public Bookmaker getBookmaker(String email, String password) throws DaoException {
        Bookmaker bookmaker = null;
        try (PreparedStatement statement = getConnection().prepareStatement(GET_BOOKMAKER_BY_EMAIL_PASSWORD)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookmaker = pickBookmakerFromResultSet(resultSet);
            }
            resultSet.close();
            return bookmaker;
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for finding bookmaker", e);
        }
    }

    private Bookmaker pickBookmakerFromResultSet(ResultSet resultSet) throws DaoException {
        Bookmaker bookmaker = new Bookmaker();
        try {
            bookmaker.setId(resultSet.getInt(1));
            bookmaker.setFirstName(resultSet.getString(2));
            bookmaker.setLastName(resultSet.getString(3));
            bookmaker.setEmail(resultSet.getString(5));
            bookmaker.setPassword(resultSet.getString(4));
        } catch (SQLException e) {
            throw new DaoException("Cannot create bookmaker from resultSet", e);
        }
        return bookmaker;
    }

    public Bookmaker getBookmaker(String email) throws DaoException {
        Bookmaker bookmaker = null;
        try (PreparedStatement statement = getConnection().prepareStatement(GET_BOOKMAKER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookmaker = pickBookmakerFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for finding by email", e);
        }
        return bookmaker;
    }
}
