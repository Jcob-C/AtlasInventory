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
        return get_inventory_table("SELECT * FROM inventory");
    }
    

    public ArrayList<ArrayList<String>> get_sorted_inventory_table(int column_index, String order) {
        return get_inventory_table(
            "SELECT * FROM inventory ORDER BY " + inventory_column_names[column_index] + " " + order
        );
    }


    public ArrayList<ArrayList<String>> get_searched_inventory_table(String keyword) {
        return get_inventory_table(get_search_inventory_query(keyword));
    }


    public ArrayList<ArrayList<String>> get_searchedsorted_inventory_table
        (String keyword,int column_index, String order) 
    {
        return get_inventory_table(
            get_search_inventory_query(keyword)
            + " ORDER BY " + inventory_column_names[column_index] + " " + order
        );
    }
    

    private String get_search_inventory_query(String keyword) {
        return "SELECT * FROM inventory WHERE "
            +"CAST(item_id AS CHAR) LIKE '%" + keyword
            +"%' OR barcode LIKE '%" + keyword
            +"%' OR item_name LIKE '%" + keyword
            +"%' OR item_type LIKE '%" + keyword
            +"%' OR descr LIKE '%" + keyword
            +"%' OR location LIKE '%" + keyword
            +"%' OR CAST(stock AS CHAR) LIKE '%" + keyword
            +"%'"
        ;
    }

    
    private ArrayList<ArrayList<String>> get_inventory_table(String query) {
        ArrayList<ArrayList<String>> inventory_table = new ArrayList<>();
        try (
            Connection conn = Main.db_connection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
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