package celestino.orders;

import main.DB;
import main.Main;
import celestino.TableBrowserJPanel;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class OrdersMain {

    private static final String[]
        column_names = {"ID","Date&Time","Customer","Contact","Address","Status","PaymentID","Price"};
    private static final TableBrowserJPanel 
        orders_panel = new TableBrowserJPanel(
            column_names, 
            OrdersMain::selectOrder, 
            OrdersMain::gotoOrders, 
            OrdersMain::updateOrdersJTable,
            OrdersMain::refreshOrders
        ),
        order_item_select_panel = new TableBrowserJPanel(
            Main.inventory_columns,
            OrdersMain::addToOrder,
            OrdersMain::gotoOrderCreate,
            OrdersMain::updateItemSelectJTable,
            OrdersMain::refreshItemSelect
        );
    

    public static void createOrdersModule() {
        Main.addCard(OrderViewPage.createPanel(), "order view");
        Main.addCard(orders_panel, "orders");
        Main.addCard(OrderCreatePage.createPanel(), "order create");
        Main.addCard(order_item_select_panel, "order item select");

        orders_panel.setTitle("ORDERS");
        order_item_select_panel.setTitle("SELECT an ITEM to ADD on your ORDER");

        JButton order_create_button = new JButton(new ImageIcon("src/main/resources/add.png"));
        order_create_button.setBackground(Main.getMidColor());
        order_create_button.setBounds(29,116,40,40);
        order_create_button.addActionListener(e -> gotoOrderCreate());
        orders_panel.add(order_create_button);
        orders_panel.setComponentZOrder(order_create_button, 1);

        refreshOrders();
    }


    public static void gotoOrders() {
        refreshOrders();
        Main.changeCard("orders");
    }


    static void gotoOrderCreate() {
        Main.changeCard("order create");
    }


    static void gotoOrderItems(int order_id) {
        OrderViewPage.setTable(OrdersDB.getOrderItems(order_id));
        OrderViewPage.setOrderInfo(OrdersDB.getOrder(order_id));
        Main.changeCard("order view");
    }


    static void gotoOrderItemSelect() {
        refreshItemSelect();
        Main.changeCard("order item select");
    }


    static void changeOrderStatus(int order_id) {
        String order_statuses[] = {"Cancelled","Pending","Preparing","Shipping","Completed"};
        int order_status = Main.popupOption("Update order status to:", order_statuses);
        if (order_status != -1) {
            if (!checkStocks(order_id)) {
                if (!Main.popupConfirm("Insufficient Stock!\n\nContinue?")) {
                    return;
                } 
            }

            if (OrdersDB.edit(order_id, 5, order_statuses[order_status])) {
                updateOrdersJTable();
                Main.popupMessage("Order Status Updated");
            }
            else {
                Main.popupError("Order Status Update Failed");
            }

            switch(order_status) {
                case 0:
                    if (Main.popupConfirm("ADD order items to inventory STOCK?")) {
                        
                    }
                break;
                case 2:
                    if (Main.popupConfirm("SUBTRACT order items from inventory STOCK?")) {
                        
                    }
                break;
            }
        } 
    }


    static void updateOrdersJTable() {
        orders_panel.updateTable(
            OrdersDB.getSearchedSortedTable(
                orders_panel.getSearchInput(), 
                orders_panel.getSortColumnIndex(),
                orders_panel.getSortOrder()
            )
        );
    }


    static void updateItemSelectJTable() {
        order_item_select_panel.updateTable(
            DB.getSearchSortedInventoryTable(
                order_item_select_panel.getSearchInput(), 
                order_item_select_panel.getSortColumnIndex(),
                order_item_select_panel.getSortOrder()
            )
        );
    }


    static void refreshItemSelect() {
        order_item_select_panel.resetSortFilter();
        order_item_select_panel.updateTable(DB.getInventoryTable());
    }


    static void refreshOrders() {
        orders_panel.resetSortFilter();
        orders_panel.flipSortOrder();
        orders_panel.updateTable(OrdersDB.getTable());
    }


    static void selectOrder(int[] xy) {
        if (xy[0] == -1 || xy[1] == -1) return;

        Integer selected_id = Main.toInteger(orders_panel.getValueAt(xy[0],0));

        int decision = Main.popupOption(
            "Selected Order ID: " + selected_id + "\n\n" + 
            column_names[xy[1]] + ":\n" + 
            orders_panel.getValueAt(xy[0],xy[1]) + "\n\n", 
            new String[]{
                "Copy",
                "View Order",
                "Update Status"
            }
        );

        switch (decision) {
            case 0: Main.copyToClipboard(orders_panel.getValueAt(xy[0],xy[1])); break;
            case 1: gotoOrderItems(selected_id); break;
            case 2: changeOrderStatus(selected_id); break;
        }          
    }


    static boolean checkStocks(int order_id) {
        ArrayList<ArrayList<String>> order_items = OrdersDB.getOrderItems(order_id);
        for (ArrayList<String> x : order_items) {
            if (!DB.checkStock(x.get(0),x.get(4))) {
                return false;
            }
        }
        return true;
    }


    static void addToOrder(int[] xy) {
        if (xy[0] == -1 || xy[1] == -1) return;
        String
            item_id = order_item_select_panel.getValueAt(xy[0], 0),
            name = order_item_select_panel.getValueAt(xy[0], 2),
            type = order_item_select_panel.getValueAt(xy[0], 3),
            price = order_item_select_panel.getValueAt(xy[0], 6);
        OrderCreatePage.addItem(new String[]{item_id,name,type,price,"1"});
        gotoOrderCreate();
        if(mergeNewItem()) {
            Main.popupMessage("Added 1 to item's Quantity");
        }
        else {
            Main.popupMessage("New item Added");
        }
    }


    static boolean mergeNewItem() {
        int new_item_index = OrderCreatePage.getRowCount()-1;
        for(int i = new_item_index-1; i >= 0; i--) {
            if(OrderCreatePage.getValue(i, 0).equals(OrderCreatePage.getValue(new_item_index, 0))) {
                OrderCreatePage.setValue(i, 4, String.valueOf(
                        Main.toInteger(OrderCreatePage.getValue(i, 4))
                        +
                        Main.toInteger(OrderCreatePage.getValue(new_item_index, 4))
                    )
                );
                OrderCreatePage.removeItem(new_item_index);
                return true;
            }
        }
        return false;
    }


    static void selectOrderItem(int[] xy) {
        
    }


    static void submitOrder() {

    }


    static void clearOrderCreate() {
        OrderCreatePage.clearTable();
    }
}
