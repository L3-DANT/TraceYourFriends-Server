package main.com.traceyourfriend.dao;


import main.com.traceyourfriend.util.ToJSON;
import org.codehaus.jettison.json.JSONArray;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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

    private final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private final String SQL_SELECT_USER_BY_NAME = "SELECT * FROM users WHERE name = ?";
    private final String SQL_SELECT_USER = "SELECT * FROM users";

    MySQLDao mySQLDao = MySQLDao.getDbCon();

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
    public JSONArray findWithEmail(String email) throws Exception {


        ToJSON converter = new ToJSON();
        JSONArray json = new JSONArray();

        try{
            PreparedStatement preparedStatement = mySQLDao.conn.prepareStatement(SQL_SELECT_USER_BY_EMAIL);
            preparedStatement.setString(1, email); //protect against sql injection
            ResultSet resultSet = preparedStatement.executeQuery();

            json = converter.toJSONArray(resultSet);
            preparedStatement.close();

        }catch(SQLException sqlError) {
            sqlError.printStackTrace();
            return json;
        }
        catch(Exception e) {
            e.printStackTrace();
            return json;
        }

        return json;

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
    public JSONArray findWithName(String name) throws Exception {

        ToJSON converter = new ToJSON();
        JSONArray json = new JSONArray();

        try{
            PreparedStatement preparedStatement = mySQLDao.conn.prepareStatement(SQL_SELECT_USER_BY_NAME);
            preparedStatement.setString(1, name); //protect against sql injection
            ResultSet resultSet = preparedStatement.executeQuery();

            json = converter.toJSONArray(resultSet);
            preparedStatement.close();

        }catch(SQLException sqlError) {
            sqlError.printStackTrace();
            return json;
        }
        catch(Exception e) {
            e.printStackTrace();
            return json;
        }

        return json;
    }


    /**
     * This method will return all users.
     *
     * @return - all users in json format
     * @throws Exception
     */

    @Override
    public JSONArray findAll() throws Exception {

        MySQLDao mySQLDao = MySQLDao.getDbCon();
        ToJSON converter = new ToJSON();
        JSONArray json = new JSONArray();

        try{
            PreparedStatement preparedStatement = mySQLDao.conn.prepareStatement(SQL_SELECT_USER);

            ResultSet resultSet = preparedStatement.executeQuery();
            json = converter.toJSONArray(resultSet);
            preparedStatement.close();

        }catch(SQLException sqlError) {
            sqlError.printStackTrace();
            return json;
        }
        catch(Exception e) {
            e.printStackTrace();
            return json;
        }

        return json;

    }
}
