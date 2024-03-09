package org.example;
import java.sql.*;
public class dbConnection {
    final static private String URL = "jdbc:mysql://localhost:3306/dormitorydb";
    final static private String USER = "root";
    final static private String PASS = "411102";
    static private Connection conn;

    public static Connection getconn() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASS);
                return conn;
            }
            catch (SQLException e) {
                System.out.println("Error: database connect failed");
                e.printStackTrace();
                System.exit(1);
                return null;
            }
        }
        else
            return conn;
    }
    public static void closeConn(){
        try {
            if (conn != null)
                conn.close();
        }
        catch (SQLException e){
            System.out.println("Error: database connection close failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
