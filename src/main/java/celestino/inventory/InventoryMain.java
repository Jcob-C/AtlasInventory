package celestino.inventory;

import celestino.Main;
import celestino.inventory.ui.InventoryPanel;

public class InventoryMain {

    public void call_action(String action) {

    }


    public InventoryMain() {
        Main.add_card(inventory_card, "inventory");
    }


    private String inventory_table_cache[][] = null;
    private final InventoryPanel inventory_card = new InventoryPanel(this);
}