package com.epam.ilya.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class RunnerFotPool {
    public static final Logger log = LoggerFactory.getLogger(RunnerFotPool.class);

    public static void main(String[] args) throws ConnectionPoolException {

        String ADD_COLUMN = "ALTER TABLE users ADD COLUMN ? INT (10) NOT NULL ;";
        String INSERT_CUSTOMER = "Insert INTO users VALUES (id,?,?,?,?,?)";
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con1 = connectionPool.getConnection();
        try {
            PreparedStatement statement = con1.prepareStatement(INSERT_CUSTOMER);
           // statement.setInt(1,2);
            statement.setString(1,"Vikdi");
            statement.setString(2,"Borovindskaya");
            statement.setString(3,"vikikdddrg@mail.ru");
            statement.setString(4,"1234d56");
            statement.setInt(5,3);
            statement.execute();
            statement.close();
            connectionPool.closeConnection(con1);
            Connection con2 = connectionPool.getConnection();
            Statement statement1 = con2.createStatement();
            ResultSet resultSet = statement1.executeQuery("SELECT * FROM users");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                System.out.println(id);
            }
            connectionPool.closeConnection(con2);
            Connection con3 = connectionPool.getConnection();
            Connection con4 = connectionPool.getConnection();
            Connection con5 = connectionPool.getConnection();
            Connection con6 = connectionPool.getConnection();
            Connection con7 = connectionPool.getConnection();
            Connection con8 = connectionPool.getConnection();
            Connection con9 = connectionPool.getConnection();
            Connection con10 = connectionPool.getConnection();

        } catch (SQLException e) {
            log.error("Error",e);
            e.printStackTrace();
        }
    }
}
