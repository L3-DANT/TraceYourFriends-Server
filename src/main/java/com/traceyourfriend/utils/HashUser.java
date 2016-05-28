package com.traceyourfriend.utils;


import com.traceyourfriend.beans.User;
import com.traceyourfriend.dao.UsersDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashUser {

    private final ConcurrentHashMap<String, User> usersMail = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, User> usersName = new ConcurrentHashMap<>();
    private final UsersDAO dao = new UsersDAO();

    /** Constructeur privé */
    private HashUser(){
    }

    /** Instance unique non préinitialisée */
    public static final HashUser INSTANCE = new HashUser();

    public static HashUser getInstance() {
        return INSTANCE;
    }

    public User searchHash(String emailOrName) throws SQLException {
        User user = usersMail.get(emailOrName);
        if (user != null) {
            return user;
        }
        user = usersName.get(emailOrName);
        if (user != null) {
            return user;
        }
        user = dao.search(emailOrName);
        if (user != null) {
            usersMail.put(emailOrName, user);
            usersName.put(emailOrName, user);
            dao.loadFriends(user);
        }
        return user;
    }

    public ArrayList<String> searchListContacts(String recherche){
        ArrayList<String> contactInSearch = new ArrayList<>();
        for (Map.Entry<String, User> e : usersName.entrySet()) {
            if (e.getKey().startsWith(recherche)) {
                contactInSearch.add(e.getValue().getName());
            }
        }
        return contactInSearch;
    }
}
