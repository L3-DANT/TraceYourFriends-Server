package com.traceyourfriend.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnection {

    public static final SQLConnection db = new SQLConnection();
	private Connection conn;

    public Connection getDbCon() {
        return conn;
    }

	private SQLConnection() {
		initConnection();
	}

	private void initConnection() {
		String dbHost = "localhost";
		String dbName = "TraceYourFriend-DB";
		String url= "jdbc:mysql://"+ dbHost + ":3306/"+ dbName;
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "root";
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url,userName,password);
		}
		catch (Exception sql) {
			sql.printStackTrace();
		}
	}


}
