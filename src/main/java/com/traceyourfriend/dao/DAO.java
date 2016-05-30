package com.traceyourfriend.dao;

import com.traceyourfriend.beans.User;

import java.sql.SQLException;
import java.util.List;

public interface DAO <T>{

    int add(User user) throws SQLException;

    List<User> findAll() throws SQLException;

    User search(String email) throws SQLException;

    List<String> loadFriends(User user) throws SQLException;

    List<String> loadRequests(User user) throws SQLException;

    List<String> loadInvitations(User user) throws SQLException;
}
