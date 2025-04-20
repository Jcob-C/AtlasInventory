package celestino.orders;

import celestino.Main;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersDB {

    private final String[] column_names = 
        {"item_id","barcode","item_name","item_type","descr","location","price","stock"}
    ;


    public boolean delete_row(String id) {
        String delete_stmt = 
        "DELETE FROM orders WHERE order_id = " + id + ";";
        try (
            Connection conn = Main.db_connection();
            Statement stmt = conn.createStatement()
            ) 
        {
            if (stmt.executeUpdate(delete_stmt) > 0) return true;
        }   
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean insert_row(String[] new_row) {
        String insert_stmt = 
        """
        INSERT INTO orders
        ( )
        VALUES 
        (?, ?, ?, ?, ?, ?, ?);
        """;
        try (
            Connection conn = Main.db_connection();
            PreparedStatement stmt = conn.prepareStatement(insert_stmt);
            ) 
        {
            for (int i = 0; i < new_row.length; i++) 
                stmt.setString(i + 1, new_row[i]);
            if (stmt.executeUpdate() > 0) return true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean insert_order_items() {
        return false;
    }

    
    public ArrayList<ArrayList<String>> get_orders_table() {
        return get_orders_table("SELECT * FROM orders ORDER BY order_id DESC");
    }


    public ArrayList<ArrayList<String>> get_searchedsorted_orders_table(String keyword, int column_index, String order) {
        return get_orders_table(
            get_searched_inventory_query(keyword)
            + " ORDER BY " + column_names[column_index] + " " + order
        );
    }


    public boolean edit_attribute(int id, int column, String new_value) {
        String edit_query = "UPDATE orders SET " + column_names[column] + " = ? WHERE order_id = " + id + ";";
        try (
            Connection conn = Main.db_connection();
            PreparedStatement stmt = conn.prepareStatement(edit_query) 
            ) 
        {
            stmt.setString(1, new_value);
            if (stmt.executeUpdate() > 0) return true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }
    

    private String get_searched_inventory_query(String keyword) {
        return "SELECT * FROM orders WHERE "
            +"CAST(order_id AS CHAR) LIKE '%" + keyword
            +"%' OR barcode LIKE '%" + keyword
            +"%' OR item_name LIKE '%" + keyword
            +"%' OR item_type LIKE '%" + keyword
            +"%' OR descr LIKE '%" + keyword
            +"%' OR location LIKE '%" + keyword
            +"%' OR price LIKE '%" + keyword
            +"%' OR CAST(total_price AS CHAR) LIKE '%" + keyword
            +"%'"
        ;
    }

    
    private ArrayList<ArrayList<String>> get_orders_table(String query) {
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        try (
            Connection conn = Main.db_connection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ) 
        {
            while (rs.next()) {
                ArrayList<String> new_row = new ArrayList<>();
                for (int i = 1; i <= column_names.length; i++) {
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