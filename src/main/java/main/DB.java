package main;

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
        db_host = "localhost";
    public static final String[] db_inventory_columns = 
        {"item_id","barcode","item_name","item_type","descr","location","price","stock"}
    ;
      
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_name,
            db_user,
            db_password
        );
    }


    public static ArrayList<ArrayList<String>> getInventoryTable() {
        return DB.getTable("SELECT * FROM inventory", db_inventory_columns.length);
    }


    public static ArrayList<ArrayList<String>> getSearchSortedInventoryTable(String keyword,int column_index, String order) {
        return DB.getTable(
            "SELECT * FROM inventory WHERE "
            +"CAST(item_id AS CHAR) LIKE '%" + keyword
            +"%' OR barcode LIKE '%" + keyword
            +"%' OR item_name LIKE '%" + keyword
            +"%' OR item_type LIKE '%" + keyword
            +"%' OR descr LIKE '%" + keyword
            +"%' OR location LIKE '%" + keyword
            +"%' OR CAST(price AS CHAR) LIKE '%" + keyword
            +"%' OR CAST(stock AS CHAR) LIKE '%" + keyword
            +"%' ORDER BY " + db_inventory_columns[column_index] + " " + order,
            db_inventory_columns.length
        );
    }


    public static ArrayList<ArrayList<String>> getTable(String query, int columns) {
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        try (
            Connection conn = getConnection();
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


    public static boolean findBarcode(String barcode) {
        try (
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inventory WHERE barcode LIKE '%" + barcode + "%';");
            ) 
        {
            if (rs.next()) return true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }
}
