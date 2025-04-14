package celestino.inventory;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import celestino.Main;

public class InventoryDB {

    public final String[] inventory_column_names = 
        {"item_id","barcode","item_name","item_type","descr","location","stock"}
    ;


    public ArrayList<ArrayList<String>> get_inventory_table() {
        ArrayList<ArrayList<String>> inventory_table = new ArrayList<>();
        try (
            Connection conn = Main.db_connection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inventory");
            ) 
        {
            while (rs.next()) {
                ArrayList<String> new_row = new ArrayList<>();
                new_row.add(rs.getString(1));
                new_row.add(rs.getString(2));
                new_row.add(rs.getString(3));
                new_row.add(rs.getString(4));
                new_row.add(rs.getString(5));
                new_row.add(rs.getString(6));
                new_row.add(rs.getString(7));
                inventory_table.add(new_row);
            }
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return inventory_table;
    }


    public ArrayList<ArrayList<String>> get_sorted_inventory_table(int column_index, String order) {
        ArrayList<ArrayList<String>> inventory_table = new ArrayList<>();
        try (
            Connection conn = Main.db_connection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM inventory ORDER BY " + inventory_column_names[column_index] + " " + order);
            ) 
        {
            while (rs.next()) {
                ArrayList<String> new_row = new ArrayList<>();
                new_row.add(rs.getString(1));
                new_row.add(rs.getString(2));
                new_row.add(rs.getString(3));
                new_row.add(rs.getString(4));
                new_row.add(rs.getString(5));
                new_row.add(rs.getString(6));
                new_row.add(rs.getString(7));
                inventory_table.add(new_row);
            }
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return inventory_table;
    }
}