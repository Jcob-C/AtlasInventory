package delarama;

import java.sql.*;

public class DBC {
    public static Connection getConnection() throws SQLException {
        return celestino.DB.get_connection();
    }
}