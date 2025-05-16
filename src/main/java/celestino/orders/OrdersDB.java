package celestino.orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import base.DB;

public class OrdersDB {
    
    private static final String column_names[] = {"order_id","order_datetime","customer_name","contact_no","address","order_status","payment_id","total_price","payment_method"};


    static ArrayList<ArrayList<String>> getTable() {
        return DB.getTable("SELECT * FROM orders ORDER BY order_id DESC", column_names.length);
    }


    static ArrayList<ArrayList<String>> getOrderItems(String order_id) {
        String query = 
        """
        SELECT 
            i.item_id,
            i.item_name,
            i.item_type,
            oi.price_each,
            oi.quantity
        FROM 
            order_items oi
        JOIN 
            inventory i ON oi.item_id = i.item_id
        WHERE 
            oi.order_id = 
        """ 
        + order_id + ';';
        return DB.getTable(query, 5);
    }


    static boolean edit(String id, int column, String new_value) {
        String edit_query = "UPDATE orders SET " + column_names[column] + " = ? WHERE order_id = " + id + ";";
        try (
            Connection conn = DB.getConnection();
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


    static int insertNewOrder(String[] order) {
        String insertSql = """
        INSERT INTO orders (customer_name, contact_no, address, payment_id, total_price, payment_method) 
        VALUES (?, ?, ?, ?, ?, ?);
        """;
        try (
            Connection conn = DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)
            ) {

            for (int i = 0; i < order.length; i++) {
                stmt.setString(i+1, order[i]);
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return -1;
    }


    static void insertOrderItem(String[] new_row) {
        String insertSql = """
        INSERT INTO order_items (order_id, item_id, quantity, price_each) 
        VALUES (?, ?, ?, ?);
        """;
        try (
            Connection conn = DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)
            ) {
                
            for (int i = 0; i < new_row.length; i++) {
                stmt.setString(i+1, new_row[i]);
            }
            stmt.executeUpdate();
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    static ArrayList<ArrayList<String>> getOrder(String order_id) {
        return DB.getTable(
            "SELECT * FROM orders WHERE "
            +"CAST(order_id AS CHAR) LIKE '%" + order_id + "%';",
            column_names.length
        );
    }   


    static ArrayList<ArrayList<String>> getSearchedSortedTable(String keyword,int column_index, String order) {
        return DB.getTable(
            "SELECT * FROM orders WHERE "
            +"CAST(order_id AS CHAR) LIKE '%" + keyword
            +"%' OR CAST(order_datetime AS CHAR) LIKE '%" + keyword
            +"%' OR customer_name LIKE '%" + keyword
            +"%' OR contact_no LIKE '%" + keyword
            +"%' OR address LIKE '%" + keyword
            +"%' OR order_status LIKE '%" + keyword
            +"%' OR payment_id LIKE '%" + keyword
            +"%' OR CAST(total_price AS CHAR) LIKE '%" + keyword
            +"%' ORDER BY " + column_names[column_index] + " " + order,
            column_names.length
        );
    }   
}
