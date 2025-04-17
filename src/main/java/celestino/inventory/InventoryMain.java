package celestino.inventory;

import celestino.Main;
import celestino.inventory.ui.AddItemPanel;
import celestino.inventory.ui.InventoryPanel;

public class InventoryMain {

    public void call_action(String action) {
        switch (action) {
            case "inventory": refresh_table(); Main.change_card("inventory");
            break;
            case "refresh": refresh_table();
            break;
            case "order sort": inventory_card.flip_sort_order();
            case "search": 
            case "sort": search_sort_table();
            break;
            case "new item": Main.change_card("add item");
            break;
            case "create item": insert_new_item(add_item_card.get_new_item_inputs());
            break;
            case "select cell": select_cell();
            break;
            default: System.out.println("Unmapped Action : " + action);
        }
    }


    public InventoryMain() {
        Main.add_card(inventory_card, "inventory");
        Main.add_card(add_item_card, "add item");
    }
    

    private final AddItemPanel add_item_card = new AddItemPanel(this);
    private final InventoryPanel inventory_card = new InventoryPanel(this);
    private final InventoryDB inventory_db = new InventoryDB();
    private Integer sorted = null;
    private String
        searched = null,
        order = null
    ;


    private void select_cell() {
        Integer cell[] = inventory_card.get_selected_xy();
        if (cell[0] == -1 || cell[1] == -1) return;
        int 
            selected_id = Main.to_integer(inventory_card.get_id_at_row(cell[0])),
            decision = Main.popup_option(
                "Item ID: " + selected_id +
                "\n\nSelected Value:\n" + 
                inventory_card.get_value_at_xy(cell[0],cell[1]) + 
                "\n\n", 
                new String[]{"","","Delete"}
            );
        switch (decision) {
            case 0: restock_item(selected_id);
            break;
            case 1: edit_item_attribute(selected_id, cell[1]);
            break;
            case 2: delete_item(selected_id);
            break;
        }
    }


    private void restock_item(int item_id) {

    }


    private void edit_item_attribute(int item_id, int column_index) {
        //String new_value = Main.popup_input("Enter the new value:");
    }


    private void delete_item(int item_id) {
        if (Main.popup_confirm
            ("Delete item with ID:\n\n                     "+item_id+"\n\n")) 
        {
            if (inventory_db.delete_item(item_id)) {
                Main.popup_message("Delete Successful");
                refresh_table();
            }
            else {
                Main.popup_error("Delete Failed");
            }
        }
    }


    private void insert_new_item(String[] new_item_inputs) {
        if (Main.to_integer(new_item_inputs[6]) == null) {
            Main.popup_error("Stock input is not a valid number"); 
            return;
        }
        if (Main.to_double(new_item_inputs[5]) == null) {
            Main.popup_error("Price input is not a valid number"); 
            return;
        }
        if (inventory_db.insert_new_item(new_item_inputs)) {
            Main.popup_message("New item added");
            add_item_card.clear_new_item_inputs();
            call_action("inventory");
        }
    }
    

    private void refresh_table() {
        inventory_card.reset_search_field();
        inventory_card.reset_sort_order();
        inventory_card.reset_sort_column();
        searched = null;
        order = null;
        sorted = null;
        inventory_card.update_table_pane(inventory_db.get_inventory_table());
    }


    private void search_sort_table() {
        searched = inventory_card.get_search_input();
        sorted = inventory_card.get_sort_column_index();
        order = "ASC";
        switch (inventory_card.get_sort_order()) {
            case ">": order = "ASC"; break;
            case "<": order = "DESC"; break;
            default: return;
        }
        inventory_card.update_table_pane(
            inventory_db.get_searched_sorted_inventory_table(
                searched, sorted, order
            )
        );
    }
}