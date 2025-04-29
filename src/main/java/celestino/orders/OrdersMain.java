package celestino.orders;

import javax.swing.JButton;

import celestino.TableBrowserJPanel;
import main.DB;
import main.Main;

public class OrdersMain {

    private static final String[]
        column_names = {"ID","Date&Time","Customer","Contact","Address","Status","PaymentID","Price"};
    private static final TableBrowserJPanel 
        orders_panel = new TableBrowserJPanel(
            column_names, 
            OrdersMain::select_order, 
            OrdersMain::goto_orders, 
            OrdersMain::update_orders_jtable,
            OrdersMain::refresh_orders
        ),
        order_item_select_panel = new TableBrowserJPanel(
            Main.inventory_columns,
            OrdersMain::add_to_order,
            OrdersMain::goto_orders,
            OrdersMain::update_item_select_jtable,
            OrdersMain::refresh_item_select
        );

    

    public static void create_orders_module() {
        Main.add_card(OrderViewPage.create_panel(), "order view");
        Main.add_card(orders_panel, "orders");
        Main.add_card(OrderCreatePage.create_panel(), "order create");
        Main.add_card(order_item_select_panel, "order item select");

        JButton order_create_button = new JButton("Order");
        order_create_button.setBackground(Main.get_dark_color());
        order_create_button.setForeground(Main.get_light_color());
        order_create_button.setFont(Main.get_font(16));
        order_create_button.setBounds(66,49,92,40);
        order_create_button.addActionListener(e -> goto_order_create());
        orders_panel.add(order_create_button);
        orders_panel.setComponentZOrder(order_create_button, 1);
        
        refresh_orders();
    }


    public static void goto_orders() {
        refresh_orders();
        Main.change_card("orders");
    }


    static void goto_order_create() {
        Main.change_card("order create");
    }


    static void goto_order_items(int order_id) {
        
        Main.change_card("order view");
    }


    static void goto_order_item_select() {
        refresh_item_select();
        Main.change_card("order item select");
    }


    static void change_order_status(int order_id) {
        String order_statuses[] = {"Cancelled","Verifying","Preparing","On Delivery","Completed"};
        int order_status = Main.popup_option("Update order status to:", order_statuses);
        if (order_status != -1) {
            if (OrdersDB.edit(order_id, 5, order_statuses[order_status])) {
                update_orders_jtable();
                Main.popup_message("Order Status Updated");
            }
            else {
                Main.popup_error("Order Status Update Failed");
            }
        } 
    }


    static void edit_order_attribute(int order_id, int column) {
        
    }


    static void update_orders_jtable() {
        orders_panel.update_table_pane(
            OrdersDB.get_searchedsorted_table(
                orders_panel.get_search_input(), 
                orders_panel.get_sort_column_index(),
                orders_panel.get_sort_order()
            )
        );
    }


    static void update_item_select_jtable() {
        order_item_select_panel.update_table_pane(
            DB.get_searchedsorted_inventory_table(
                order_item_select_panel.get_search_input(), 
                order_item_select_panel.get_sort_column_index(),
                order_item_select_panel.get_sort_order()
            )
        );
    }


    static void refresh_item_select() {
        order_item_select_panel.reset_sort_n_filter();
        order_item_select_panel.update_table_pane(DB.get_inventory_table());
    }


    static void refresh_orders() {
        orders_panel.reset_sort_n_filter();
        orders_panel.update_table_pane(OrdersDB.get_table());
    }


    static void select_order(int[] xy) {
        if (xy[0] == -1 || xy[1] == -1) return;

        Integer selected_id = Main.to_integer(orders_panel.get_value_at_xy(xy[0],0));

        int decision = Main.popup_option(
            "Selected Row ID: " + selected_id + "\n\n" + 
            column_names[xy[1]] + ":\n" + 
            orders_panel.get_value_at_xy(xy[0],xy[1]) + "\n\n", 
            new String[]{
                "View Order Items",
                "Edit Selected Attribute",
                "Update Order Status"
            }
        );

        switch (decision) {
            case 0: goto_order_items(selected_id); break;
            case 1: edit_order_attribute(selected_id, xy[1]); break;
            case 2: change_order_status(selected_id); break;
        }          
    }


    static void add_to_order(int[] xy) {
        if (xy[0] == -1 || xy[1] == -1) return;
        String
            item_id = order_item_select_panel.get_value_at_xy(xy[0], 0),
            name = order_item_select_panel.get_value_at_xy(xy[0], 2),
            type = order_item_select_panel.get_value_at_xy(xy[0], 3),
            price = order_item_select_panel.get_value_at_xy(xy[0], 6);
        OrderCreatePage.add_item(new String[]{item_id,name,type,price,"1"});
        goto_order_create();
        merge_item_repeats();
    }


    static void merge_item_repeats() {
        int row_count = OrderCreatePage.get_row_count();
        for (int i = 0; i < row_count; i++) {
            for (int o = 0; o < row_count; o++) {
                if (i != o && OrderCreatePage.get_value(i, 0).equals(OrderCreatePage.get_value(o, 0))) {
                    OrderCreatePage.set_value(
                        i, 
                        4, 
                        String.valueOf(
                            Main.to_integer(OrderCreatePage.get_value(i,4)) 
                            +
                            Main.to_integer(OrderCreatePage.get_value(o,4))
                        )
                    );
                    OrderCreatePage.remove_item(o);
                }
            }
        }
    }


    static void select_order_item(int[] xy) {
        
    }


    static void submit_order() {

    }


    static void clear_order_create() {
        
    }
}
