package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Bet;
import com.epam.ilya.model.Customer;
import com.epam.ilya.model.PaginatedList;
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
    static final Logger log = LoggerFactory.getLogger(CustomerDao.class);
    private String FIND_BY_ID = "SELECT * FROM customers WHERE id = ?";
    private String UPDATE_CUSTOMER = "UPDATE customers SET first_name = ? , last_name = ? ,password=?, email = ? WHERE id = ?";
    private String UPDATE_CUSTOMERS_AVATAR = "UPDATE customers SET avatar_id=?  WHERE id = ?";
    private String DELETE_CUSTOMER = "DELETE FROM customers WHERE id = ?";
    private String INSERT_CUSTOMER = "Insert INTO customers VALUES (id,?,?,?,?,?,?,NULL)";
    private String FIND_ALL = "SELECT * FROM customers WHERE active = ?";
    private String FIND_ALL_IN_RANGE = "SELECT * FROM customers JOIN cash_accounts ON customers.purse_id=cash_accounts.id  WHERE active = ? order by cash_accounts.balance desc LIMIT ?,?";
    private String GET_BETS_CUSTOMER = "SELECT id, firstName, lastName, password, email FROM customers JOIN customers_bets ON customers.id=customers_bets.customer_id WHERE customers_bets.bets_id=?";
    private String CUSTOMERS_COUNT = "SELECT count(*) FROM bets.customers where active =1";


    @Override
    public Customer create(Customer customer) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_CUSTOMER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getPassword());
            statement.setString(4, customer.getEmail());
            statement.setBoolean(5, true);
            statement.setInt(6, customer.getPersonsPurse().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                log.info("Set generated id = {} to customer", id);
                customer.setId(id);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create customer in database", e);
        }
        return customer;
    }

    @Override
    public Customer findById(int id) throws DaoException {
        Customer customer = new Customer();
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customer = pickCustomerFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id", e);
        }
        return customer;
    }

    @Override
    public void update(Customer customer) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_CUSTOMER)) {
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getPassword());
            statement.setString(4, customer.getEmail());
            statement.setInt(5, customer.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for updating customer", e);
        }


    }

    @Override
    public void delete(Customer customer) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(DELETE_CUSTOMER)) {
            statement.setInt(1, customer.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for deleting customer", e);
        }
    }

    public List<Customer> getAllActiveCustomers(int pageNumber, int pageSize) throws DaoException {
        List<Customer> customers = new PaginatedList<>(pageNumber, pageSize);
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_ALL_IN_RANGE)) {
            statement.setBoolean(1,Dao.ACTIVE);
            statement.setInt(2,((pageNumber-1)*pageSize));
            statement.setInt(3,pageSize);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customers.add(pickCustomerFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for finding all customers", e);
        }
        return customers;
    }

    public List<Customer> findByParameters(Map<String, String> parameters) throws DaoException {
        List<Customer> customers = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(createQuery(parameters))) {
            while (resultSet.next()) {
                customers.add(pickCustomerFromResultSet(resultSet));
            }
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

    public Customer getBetsCustomer(Bet bet) throws DaoException {
        Customer customer = null;
        try (PreparedStatement statement = getConnection().prepareStatement(GET_BETS_CUSTOMER)) {
            statement.setInt(1, bet.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customer = pickCustomerFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for getting customer by bet", e);
        }
        return customer;
    }

    public Customer pickCustomerFromResultSet(ResultSet resultSet) throws DaoException {
        Customer customer = new Customer();
        try {
            customer.setId(resultSet.getInt(1));
            customer.setFirstName(resultSet.getString(2));
            customer.setLastName(resultSet.getString(3));
            customer.setPassword(resultSet.getString(4));
            customer.setEmail(resultSet.getString(5));
        } catch (SQLException e) {
            throw new DaoException("Cannot create customer from resultSet", e);
        }
        return customer;
    }

    public void updateAvatar(Customer customer) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_CUSTOMERS_AVATAR)) {
            statement.setInt(1, customer.getAvatar().getId());
            statement.setInt(2, customer.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for updating customer", e);
        }
    }

    public int getCustomersCount() throws DaoException {
        int count = 0;
        try(Statement statement= getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(CUSTOMERS_COUNT);
            while (resultSet.next()){
                count = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for counting customers",e);
        }
        return count;
    }
}


