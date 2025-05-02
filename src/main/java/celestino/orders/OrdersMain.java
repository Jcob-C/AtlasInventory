package celestino.orders;

import javax.swing.JButton;

import java.awt.Color;
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
            OrdersMain::goto_order_create,
            OrdersMain::update_item_select_jtable,
            OrdersMain::refresh_item_select
        )
    ;
    

    public static void create_orders_module() {
        Main.addCard(OrderViewPage.create_panel(), "order view");
        Main.addCard(orders_panel, "orders");
        Main.addCard(OrderCreatePage.create_panel(), "order create");
        Main.addCard(order_item_select_panel, "order item select");

        orders_panel.setTitle("ORDERS");
        order_item_select_panel.setTitle("Select an item");

        JButton order_create_button = new JButton("Order");
        order_create_button.setBackground(Main.getMidColor());
        order_create_button.setForeground(Color.WHITE);
        order_create_button.setFont(Main.getFont(16));
        order_create_button.setBounds(66,49,92,40);
        order_create_button.addActionListener(e -> goto_order_create());
        orders_panel.add(order_create_button);
        orders_panel.setComponentZOrder(order_create_button, 1);

        refresh_orders();
    }


    public static void goto_orders() {
        refresh_orders();
        Main.changeCard("orders");
    }


    static void goto_order_create() {
        Main.changeCard("order create");
    }


    static void goto_order_items(int order_id) {
        OrderViewPage.set_table(OrdersDB.get_order_items(order_id));
        Main.changeCard("order view");
    }


    static void goto_order_item_select() {
        refresh_item_select();
        Main.changeCard("order item select");
    }


    static void change_order_status(int order_id) {
        String order_statuses[] = {"Cancelled","Verifying","Preparing","On Delivery","Completed"};
        int order_status = Main.popupOption("Update order status to:", order_statuses);
        if (order_status != -1) {
            if (OrdersDB.edit(order_id, 5, order_statuses[order_status])) {
                update_orders_jtable();
                Main.popupMessage("Order Status Updated");
            }
            else {
                Main.popupError("Order Status Update Failed");
            }
        } 
    }


    static void edit_order_attribute(int order_id, int column) {
        
    }


    static void update_orders_jtable() {
        orders_panel.updateTable(
            OrdersDB.get_searchedsorted_table(
                orders_panel.getSearchInput(), 
                orders_panel.getSortColumnIndex(),
                orders_panel.getSortOrder()
            )
        );
    }


    static void update_item_select_jtable() {
        order_item_select_panel.updateTable(
            DB.getSearchSortedInventoryTable(
                order_item_select_panel.getSearchInput(), 
                order_item_select_panel.getSortColumnIndex(),
                order_item_select_panel.getSortOrder()
            )
        );
    }


    static void refresh_item_select() {
        order_item_select_panel.resetSortFilter();
        order_item_select_panel.updateTable(DB.getInventoryTable());
    }


    static void refresh_orders() {
        orders_panel.resetSortFilter();
        orders_panel.flipSortOrder();
        orders_panel.updateTable(OrdersDB.getTable());
    }


    static void select_order(int[] xy) {
        if (xy[0] == -1 || xy[1] == -1) return;

        Integer selected_id = Main.toInteger(orders_panel.getValueAt(xy[0],0));

        int decision = Main.popupOption(
            "Selected Order ID: " + selected_id + "\n\n" + 
            column_names[xy[1]] + ":\n" + 
            orders_panel.getValueAt(xy[0],xy[1]) + "\n\n", 
            new String[]{
                "View Order",
                "Edit Attribute",
                "Update Status"
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
            item_id = order_item_select_panel.getValueAt(xy[0], 0),
            name = order_item_select_panel.getValueAt(xy[0], 2),
            type = order_item_select_panel.getValueAt(xy[0], 3),
            price = order_item_select_panel.getValueAt(xy[0], 6);
        OrderCreatePage.add_item(new String[]{item_id,name,type,price,"1"});
        goto_order_create();
        if(merge_new_item()) {
            Main.popupMessage("Added 1 to item's Quantity");
        }
        else {
            Main.popupMessage("New item Added");
        }
    }


    static boolean merge_new_item() {
        int new_item_index = OrderCreatePage.get_row_count()-1;
        for(int i = new_item_index-1; i >= 0; i--) {
            if(OrderCreatePage.get_value(i, 0).equals(OrderCreatePage.get_value(new_item_index, 0))) {
                OrderCreatePage.set_value(i, 4, String.valueOf(
                        Main.toInteger(OrderCreatePage.get_value(i, 4))
                        +
                        Main.toInteger(OrderCreatePage.get_value(new_item_index, 4))
                    )
                );
                OrderCreatePage.remove_item(new_item_index);
                return true;
            }
        }
        return false;
    }


    static void select_order_item(int[] xy) {
        
    }


    static void submit_order() {

    }


    static void clear_order_create() {
        OrderCreatePage.clear_table();
    }
}
