package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.CashAccount;
import com.epam.ilya.model.Customer;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CashAccountDao extends Dao implements EntityDao<CashAccount> {
    private String INSERT_PURSE = "Insert INTO cash_accounts VALUES (id,?,?)";
    private String FIND_BY_ID = "SELECT * FROM cash_accounts WHERE id = ?";
    private String FIND_BY_CUSTOMER = "SELECT * FROM cash_accounts WHERE owner_id = ?";
    private String UPDATE_PURSE = "UPDATE cash_accounts SET balance = ? WHERE id = ?";
    private String DELETE_PURSE = "DELETE FROM cash_accounts WHERE id = ?";

    @Override
    public CashAccount create(CashAccount cashAccount) {
        /*try {
            PreparedStatement statement = getConnection().prepareStatement(INSERT_PURSE);
            statement.setDouble(1, cashAccount.getBalance().getAmount().doubleValue());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CashAccount registeredCashAccount = new CashAccount();*/
        return null;
    }

    @Override
    public CashAccount findById(int id) throws DaoException {
        CashAccount purse = new CashAccount();
        try {
            PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            purse.setId(id);
            purse.setBalance(Money.of(CurrencyUnit.of("KZT"), resultSet.getDouble("balance")));

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id cash account",e);
        }
        return purse;
    }

    @Override
    public void update(CashAccount cashAccount) throws DaoException {
        try {
            PreparedStatement statement = getConnection().prepareStatement(UPDATE_PURSE);
            statement.setDouble(1, cashAccount.getBalance().getAmount().doubleValue());
            statement.setInt(2, cashAccount.getId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot update cash account",e);
        }
    }

    @Override
    public void delete(CashAccount cashAccount) throws DaoException {
        try {
            PreparedStatement statement = getConnection().prepareStatement(DELETE_PURSE);
            statement.setInt(1, cashAccount.getId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete cash account",e);
        }
    }

    public CashAccount createPurseForPerson(Customer registeredCustomer) throws DaoException {
        CashAccount cashAccount = new CashAccount();
        try {
            PreparedStatement statement = getConnection().prepareStatement(INSERT_PURSE,PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, cashAccount.getBalance().getAmount().doubleValue());
            statement.setInt(2, registeredCustomer.getId());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()){
                cashAccount.setId(resultSet.getInt(1));
            }
            cashAccount.setPerson(registeredCustomer);
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create cash account for registered customer {}",e);
        }
        return cashAccount;
    }

    public CashAccount findByCustomers(Customer customer) throws DaoException {
        CashAccount cashAccount=null;
        try {
            cashAccount = new CashAccount();
            PreparedStatement statement = getConnection().prepareStatement(FIND_BY_CUSTOMER);
            statement.setInt(1,customer.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                cashAccount.setBalance(Money.of(Money.of(CurrencyUnit.of("KZT"), resultSet.getDouble("balance"))));
                cashAccount.setPerson(customer);
                cashAccount.setId(resultSet.getInt(1));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement",e);
        }
        return cashAccount;
    }
}
