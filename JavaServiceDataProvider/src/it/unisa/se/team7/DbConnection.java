package it.unisa.se.team7;

import java.sql.*;

public class DbConnection {

    private String url = "jdbc:postgresql://localhost/maintenance";
    private String user = "se_project";
    private String pwd = "gruppo7";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    /*
        Returns the connection to the database.
     */
    public Connection connect(){
        Connection con = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    /*
        Given a database connection, the method closes it.
     */
    public void closeConnection(Connection c){
        try {
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

