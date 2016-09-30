package com.epam.ilya.dao.entity;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Bookmaker;
import com.epam.ilya.model.CashAccount;
import com.epam.ilya.model.Person;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class work with cash account's database parts
 *
 * @author Bondarenko Ilya
 */

public class CashAccountDao extends Dao implements EntityDao<CashAccount> {

    private static final String INSERT_PURSE = "INSERT INTO cash_accounts VALUES (id,?)";
    private static final String FIND_BY_ID = "SELECT * FROM cash_accounts WHERE id = ?";
    private static final String UPDATE_PURSE = "UPDATE cash_accounts SET balance = ? WHERE id = ?";
    private static final String DELETE_PURSE = "DELETE FROM cash_accounts WHERE id = ?";

    @Override
    public CashAccount create(CashAccount cashAccount) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_PURSE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, cashAccount.getBalance().getAmount().doubleValue());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                cashAccount.setId(resultSet.getInt(1));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create cash account for registered customer {}", e);
        }
        return cashAccount;
    }

    @Override
    public CashAccount findById(int id) throws DaoException {
        CashAccount purse = new CashAccount();
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            purse.setId(id);
            while (resultSet.next()) {
                purse.setBalance(Money.of(CurrencyUnit.of(CashAccount.CURRENCY), resultSet.getDouble("balance")));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id cash account", e);
        }
        return purse;
    }

    @Override
    public void update(CashAccount cashAccount) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_PURSE)) {
            statement.setDouble(1, cashAccount.getBalance().getAmount().doubleValue());
            statement.setInt(2, cashAccount.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Cannot update cash account", e);
        }
    }

    @Override
    public void delete(CashAccount cashAccount) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(DELETE_PURSE)) {
            statement.setInt(1, cashAccount.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete cash account", e);
        }
    }

    public CashAccount findByPerson(Person person) throws DaoException {
        CashAccount cashAccount = new CashAccount();
        String tableName;
        if (person instanceof Bookmaker) {
            tableName = "bookmakers";
        } else {
            tableName = "customers";
        }
        try (PreparedStatement statement = getConnection().prepareStatement("select cash_accounts.id , cash_accounts.balance from cash_accounts join " + tableName + " on cash_accounts.id=" + tableName + ".purse_id where " + tableName + ".id=?")) {
            statement.setInt(1, person.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cashAccount.setBalance(Money.of(Money.of(CurrencyUnit.of(CashAccount.CURRENCY), resultSet.getDouble("balance"))));
                cashAccount.setPerson(person);
                cashAccount.setId(resultSet.getInt(1));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for finding person by person", e);
        }
        return cashAccount;
    }
}
