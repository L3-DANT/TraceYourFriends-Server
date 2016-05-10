package com.traceyourfriend.utils;


import com.traceyourfriend.beans.User;
import com.traceyourfriend.dao.UsersDAO;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public class HashUser {

    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();;
    private final UsersDAO dao = new UsersDAO();

    /** Constructeur privé */
    private HashUser(){
    }

    /** Instance unique non préinitialisée */
    public static final HashUser INSTANCE = new HashUser();

    public static HashUser getInstance() {
        return INSTANCE;
    }

    public User searchHash(String email) throws SQLException {
        User user = users.get(email);
        if (user != null) {
            return user;
        }
        user = dao.search(email);
        if (user != null) {
            users.put(email, user);
        }
        return user;
    }
}
