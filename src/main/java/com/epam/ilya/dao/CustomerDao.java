package com.epam.ilya.dao;

import com.epam.ilya.connection.ConnectionPool;
import com.epam.ilya.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDao implements EntityDao<Customer> {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    private String INSERT_CUSTOMER = "Insert INTO users VALUES (id,?,?,?,?,?)";
    private String FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
    private String UPDATE_CUSTOMER = "UPDATE users SET first_name = ? , last_name = ? , email = ? , password=?, purse_id=? WHERE id = ?";
    private String DELETE_CUSTOMER = "DELETE FROM users WHERE id = ?";

    @Override
    public void create(Customer customer) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_CUSTOMER);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.setInt(5, customer.getPersonsPurse().getId());
            statement.close();
            connectionPool.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }// обязательно закрывать всё в блоке файнали ?
    }

    @Override
    public Customer findById(int id) {
        Customer customer = new Customer();
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            customer.setId(id);
            customer.setFirstName(resultSet.getString("first_name"));
            customer.setLastName(resultSet.getString("last_name"));
            customer.setEmail(resultSet.getString("email"));
            customer.setPassword(resultSet.getString("password"));
            // TODO : set person purse
            resultSet.close();
            statement.close();
            connectionPool.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public void update(Customer customer) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER);
            statement.setString(1,customer.getFirstName());
            statement.setString(2,customer.getLastName());
            statement.setString(3,customer.getEmail());
            statement.setString(4,customer.getPassword());
            statement.setInt(5,customer.getPersonsPurse().getId());
            statement.setInt(6,customer.getId());
            statement.execute();
            statement.close();
            connectionPool.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void delete(Customer customer) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER);
            statement.setInt(1,customer.getId());
            statement.execute();
            statement.close();
            connectionPool.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
