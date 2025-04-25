package celestino.orders;

import celestino.Main;
import celestino.TableBrowserJPanel;

public class OrdersMain {

    private final String[]
        orders_column_names = {"ID","Date&Time","Customer","Contact","Address","Status","PaymentID","Price"},
        order_create_column_names = {"ID","Name","Type","Price","Quantity"};
    private final TableBrowserJPanel orders_panel = new TableBrowserJPanel(orders_column_names, this::select_order, this::goto_orders, this::update_jtable);
    private final OrderCreateJPanel order_create_panel = new OrderCreateJPanel(order_create_column_names, this);
    private final OrdersDB orders_db = new OrdersDB();


    public OrdersMain() {
        Main.add_card(orders_panel, "orders");
        Main.add_card(order_create_panel, "order create");
        refresh();
    }


    public void goto_orders() {
        refresh();
        Main.change_card("orders");
    }


    public void goto_order_create() {
        Main.change_card("order create");
    }


    public void goto_order_items(int order_id) {

    }


    public void change_order_status(int order_id) {

    }


    public void edit_order_attribute(int order_id, int column) {
        
    }


    public void update_jtable() {
        orders_panel.update_table_pane(
            orders_db.get_searched_sorted_table(
                orders_panel.get_search_input(), 
                orders_panel.get_sort_column_index(),
                orders_panel.get_sort_order()
                )
        );
    }


    public void refresh() {
        orders_panel.reset_sort_n_filter();
        orders_panel.update_table_pane(orders_db.get_table());
    }


    public void select_order(int[] xy) {
        if (xy[0] == -1 || xy[1] == -1) return;

        Integer selected_id = Main.to_integer(orders_panel.get_value_at_xy(xy[0],0));

        int decision = Main.popup_option(
            "Selected Row ID: " + selected_id + "\n\n" + 
            orders_column_names[xy[1]] + ":\n" + 
            orders_panel.get_value_at_xy(xy[0],xy[1]) + "\n\n", 
            new String[]{
                "View Content",
                "Edit Attribute",
                "Change Status"
            }
        );

        switch (decision) {
            case 0: goto_order_items(selected_id); break;
            case 1: break;
            case 2:  break;
        }          
    }


    public void select_new_order_item(int[] xy) {

    }


    public void add_new_order_item(int item_id, String name, String type) {

    }
}
