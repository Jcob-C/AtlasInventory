package celestino.inventory;

import celestino.Main;
import celestino.inventory.ui.*;

public class InventoryMain {

    private final String column_names[] = {"ID","Barcode","Name","Type","Description","Location","Price","Stock"};
    private final ItemCreatePanel item_create_card = new ItemCreatePanel(this);
    private final InventoryPanel inventory_card = new InventoryPanel(column_names, this);
    private final InventoryDB inventory_db = new InventoryDB();
    
    
    public InventoryMain() {
        Main.add_card(inventory_card, "inventory");
        Main.add_card(item_create_card, "item create");
    }


    public void goto_inventory() {
        refresh_button();
        Main.change_card("inventory");
    }


    public void goto_item_create() {
        Main.change_card("item create");
    }


    public void refresh_button() {
        inventory_card.reset_sort_n_filter();
        inventory_card.update_table_pane(inventory_db.get_table());
    }


    public void update_jtable() {
        inventory_card.update_table_pane(
            inventory_db.get_searched_sorted_table(
                inventory_card.get_search_input(), 
                inventory_card.get_sort_column_index(), 
                inventory_card.get_sort_order()
            )
        );
    }
    
    
    public void add_stock(Integer item_id) {
        String new_stock = Main.popup_input("Enter the amount to add on stock:");
        if (new_stock == null) return;

        Integer int_new_stock = Main.to_integer(new_stock);
        if (int_new_stock == null) {
            Main.popup_error("Invalid Amount");
            return;
        }
        
        if (inventory_db.add_stock(item_id, int_new_stock)) {
            update_jtable();
            Main.popup_message("Add Stock Successful!");
            return;
        }
        else {
            Main.popup_error("Add Stock Failed");
        }
    }


    public void edit_attribute(Integer item_id, int column_index) {
        String new_value = Main.popup_input("Enter the new " + column_names[column_index] + ":");
        if (new_value == null ) return;

        if (column_index >= 6 && Main.to_double(new_value) == null) {
            Main.popup_error("Invalid for Column");
            return;
        }
        else if (column_index >= 6) {
            new_value = String.valueOf(Main.to_double(new_value));
        }

        if (inventory_db.edit(item_id,column_index,new_value)) {
            update_jtable();
            Main.popup_message("Edit Successful!");
        } 
        else {
            Main.popup_error("Edit Failed");
        }
    }


    public void delete(Integer item_id) {
        if (Main.popup_confirm("Delete item with ID:\n\n                     "+item_id+"\n\n")) {
            if (inventory_db.delete(item_id)) {
                update_jtable();
                Main.popup_message("Delete Successful");
            } else {
                Main.popup_error("Delete Failed");
            }
        }
    }
    
    
    public void create_button() {
        String new_item_inputs[] = item_create_card.get_new_item_inputs();
        if (Main.to_integer(new_item_inputs[6]) == null) {
            Main.popup_error("Stock input is not a valid number"); 
            return;
        }
        if (Main.to_double(new_item_inputs[5]) == null) {
            Main.popup_error("Price input is not a valid number"); 
            return;
        }
        if (inventory_db.insert(new_item_inputs)) {
            Main.popup_message("New item added");
            item_create_card.clear_new_item_fields();
            goto_inventory();
        }
    }
}