package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerDao extends Dao implements EntityDao<Customer> {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(CustomerDao.class));
    private String FIND_BY_ID = "SELECT * FROM customers WHERE id = ?";
    private String UPDATE_CUSTOMER = "UPDATE customers SET first_name = ? , last_name = ? ,password=?, email = ?   WHERE id = ?";
    private String DELETE_CUSTOMER = "DELETE FROM customers WHERE id = ?";
    private String INSERT_CUSTOMER = "Insert INTO customers VALUES (id,?,?,?,?,?,purse_id)";
    private String FIND_BY_EMAIL_PASSWORD = "SELECT * FROM customers WHERE email=? AND password=?";
    private String FIND_ALL = "SELECT * FROM customers";
    private String FIND_BY_EMAIL = "SELECT * FROM customers WHERE email=?";


    @Override
    public Customer create(Customer customer) throws DaoException {
        try {
            PreparedStatement statement = getConnection().prepareStatement(INSERT_CUSTOMER, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getPassword());
            statement.setString(4, customer.getEmail());
            statement.setBoolean(5,true);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                log.info("Set generated id = {} to customer", id);
                customer.setId(id);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create customer in database", e);
        }
        return customer;
    }

    @Override
    public Customer findById(int id) throws DaoException {
        Customer customer = new Customer();
        try {
            PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customer = pickCustomerFromResultSet(resultSet);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id", e);
        }
        return customer;
    }

    @Override
    public void update(Customer customer) throws DaoException {
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
            throw new DaoException("Cannot create statement for updating customer",e);
        }


    }

    @Override
    public void delete(Customer customer) throws DaoException {
        try {
            PreparedStatement statement = getConnection().prepareStatement(DELETE_CUSTOMER);
            statement.setInt(1, customer.getId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for deleting customer",e);
        }
    }

    public List<Customer> getAllCustomers() throws DaoException {
        List<Customer> customers = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                customers.add(pickCustomerFromResultSet(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for finding all customers", e);
        }
        return customers;
    }

    public Customer pickCustomerFromResultSet(ResultSet resultSet) throws DaoException {
        Customer customer = new Customer();
        try {
            customer.setId(resultSet.getInt(1));
            customer.setFirstName(resultSet.getString(2));
            customer.setLastName(resultSet.getString(3));
            customer.setEmail(resultSet.getString(5));
            customer.setPassword(resultSet.getString(4));
        } catch (SQLException e) {
            throw new DaoException("Cannot create customer from resultSet", e);
        }
        return customer;
    }

    public List<Customer> findByParameters(Map<String, String> parameters) throws DaoException {
        List<Customer> customers = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(createQuery(parameters));
            while (resultSet.next()) {
                customers.add(pickCustomerFromResultSet(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for finding by parameters", e);
        }
        return customers;
    }

    private String createQuery(Map<String, String> parameters) {
        String query = "SELECT * FROM customers WHERE ";
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            if (parameters.size() == 1) {
                query = query + parameter.getKey() + "='" + parameter.getValue() + "'";
                log.debug("Create query - '{}' for finding by parameter", query);
                return query;
            } else {
                query = query + parameter.getKey() + "='" + parameter.getValue() + "' AND ";
                log.debug("Create query - '{}' for finding by parameters", query.substring(0, query.length() - 5));
            }
        }
        return query.substring(0, query.length() - 5);
    }
}


