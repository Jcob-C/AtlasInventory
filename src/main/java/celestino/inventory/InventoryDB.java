package celestino.inventory;

import main.DB;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryDB {

    static boolean insert(String[] new_item) {
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
    
    
    static boolean delete(int id) {
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


    static boolean edit(int id, int column, String new_value) {
        String edit_query = "UPDATE inventory SET " + DB.db_inventory_columns[column] + " = ? WHERE item_id = " + id + ";";
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


    static boolean add_stock(int id, int new_stock) {
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
}