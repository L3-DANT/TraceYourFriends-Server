package main.com.traceyourfriend.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDao {

    private Connection connection;

    private String dbHost = null;
    private String dbUser = null;
    private String dbPwd = null;
    private String dbName = null;

    public MySQLDao(){
        this.dbHost = Conn.DB_HOST;
        this.dbUser = Conn.DB_USER;
        this.dbPwd = Conn.DB_PWD;
        this.dbName = Conn.DB_NAME;
    }

    public void setUp() throws Exception {
        String url = "jdbc:mysql://"+this.dbHost+":3306/"+dbName;
        Class.forName("org.mysql.Driver");
        connection = DriverManager.getConnection(url, this.dbUser, this.dbPwd);
    }

    public void after() throws SQLException {
        connection.close();
    }



}
