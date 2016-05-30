package com.traceyourfriend.dao;

import com.traceyourfriend.beans.User;

import java.sql.SQLException;
import java.util.List;

public interface DAO <T> {

    int add(User user) throws SQLException;

    List<User> findAll() throws SQLException;

    User search(String email) throws SQLException;

    List<String> loadFriends(User user) throws SQLException;

    List<String> loadRequests(User user) throws SQLException;

    List<String> loadInvitations(User user) throws SQLException;

    void deleteFriend(User current ,User friend) throws SQLException;

    void deleteRequest(User current, User request) throws SQLException;

    void deleteInvitation(User current, User invitation) throws SQLException;

    void acceptFriend(User user, User friend) throws SQLException;

    List<String> loadPoeple(String str) throws SQLException;
}