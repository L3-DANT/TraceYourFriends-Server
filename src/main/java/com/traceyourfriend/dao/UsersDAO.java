package com.traceyourfriend.dao;


import com.traceyourfriend.beans.User;

import java.sql.*;
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
    private static final String SQL_SELECT_USER_BY_NAME = "SELECT * FROM users WHERE name = ?";
	private static final String SQL_INSERT_USER = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_USER = "SELECT * FROM users";

    private final Connection connection = SQLConnection.getSQLCon().getDbCon();

	/**
	 * This method will create a users in the users table.
	 * By using prepareStatement and the ?, we are protecting against sql injection
	 *
	 * Never add parameter straight into the prepareStatement
	 *
	 * @param user - a user
	 * @return - return the user created
	 * @throws Exception
	 */

	@Override
	public int add(User user) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)){
			/**
			 *	Important: The primary key on users table will auto increment.
			 * 		That means the Pid column does not need to be apart of the
			 * 		SQL insert query below.
			 */
			preparedStatement.setString(1, user.getName()); //protect against sql injection
			preparedStatement.setString(2, user.getMail()); //protect against sql injection
			preparedStatement.setString(3, user.getPassword()); //protect against sql injection
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()){
				user.setId(resultSet.getLong(1));
			}
			resultSet.close();
			preparedStatement.close();
			return 200;
		}catch (SQLException e){
			e.printStackTrace();
			return 500;
		}
	}

	/**
	 * This method will search for a specific users with via his email from the users table.
	 * By using prepareStatement and the ?, we are protecting against sql injection
	 *
	 * Never add parameter straight into the prepareStatement
	 *
	 * @param emailOrName - email user
	 * @return - json array of the results from the database
	 * @throws Exception
	 */

	@Override
	public User search(String emailOrName) throws SQLException {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_EMAIL);
			preparedStatement.setString(1, emailOrName); //protect against sql injection
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()){
				return resultNext(resultSet, preparedStatement);
			}
			PreparedStatement preparedStatement1 = connection.prepareStatement(SQL_SELECT_USER_BY_NAME);
			preparedStatement1.setString(1, emailOrName); //protect against sql injection
			ResultSet resultSet1 = preparedStatement1.executeQuery();
			if (resultSet1.next()){
				return resultNext(resultSet1, preparedStatement1);
			}
			resultSet.close();
			resultSet1.close();
			preparedStatement.close();
			preparedStatement1.close();
			return null;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}

	public User resultNext(ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException{
		User u = convertFromResultSet(resultSet);
		preparedStatement.close();
		resultSet.close();
		return u;
	}

	/**
	 * This method will search for a specific users with via his name from the users table.
	 * By using prepareStatement and the ?, we are protecting against sql injection
	 *
	 * Never add parameter straight into the prepareStatement
	 *
	 * @param name - product brand
	 * @return - json array of the results from the database
	 * @throws Exception
	 */

	@Override
	public User findWithName(String name) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_NAME)){
			preparedStatement.setString(1, name); //protect against sql injection
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
		User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
		user.setId(resultSet.getInt(1));
		return user;
	}


}
