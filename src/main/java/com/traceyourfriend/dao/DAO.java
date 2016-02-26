package com.traceyourfriend.dao;

import com.traceyourfriend.beans.User;

import java.sql.SQLException;
import java.util.List;

public interface DAO <T>{

    //T add(T object) throws SQLException;

    //void remove(T object)  throws SQLException;

    //T merge(T object) throws SQLException;

    User findWithEmail(String email) throws SQLException;

    User findWithName(String name) throws SQLException;

    List<User> findAll() throws SQLException;

}