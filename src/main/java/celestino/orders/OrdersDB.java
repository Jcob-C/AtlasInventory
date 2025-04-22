package celestino.orders;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import celestino.Main;

public class OrdersDB {
    
    private final String column_names[] = {"order_id","order_datetime","customer_name","contact_no","address","order_status","payment_id","total_price"};


    ArrayList<ArrayList<String>> get_table() {
        return get_table("SELECT * FROM orders ORDER BY order_id DESC");
    }


    ArrayList<ArrayList<String>> get_searched_sorted_table(String keyword,int column_index, String order) {
        return get_table(
            "SELECT * FROM orders WHERE "
            +"CAST(order_id AS CHAR) LIKE '%" + keyword
            +"%' OR CAST(order_datetime AS CHAR) LIKE '%" + keyword
            +"%' OR customer_name LIKE '%" + keyword
            +"%' OR contact_no LIKE '%" + keyword
            +"%' OR address LIKE '%" + keyword
            +"%' OR order_status LIKE '%" + keyword
            +"%' OR payment_id LIKE '%" + keyword
            +"%' OR CAST(total_price AS CHAR) LIKE '%" + keyword
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
