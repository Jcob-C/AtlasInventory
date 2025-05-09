package celestino.orders;

import main.DB;
import main.Main;
import celestino.TableBrowserJPanel;
import celestino.inventory.InventoryMain;

import java.util.ArrayList;
import javax.swing.JButton;

public class OrdersMain {

    private static final String[]
        column_names = {"ID","Date&Time","Customer","Contact","Address","Status","PaymentID","Price"};
    private static final TableBrowserJPanel 
        orders_panel = new TableBrowserJPanel(
            column_names, 
            OrdersMain::selectOrder, 
            InventoryMain::gotoInventory, 
            OrdersMain::updateOrdersJTable,
            OrdersMain::refreshOrders
        ),
        order_item_select_panel = new TableBrowserJPanel(
            Main.inventory_columns,
            OrdersMain::addToOrder,
            OrdersMain::gotoOrderCreate,
            OrdersMain::updateItemSelectJTable,
            OrdersMain::refreshItemSelect
        )
    ;
    

    public static void createOrdersModule() {
        Main.addCard(OrderViewPage.createPanel(), "order view");
        Main.addCard(orders_panel, "orders");
        Main.addCard(OrderCreatePage.createPanel(), "order create");
        Main.addCard(order_item_select_panel, "order item select");

        orders_panel.setTitle("ORDERS");
        order_item_select_panel.setTitle("SELECT an ITEM to ADD on your ORDER");

        JButton order_create_button = new JButton(Main.addIcon);
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


    static void gotoOrderItems(String order_id) {
        OrderViewPage.setTable(OrdersDB.getOrderItems(order_id));
        OrderViewPage.setOrderInfo(OrdersDB.getOrder(order_id));
        Main.changeCard("order view");
    }


    static void gotoOrderItemSelect() {
        refreshItemSelect();
        Main.changeCard("order item select");
    }


    static void changeOrderStatus(String order_id) {
        String order_statuses[] = {"Cancelled","Pending","Preparing","Shipping","Completed"};
        int order_status = Main.popupOption("Update order status to:", order_statuses);
        if (order_status != -1) {
            if (checkStocks(order_id)) {
                return;
            }

            if (OrdersDB.edit(order_id, 5, order_statuses[order_status])) {
                updateOrdersJTable();
                Main.popupMessage("Order Status Updated");
            }
            else {
                Main.popupError("Order Status Update Failed");
            }

            ArrayList<ArrayList<String>> order_items = OrdersDB.getOrderItems(order_id);
        
            switch(order_status) {
                case 0: case 1:
                    if (Main.popupConfirm("ADD order QUANTITY to inventory STOCK?")) {
                        for (ArrayList<String> x : order_items) {
                            DB.addStock(x.get(0), x.get(4));
                        }
                    }
                break;
                case 2:
                    if (Main.popupConfirm("SUBTRACT order QUANTITY from inventory STOCK?")) {
                        for (ArrayList<String> x : order_items) {
                            DB.addStock(x.get(0), "-"+x.get(4));
                        }
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


    static void updateViewedOrder() {
        if (Main.popupConfirm("Save Changes?")) {
            String data[] = OrderViewPage.getCustomerInfoFields(),
            id = OrderViewPage.getViewedID().substring(10);
            OrdersDB.edit(id, 2, data[0]);
            OrdersDB.edit(id, 3, data[1]);
            OrdersDB.edit(id, 4, data[2]);
            OrdersDB.edit(id, 6, data[3]);
        }
        gotoOrders();
    }


    static void selectOrder(int[] xy) {
        if (xy[0] == -1 || xy[1] == -1) return;

        String selected_id = orders_panel.getValueAt(xy[0],0);

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


    static boolean checkStocks(String order_id) {
        ArrayList<ArrayList<String>> order_items = OrdersDB.getOrderItems(order_id);
        for (ArrayList<String> x : order_items) {
            if (!DB.checkStock(x.get(0),x.get(4))) {
                if (!Main.popupConfirm("Insufficient stock for " + x.get(1) + "\n\n Continue?")) {
                    return true;
                }
            }
        }
        return false;
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
        mergeNewItem();
    }

    
    static void updateTotal() {
        Double total = 0d;
        ArrayList<ArrayList<String>> x = OrderCreatePage.getTable();
        if (x == null) return;
        for(ArrayList<String> y : x) {
            total += Double.parseDouble(y.get(3))* Double.parseDouble(y.get(4));
        }
        OrderCreatePage.setTotalLabel("Total: "+total);
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
        if (xy[0] == -1 || xy[1] == -1) return;
        int option = Main.popupOption("Selected Item: " + OrderCreatePage.getValue(xy[0], 1), new String[]{"Remove Item","Edit Quantity"});

        switch (option) {
            case 0: OrderCreatePage.removeItem(xy[0]); break;
            case 1: 
                String quantity = Main.popupInput("Enter quantity:");
                if (Main.toInteger(quantity) != null) {
                    OrderCreatePage.setValue(xy[0], 4, quantity);
                }
                else {
                    Main.popupMessage("Invalid Quantity");
                }
            break;
        }
    }


    static void submitOrder() {
        if (OrderCreatePage.getRowCount() == 0) return;
        String[] order = OrderCreatePage.getOrderInfo();
        ArrayList<ArrayList<String>> items = OrderCreatePage.getTable();
        int order_id = OrdersDB.insertNewOrder(order);
        for (ArrayList<String> x : items) {
            OrdersDB.insertOrderItem(new String[]{
                String.valueOf(order_id),
                x.get(0),
                x.get(4),
                x.get(3)
            });
        }
        gotoOrders();
        OrderCreatePage.clearInputs();
        OrderCreatePage.clearTable();
        Main.popupMessage("Order Submitted");
    }


    static void clearOrderCreate() {
        OrderCreatePage.clearTable();
    }
}
