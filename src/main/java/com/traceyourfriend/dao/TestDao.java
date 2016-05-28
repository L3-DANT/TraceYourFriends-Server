package com.traceyourfriend.dao;

import com.traceyourfriend.beans.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.time.Clock;

/**
 * Created by pyrob on 28/05/2016.
 */
public class TestDao{

    public static void main(String[] args) throws SQLException {

        User u1 = new User();
        User u2 = new User();
        User u3 = new User();
        User u4 = new User();
        User u5 = new User();

        UsersDAO udao = new UsersDAO();

        u1= udao.search("Aniss");
        u2= udao.search("Alban");
        u3= udao.search("Leila");
        u4= udao.search("Romann");
        u5= udao.search("Kaci");

        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);
        System.out.println(u4);
        System.out.println(u5);

        System.out.println(udao.loadFriends(u1));




    }
}
