package celestino.inventory;

import celestino.Main;
import celestino.inventory.ui.InventoryPanel;

public class InventoryMain {

    public void call_action(String action) {
        switch (action) {
            case "refresh": refresh_table();
            break;
            case "search": search_table();
            break;
            case "sort": sort_table();
            break;
            default: System.out.println("Unmapped Action : " + action);
        }
    }


    public InventoryMain() {
        Main.add_card(inventory_card, "inventory");
    }
    

    private final InventoryPanel inventory_card = new InventoryPanel(this);
    private final InventoryDB inventory_db = new InventoryDB();
    private Integer sorted = null;
    private String
        searched = null,
        order = null
    ;
    

    private void refresh_table() {
        inventory_card.update_table_pane(inventory_db.get_inventory_table());
        searched = null;
        sorted = null;
        order = null;
    }


    private void sort_table() {
        sorted = inventory_card.get_column_sort_index();

        switch (Main.popup_option("Sort into?",new String[]{"Lowest First","Highest First"})) {
            case 0: order = "ASC"; break;
            case 1: order = "DESC"; break;
            default: order = null; return;
        }

        if (searched != null && !searched.equals("")) {
            inventory_card.update_table_pane(
                inventory_db.get_searchedsorted_inventory_table(
                    searched, sorted, order
                )
            );
        }
        else {
            inventory_card.update_table_pane(
                inventory_db.get_sorted_inventory_table(
                    sorted, order
                )
            );
        }
    }


    private void search_table() {
        searched = inventory_card.get_search_input();

        if (searched == null || searched.equals("")) return;

        if (sorted != null && order != null) {
            inventory_card.update_table_pane(
                inventory_db.get_searchedsorted_inventory_table(
                    searched, sorted, order
                )
            );
        }
        else {
            inventory_card.update_table_pane(
                inventory_db.get_searched_inventory_table(
                    searched
                )
            );
        }
    }
}