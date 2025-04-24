package celestino.orders;

import celestino.Main;
import celestino.orders.jpanels.OrdersPanel;

public class OrdersMain {

    private final String column_names[] = {"ID","Date&Time","Customer","Contact","Address","Status","PaymentID","Price"};
    private final OrdersPanel orders_panel = new OrdersPanel(column_names, this);
    private final OrdersDB orders_db = new OrdersDB();


    public OrdersMain() {
        Main.add_card(orders_panel, "orders");
    }


    public void goto_orders() {

    }


    public void goto_order_create() {

    }


    public void goto_order_items(int order_id) {

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


    public void select_cell(int[] xy) {
        if (xy[0] == -1 || xy[1] == -1) return;

        Integer selected_id = Main.to_integer(orders_panel.get_value_at_xy(xy[0],0));

        int decision = Main.popup_option(
            "Selected Row ID: " + selected_id + "\n\n" + 
            column_names[xy[1]] + ":\n" + 
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
}
