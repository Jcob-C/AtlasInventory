package celestino.orders;

import java.util.ArrayList;

import celestino.DB;

public class OrdersDB {
    
    private final String column_names[] = {"order_id","order_datetime","customer_name","contact_no","address","order_status","payment_id","total_price"};


    ArrayList<ArrayList<String>> get_table() {
        return DB.get_table("SELECT * FROM orders ORDER BY order_id DESC", column_names.length);
    }


    ArrayList<ArrayList<String>> get_searched_sorted_table(String keyword,int column_index, String order) {
        return DB.get_table(
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
