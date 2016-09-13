package com.epam.ilya.dao.entityDao;

import com.epam.ilya.dao.Dao;
import com.epam.ilya.dao.DaoException;
import com.epam.ilya.model.Avatar;
import com.epam.ilya.model.Customer;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AvatarDao extends Dao implements EntityDao<Avatar> {
    static final Logger log = LoggerFactory.getLogger(String.valueOf(AvatarDao.class));
    private String INSERT_AVATAR = "Insert INTO avatars VALUES (id,?,?)";
    private String FIND_BY_CUSTOMER = "SELECT avatars.id , avatars.picture , avatars.date FROM avatars JOIN customers ON customers.avatar_id=avatars.id WHERE customers.id=?";

    @Override
    public Avatar create(Avatar avatar) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_AVATAR, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setBlob(1, avatar.getPicture());
            statement.setTimestamp(2,new Timestamp(avatar.getCreationDate().getMillis()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            avatar.setId(resultSet.getInt(1));
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for insert avatar", e);
        }
        return avatar;
    }

    @Override
    public Avatar findById(int id) throws DaoException {
        return null;
    }

    @Override
    public void update(Avatar avatar) throws DaoException {

    }

    @Override
    public void delete(Avatar avatar) throws DaoException {

    }

    public Avatar findByPerson(Customer customer) throws DaoException {
        Avatar avatar = null;
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_CUSTOMER)) {
            log.debug("Find avatar by customer's id - {}", customer.getId());
            statement.setInt(1, customer.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                log.debug("Pick avatar from result set");
                avatar = pickAvatarFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot create statement for finding avatar by customer", e);
        }
        return avatar;
    }

    private Avatar pickAvatarFromResultSet(ResultSet resultSet) throws DaoException {
        Avatar avatar = null;
        try {
            if (!resultSet.wasNull()){
                avatar = new Avatar();
                avatar.setId(resultSet.getInt(1));
                avatar.setPicture(resultSet.getBinaryStream(2));
                avatar.setCreationDate(new DateTime(resultSet.getTimestamp(3)));
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot pick avatar from result set", e);
        }
        return avatar;
    }
}
