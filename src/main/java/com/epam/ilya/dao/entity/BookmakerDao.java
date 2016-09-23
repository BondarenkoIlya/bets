package com.epam.ilya.dao.entity;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Bookmaker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookmakerDao extends Dao implements EntityDao<Bookmaker> {
    private static final String GET_BOOKMAKER_BY_EMAIL = "SELECT * FROM bookmakers WHERE email = ?";
    private static final String GET_BOOKMAKER_BY_EMAIL_PASSWORD = "SELECT * FROM bookmakers WHERE email=? AND  password=?";
    private static final String FIND_BY_ID = "SELECT * FROM bookmakers WHERE id = ?";
    private static final String UPDATE_BOOKMAKER = "UPDATE bookmakers SET first_name = ? , last_name = ? ,password=?, email = ? WHERE id = ?";
    private static final String DELETE_BOOKMAKER = "DELETE FROM bookmakers WHERE id = ?";
    private static final String INSERT_BOOKMAKER = "Insert INTO bookmakers VALUES (id,?,?,?,?,?)";

    @Override
    public Bookmaker create(Bookmaker bookmaker) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_BOOKMAKER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, bookmaker.getFirstName());
            statement.setString(2, bookmaker.getLastName());
            statement.setString(3, bookmaker.getPassword());
            statement.setString(4, bookmaker.getEmail());
            statement.setInt(5, bookmaker.getPersonsPurse().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                bookmaker.setId(id);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create bookmaker in database", e);
        }
        return bookmaker;
    }

    @Override
    public Bookmaker findById(int id) throws DaoException {
        Bookmaker bookmaker = new Bookmaker();
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookmaker = pickBookmakerFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id", e);
        }
        return bookmaker;
    }

    @Override
    public void update(Bookmaker bookmaker) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_BOOKMAKER)) {
            statement.setString(1, bookmaker.getFirstName());
            statement.setString(2, bookmaker.getLastName());
            statement.setString(3, bookmaker.getPassword());
            statement.setString(4, bookmaker.getEmail());
            statement.setInt(5, bookmaker.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for updating bookmaker", e);
        }
    }

    @Override
    public void delete(Bookmaker bookmaker) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(DELETE_BOOKMAKER)) {
            statement.setInt(1, bookmaker.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for deleting bookmaker", e);
        }
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
