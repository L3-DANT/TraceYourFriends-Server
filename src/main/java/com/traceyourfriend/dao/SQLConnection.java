package com.traceyourfriend.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnection {

	private static final SQLConnection db = new SQLConnection();

	private Connection conn;


	private SQLConnection() {
		initConnection();
	}

	public static SQLConnection getSQLCon(){return db;}

	public Connection getDbCon() {
		return this.conn;
	}

	private void initConnection() {
		String dbHost = "localhost";
		String dbName = "traceyourfriends";
		String url= "jdbc:mysql://"+ dbHost + ":8889/"+ dbName;
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
