package celestino.inventory;

import javax.swing.JButton;

import celestino.Main;
import celestino.TableBrowserJPanel;

public class InventoryMain {

    private final String column_names[] = {"ID","Barcode","Name","Type","Description","Location","Price","Stock"};
    private final ItemCreateJPanel item_create_panel = new ItemCreateJPanel(this);
    private final TableBrowserJPanel inventory_panel = new TableBrowserJPanel(column_names, this::select_cell, this::goto_inventory, this::update_jtable);
    private final InventoryDB inventory_db = new InventoryDB();
    
    
    public InventoryMain() {
        JButton item_create_button = new JButton("Add");
        item_create_button.setBackground(Main.get_dark_color());
        item_create_button.setForeground(Main.get_light_color());
        item_create_button.setFont(Main.get_font(16));
        item_create_button.setBounds(66,49,92,40);
        item_create_button.addActionListener(e -> goto_item_create());
        inventory_panel.add(item_create_button);
        inventory_panel.setComponentZOrder(item_create_button, 1);

        Main.add_card(inventory_panel, "inventory");
        Main.add_card(item_create_panel, "item create");
        refresh();
    }


    public void goto_inventory() {
        refresh();
        Main.change_card("inventory");
    }


    public void goto_item_create() {
        Main.change_card("item create");
    }


    public void refresh() {
        inventory_panel.reset_sort_n_filter();
        inventory_panel.update_table_pane(inventory_db.get_table());
    }


    public void update_jtable() {
        inventory_panel.update_table_pane(
            inventory_db.get_searched_sorted_table(
                inventory_panel.get_search_input(), 
                inventory_panel.get_sort_column_index(), 
                inventory_panel.get_sort_order()
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
        String new_item_inputs[] = item_create_panel.get_new_item_inputs();
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
            item_create_panel.clear_new_item_fields();
            goto_inventory();
        }
    }


    public void select_cell(int[] xy) {
        if (xy[0] == -1 || xy[1] == -1) return;

        Integer selected_id = Main.to_integer(inventory_panel.get_value_at_xy(xy[0],0));

        int decision = Main.popup_option(
            "Selected Row ID: " + selected_id + "\n\n" + 
            column_names[xy[1]] + ":\n" + 
            inventory_panel.get_value_at_xy(xy[0],xy[1]) + "\n\n", 
            new String[]{
                "Add Stock",
                "Edit Attribute",
                "Delete Row"
            }
        );

        switch (decision) {
            case 0: add_stock(selected_id); break;
            case 1: edit_attribute(selected_id, xy[1]); break;
            case 2: delete(selected_id); break;
        }
    }
}