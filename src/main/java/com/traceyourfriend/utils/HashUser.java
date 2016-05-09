package com.traceyourfriend.utils;


import com.traceyourfriend.beans.User;
import com.traceyourfriend.dao.UsersDAO;

import java.sql.SQLException;
import java.util.Hashtable;

public class HashUser {

    private Hashtable <String, User> users;
    private final UsersDAO dao = new UsersDAO();

    /** Constructeur privé */
    private HashUser(){
        this.users = new Hashtable<>();
    }

    /** Instance unique non préinitialisée */
    private static HashUser INSTANCE = null;

    /** Point d'accès pour l'instance unique du singleton */
    public static HashUser getInstance(){
        if (INSTANCE == null){
            synchronized(HashUser.class){
                if (INSTANCE == null){
                    INSTANCE = new HashUser();
                }
            }
        }
        return INSTANCE;
    }

    public User searchHash(String email){
        if(!users.isEmpty() && this.users.get(email) != null) {
            return this.users.get(email);
        }else{
            try {
                User u = dao.search(email);
                if (u != null){
                    users.put(email, u);
                }
                return u;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
