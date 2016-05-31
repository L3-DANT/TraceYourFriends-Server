package com.traceyourfriend.utils;


import com.traceyourfriend.beans.User;
import com.traceyourfriend.dao.UsersDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
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
            usersMail.put(user.getMail(), user);
            usersName.put(user.getName(), user);
            dao.loadFriends(user);
            dao.loadRequests(user);
            dao.loadInvitations(user);
        }
        return user;
    }

    public ArrayList<String> searchListContacts(String recherche) throws SQLException {
        ArrayList<String> contactInSearch = new ArrayList<>();
        List<String> poeple = dao.loadPoeple(recherche);

        for (String p: poeple) {
            User user = dao.search(p);
            if (user != null) {
                searchHash(user.getName());
                contactInSearch.add(user.getName());
            }
        }
        return contactInSearch;
    }
}
