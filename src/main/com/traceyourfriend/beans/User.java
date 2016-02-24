package main.com.traceyourfriend.beans;


import main.com.traceyourfriend.dao.MySQLDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@Path("/users")
public class User implements Comparable<User> {

    MySQLDao mySQLDao = MySQLDao.getDbCon();

    private final String SQL_SELECT = "SELECT name FROM users";

    private long id;

    private String mail;

    private String password;

    public User(){}

    public User(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String message(){
        return "<p>Bonjour<p>";
    }

    @Path("/message2")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String message2(){
        return "<p>Bonsoir<p>";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return !(mail != null ? !mail.equals(user.mail) : user.mail != null);

    }

    @Override
    public int hashCode() {
        return mail != null ? mail.hashCode() : 0;
    }

    @Override
    public int compareTo(User o) {
        return mail.compareToIgnoreCase(o.getMail());
    }

    @Override
    public String toString() {
        return mail;
    }


    @Path("/database")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String nameOfUser() throws Exception {

        String str;
        String returnedString = "";
        try{
            PreparedStatement preparedStatement = mySQLDao.conn.prepareStatement(SQL_SELECT);


            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                str = resultSet.getString(1);
                returnedString = "<p>Database Status</p> " + "<p>Database name user  : " + str +"</p>";
            }
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnedString;
    }

}
