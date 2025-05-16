package delarama;

import java.sql.*;

public class DBC {
    private final static String url = "jdbc:mysql://localhost:3306/db0321", 
    user = "root",
    pass = "";
    public static Connection getConnection() throws SQLException {
        
        return DriverManager.getConnection(url, user, pass);
    }
}