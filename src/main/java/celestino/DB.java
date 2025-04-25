package celestino;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {
    
    private static final String
        db_name = "db0321",
        db_password = "",
        db_user = "root",
        db_port = "3306",
        db_host = "localhost"
    ;
      
    
    public static Connection get_connection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_name,
            db_user,
            db_password
        );
    }


    public static ArrayList<ArrayList<String>> get_table(String query, int columns) {
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        try (
            Connection conn = get_connection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ) 
        {
            while (rs.next()) {
                ArrayList<String> new_row = new ArrayList<>();
                for (int i = 1; i <= columns; i++) {
                    new_row.add(rs.getString(i));
                }
                table.add(new_row);
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return table;
    }
}
