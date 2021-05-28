package sample;

import org.sqlite.SQLiteConnection;

import java.sql.*;

public class DatabaseHelper {
    public static Connection connect(){
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:users.db");
           // System.out.println("Connected!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(e+"");
        }
        return con;
    }
}

