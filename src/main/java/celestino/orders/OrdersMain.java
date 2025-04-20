package celestino.orders;

import celestino.orders.ui.*;
import celestino.Main;

public class OrdersMain {

    private final String column_names[] = {"ID","Date & Time","Customer Name","Contact No","Address","Status","Payment ID","Total Price"};
    private final OrdersPanel orders_card = new OrdersPanel(column_names, this);
    private final OrdersDB orders_db = new OrdersDB();


    public OrdersMain() {
        Main.add_card(orders_card, "orders");
    }


    public void goto_orders() {
        refresh_button();
        Main.change_card("orders");
    }


    public void add_button() {

    }


    public void update_table() {

    }


    public void refresh_button() {
        orders_card.reset_search_field();
        orders_card.reset_sort_order();
        orders_card.reset_sort_column();
        orders_card.update_table_pane(orders_db.get_orders_table());
    }

    
    public void view_selected() {

    }


    public void edit_selected() {

    }
    

    public void cancel_selected() {

    }


    public void change_status_selected() {

    }
}
