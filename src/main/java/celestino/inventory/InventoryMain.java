package celestino.inventory;

import celestino.Main;
import celestino.inventory.ui.InventoryPanel;

public class InventoryMain {

    public void call_action(String action) {
        switch (action) {
            case "refresh": refresh_table();
            break;
            case "search":
            break;
            case "sort":
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
    }
}