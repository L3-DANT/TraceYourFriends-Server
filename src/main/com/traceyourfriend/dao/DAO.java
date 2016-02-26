package main.com.traceyourfriend.dao;

import main.com.traceyourfriend.beans.User;

import java.sql.SQLException;
import java.util.List;

public interface DAO <T>{

    //T add(T object) throws SQLException;

    //void remove(T object)  throws SQLException;

    //T merge(T object) throws SQLException;

    User findWithEmail(String email) throws SQLException;

    List<User> findAll() throws SQLException;

}
