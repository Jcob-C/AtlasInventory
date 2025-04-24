package celestino.inventory;

import celestino.Main;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            Connection conn = Main.db_connection();
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


    boolean edit(int id, int column, String new_value) {
        String edit_query = "UPDATE inventory SET " + column_names[column] + " = ? WHERE item_id = " + id + ";";
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


    boolean add_stock(int id, int new_stock) {
        String edit_query = "UPDATE inventory SET stock = stock + ? WHERE item_id = " + id + ";";
        try (
            Connection conn = Main.db_connection();
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
        return get_table("SELECT * FROM inventory");
    }


    ArrayList<ArrayList<String>> get_searched_sorted_table(String keyword,int column_index, String order) {
        return get_table(
            "SELECT * FROM inventory WHERE "
            +"CAST(item_id AS CHAR) LIKE '%" + keyword
            +"%' OR barcode LIKE '%" + keyword
            +"%' OR item_name LIKE '%" + keyword
            +"%' OR item_type LIKE '%" + keyword
            +"%' OR descr LIKE '%" + keyword
            +"%' OR location LIKE '%" + keyword
            +"%' OR CAST(price AS CHAR) LIKE '%" + keyword
            +"%' OR CAST(stock AS CHAR) LIKE '%" + keyword
            +"%' ORDER BY " + column_names[column_index] + " " + order
        );
    }

    
    private ArrayList<ArrayList<String>> get_table(String query) {
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