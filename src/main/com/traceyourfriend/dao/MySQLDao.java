package main.com.traceyourfriend.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class  MySQLDao {

    public static Connection conn;
    public static MySQLDao db;

    private MySQLDao() {
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
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
    public static MySQLDao getDbCon() {
        if ( db == null ) {
            db = new MySQLDao();
        }
        return db;
    }



}
