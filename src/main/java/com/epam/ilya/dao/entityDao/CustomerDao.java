package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDao extends Dao implements EntityDao<Customer> {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(CustomerDao.class));
    private String FIND_BY_ID = "SELECT * FROM customers WHERE id = ?";
    private String UPDATE_CUSTOMER = "UPDATE customers SET first_name = ? , last_name = ? ,password=?, email = ?   WHERE id = ?";
    private String DELETE_CUSTOMER = "DELETE FROM customers WHERE id = ?";
    private String INSERT_CUSTOMER = "Insert INTO customers VALUES (id,?,?,?,?)";
    private String FIND_BY_EMAIL_PASSWORD = "SELECT * FROM customers WHERE email=? AND password=?";

    @Override
    public Customer create(Customer customer) throws DaoException {
        try {
            PreparedStatement statement = getConnection().prepareStatement(INSERT_CUSTOMER,PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                log.info("Set generated id = {} to customer",id);
                customer.setId(id);
            }
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create customer in database",e);
        }
        return customer;
    }

    @Override
    public Customer findById(int id) {
        Customer customer = new Customer();
        try {
            PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                customer.setId(id);
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPassword(resultSet.getString("password"));
            }

            /*CashAccountDao cashAccountDao = new CashAccountDao();
            CashAccount purse = cashAccountDao.findById(resultSet.getInt("purse_id"));
            customer.setPersonsPurse(purse);*/

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public void update(Customer customer) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(UPDATE_CUSTOMER);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getPassword());
            statement.setString(4, customer.getEmail());
            statement.setInt(5, customer.getId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void delete(Customer customer) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(DELETE_CUSTOMER);
            statement.setInt(1, customer.getId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer findByEmailAndPassword(String email, String password) throws DaoException {
        Customer customer=null;
        try {
            customer = new Customer();
            PreparedStatement statement = getConnection().prepareStatement(FIND_BY_EMAIL_PASSWORD);
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                customer.setId(resultSet.getInt("id"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString(email));
                customer.setPassword(resultSet.getString(password));
            }
            resultSet.close();
            statement.close();
        }catch (SQLException e){
            throw new DaoException("Cannot get prepare statement",e);
        }
        return customer;
    }
}
