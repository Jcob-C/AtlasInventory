package celestino.inventory;

import celestino.DB;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryDB {

    private final String[] column_names = {"item_id","barcode","item_name","item_type","descr","location","price","stock"};


    boolean insert(String[] new_item) {
        String insert_stmt = """
        INSERT INTO inventory 
        (barcode, item_name, item_type, descr, location, price, stock)
        VALUES 
        (?, ?, ?, ?, ?, ?, ?);
        """;
        try (
            Connection conn = DB.get_connection();
            PreparedStatement stmt = conn.prepareStatement(insert_stmt);
            ) 
        {
            for (int i = 0; i < new_item.length; i++) {
                stmt.setString(i + 1, new_item[i]);
            }
            if (stmt.executeUpdate() > 0) return true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    
    boolean delete(int id) {
        String delete_stmt = "DELETE FROM inventory WHERE item_id = " + id + ";";
        try (
            Connection conn = DB.get_connection();
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


    boolean edit(int id, int column, String new_value) {
        String edit_query = "UPDATE inventory SET " + column_names[column] + " = ? WHERE item_id = " + id + ";";
        try (
            Connection conn = DB.get_connection();
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


    boolean add_stock(int id, int new_stock) {
        String edit_query = "UPDATE inventory SET stock = stock + ? WHERE item_id = " + id + ";";
        try (
            Connection conn = DB.get_connection();
            PreparedStatement stmt = conn.prepareStatement(edit_query) 
            ) 
        {
            stmt.setInt(1, new_stock);
            if (stmt.executeUpdate() > 0) return true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }


    ArrayList<ArrayList<String>> get_table() {
        return DB.get_table("SELECT * FROM inventory", column_names.length);
    }


    ArrayList<ArrayList<String>> get_searched_sorted_table(String keyword,int column_index, String order) {
        return DB.get_table(
            "SELECT * FROM inventory WHERE "
            +"CAST(item_id AS CHAR) LIKE '%" + keyword
            +"%' OR barcode LIKE '%" + keyword
            +"%' OR item_name LIKE '%" + keyword
            +"%' OR item_type LIKE '%" + keyword
            +"%' OR descr LIKE '%" + keyword
            +"%' OR location LIKE '%" + keyword
            +"%' OR CAST(price AS CHAR) LIKE '%" + keyword
            +"%' OR CAST(stock AS CHAR) LIKE '%" + keyword
            +"%' ORDER BY " + column_names[column_index] + " " + order,
            column_names.length
        );
    }
}