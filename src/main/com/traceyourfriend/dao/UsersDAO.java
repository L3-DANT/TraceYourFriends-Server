package main.com.traceyourfriend.dao;


import main.com.traceyourfriend.beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * This java class will hold all the sql queries.
 *
 * Having all sql/database code in one package makes it easier to maintain and audit
 * but increase complexity.
 *
 *
 * @author Aniss
 */

public class UsersDAO implements DAO{

    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String SQL_SELECT_USER = "SELECT * FROM users";

    private final Connection connection = SQLConnection.db.getDbCon();

    /**
     * This method will search for a specific users with via his email from the users table.
     * By using prepareStatement and the ?, we are protecting against sql injection
     *
     * Never add parameter straight into the prepareStatement
     *
     * @param email - product brand
     * @return - json array of the results from the database
     * @throws Exception
     */

    @Override
    public User findWithEmail(String email) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_EMAIL)){
            preparedStatement.setString(1, email); //protect against sql injection
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return convertFromResultSet(resultSet);
				}
			}
        }
		return null;
    }

    /**
     * This method will return all users.
     *
     * @return - all users in json format
     * @throws Exception
     */

    @Override
    public List<User> findAll() throws SQLException {
		List<User> users = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER)) {
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					users.add(convertFromResultSet(resultSet));
				}
			}
		}
		return users;
    }



	private User convertFromResultSet(ResultSet resultSet) throws SQLException {
		User user = new User(resultSet.getString(2), resultSet.getString(3));
		user.setId(resultSet.getInt(1));
		return user;
	}
}
