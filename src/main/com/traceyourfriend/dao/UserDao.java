package main.com.traceyourfriend.dao;

import main.com.traceyourfriend.beans.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDao implements DAO<User> {

    private final String SQL_INSERT_USER = "INSERT INTO users (email, password) VALUES (?, ?)";
    private final String SQL_DELETE_USER = "DELETE FROM users WHERE email = ?";
    private final String SQL_UPDATE_USER = "UPDATE users SET email = ?, password = ? WHERE id = ? ";
    private final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private final String SQL_SELECT_USER = "SELECT * FROM users";

    @Override
    public User add(User object) throws SQLException {
        PreparedStatement preparedStatement = MySQLDao.conn.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, object.getMail());
        preparedStatement.setString(2,object.getPassword());

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()){
            object.setId(resultSet.getLong(1));
        }
        resultSet.close();
        preparedStatement.close();
        return object;
    }

    @Override
    public void remove(User object) throws SQLException {
        PreparedStatement preparedStatement = MySQLDao.conn.prepareStatement(SQL_DELETE_USER);
        preparedStatement.setString(1, object.getMail());

        preparedStatement.execute();
        preparedStatement.close();

    }

    @Override
    public User merge(User object) throws SQLException {
        PreparedStatement preparedStatement = MySQLDao.conn.prepareStatement(SQL_UPDATE_USER);
        preparedStatement.setString(1, object.getMail());
        preparedStatement.setString(2, object.getPassword());
        preparedStatement.setLong(3, object.getId());

        preparedStatement.execute();
        preparedStatement.close();
        return object;
    }

    @Override
    public User find(long id) throws SQLException {

        User user = new User();
        PreparedStatement preparedStatement = MySQLDao.conn.prepareStatement(SQL_SELECT_USER_BY_ID);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            user.setId(resultSet.getLong("id"));
            user.setMail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            resultSet.close();
            preparedStatement.close();
            return user;
        }
        resultSet.close();
        preparedStatement.close();
        return null;
    }

    @Override
    public User find(String email) throws SQLException {
        User user = new User();
        PreparedStatement preparedStatement = MySQLDao.conn.prepareStatement(SQL_SELECT_USER_BY_EMAIL);
        preparedStatement.setString(1, email);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            user.setId(resultSet.getLong(1));
            user.setMail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));

            resultSet.close();
            preparedStatement.close();
            return user;
        }
        resultSet.close();
        preparedStatement.close();
        return null;
    }

    @Override
    public List<User> findAll() throws SQLException {

        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = MySQLDao.conn.prepareStatement(SQL_SELECT_USER);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setMail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            users.add(user);
        }
        resultSet.close();
        preparedStatement.close();
        return users;
    }
}
