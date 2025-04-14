package celestino.inventory;

import celestino.Main;
import celestino.inventory.ui.InventoryPanel;

public class InventoryMain {

    boolean 
        searched = false,
        sorted = false;

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


    private void refresh_table() {
        inventory_card.update_table_pane(inventory_db.get_inventory_table());
        searched = false;
        sorted = false;
    }


    private void sort_table() {
        String order = null;
        switch (Main.popup_option("Sort into?",new String[]{"Lowest First","Highest First"})) {
            case 0: order = "ASC"; break;
            case 1: order = "DESC"; break;
            default: return;
        }
        inventory_card.update_table_pane(
            inventory_db.get_sorted_inventory_table(
                inventory_card.get_column_sort_index(), order
            )
        );
        sorted = true;
    }


    private void search_table() {
        String keyword = inventory_card.get_search_input();
        if (keyword == null || keyword.equals("")) return;
        inventory_card.update_table_pane(
            inventory_db.get_searched_inventory_table(keyword)
        );
        searched = false;
    }
}