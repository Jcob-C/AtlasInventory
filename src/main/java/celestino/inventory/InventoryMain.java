package celestino.inventory;

import celestino.ScannerJPanel;
import celestino.TableBrowserJPanel;
import celestino.transact.TransactMain;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import base.DB;
import base.Main;

public class InventoryMain {

    private static final TableBrowserJPanel inventory_panel = new TableBrowserJPanel(
        Main.inventory_columns, 
        InventoryMain::selectCell,
        Main::gotoDashboard,
        InventoryMain::updateTable,
        InventoryMain::refresh
    );
    private static final ScannerJPanel scanner_panel = new ScannerJPanel(InventoryMain::scanned,InventoryMain::gotoInventory);

    
    public static void createModule() {
        Main.addCard(inventory_panel, "inventory");
        Main.addCard(ItemCreatePage.createPanel(), "item create");
        Main.addCard(scanner_panel, "restock scan");
        
        JButton item_create_button = new JButton(Main.add_icon);
        item_create_button.setBackground(Main.getMidColor());
        item_create_button.setBounds(29,116,40,40);
        item_create_button.addActionListener(e -> gotoItemCreate());
        inventory_panel.add(item_create_button);
        inventory_panel.setComponentZOrder(item_create_button, 1);

        JButton scan_button = new JButton(new ImageIcon("src/main/resources/scanner.png"));
        scan_button.setBackground(Main.getMidColor());
        scan_button.setBounds(80,116,40,40);
        scan_button.addActionListener(e -> gotoScanner());
        inventory_panel.add(scan_button);
        inventory_panel.setComponentZOrder(scan_button, 1);

        inventory_panel.setTitle("INVENTORY");
       
        refresh();
    }


    public static void gotoInventory() {
        refresh();
        Main.changeCard("inventory");
    }


    static void gotoItemCreate() {
        Main.changeCard("item create");
    }


    static void gotoScanner() {
        scanner_panel.clearBuffer();
        Main.changeCard("restock scan");
        scanner_panel.requestFocusInWindow();
    }


    static void scanned(String input) {
        if (DB.findBarcode(input)) {
            gotoInventory();
            inventory_panel.setSearchInput(input);
            updateTable();
            addStock(inventory_panel.getValueAt(0, 0));
            gotoScanner();
        }
        else {
            int x = Main.popupOption("Barcode not found", new String[]{"Create Item","Retry Scan"});
            if (x == 0) {
                gotoItemCreate();
                ItemCreatePage.setBarcodeInput(input);
            }
        }
    }


    static void refresh() {
        inventory_panel.resetSortFilter();
        inventory_panel.updateTable(DB.getInventoryTable());
    }


    static void updateTable() {
        inventory_panel.updateTable(
            DB.getSearchSortedInventoryTable(
                inventory_panel.getSearchInput(), 
                inventory_panel.getSortColumnIndex(), 
                inventory_panel.getSortOrder()
            )
        );
    }
    
    
    static void addStock(String item_id) {
        String new_stock = Main.popupInput("Enter the amount to add on stock:");
        if (new_stock == null) return;

        Integer int_new_stock = Main.toInteger(new_stock);
        if (int_new_stock == null) {
            Main.popupError("Invalid Amount");
            return;
        }
        
        if (DB.addStock(item_id, new_stock)) {
            updateTable();
            Main.popupMessage("Add Stock Successful!");
            return;
        }
        else {
            Main.popupError("Add Stock Failed");
        }
    }


    static void editAttribute(String item_id, int column_index) {
        String new_value = Main.popupInput("Enter the new " + Main.inventory_columns[column_index] + ":");
        if (new_value == null ) return;

        if (column_index >= 6 && Main.toDouble(new_value) == null) {
            Main.popupError("Invalid for Column");
            return;
        }
        else if (column_index >= 6) {
            new_value = String.valueOf(Main.toDouble(new_value));
        }

        if (InventoryDB.edit(item_id,column_index,new_value)) {
            updateTable();
            Main.popupMessage("Edit Successful!");
        } 
        else {
            Main.popupError("Edit Failed");
        }
    }


    static void delete(String item_id) {
        if (Main.popupConfirm("Delete item with ID:\n\n                     "+item_id+"\n\n")) {
            if (InventoryDB.delete(item_id)) {
                updateTable();
                Main.popupMessage("Delete Successful");
            } else {
                Main.popupError("Delete Failed");
            }
        }
    }
    
    
    static void createItem() {
        String new_item_inputs[] = ItemCreatePage.getNewItemInput();
        if (Main.toInteger(new_item_inputs[6]) == null) {
            Main.popupError("Stock input is not a valid number"); 
            return;
        }
        if (Main.toDouble(new_item_inputs[5]) == null) {
            Main.popupError("Price input is not a valid number"); 
            return;
        }
        if (InventoryDB.insert(new_item_inputs)) {
            Main.popupMessage("New item added");
            ItemCreatePage.clearNewItemFields();
            gotoInventory();
        }
    }


    static void selectCell(int[] xy) {
        if (xy[0] == -1 || xy[1] == -1) return;

        String selected_id = inventory_panel.getValueAt(xy[0],0);

        int decision = Main.popupOption(
            "Selected Item: " + inventory_panel.getValueAt(xy[0],2) + "\n\n" + 
            Main.inventory_columns[xy[1]] + ":\n" + 
            inventory_panel.getValueAt(xy[0],xy[1]) + "\n\n", 
            new String[]{
                "Copy",
                "Restock Item",
                "Edit Attribute",
                "Delete Item"
            }
        );

        switch (decision) {
            case 0: Main.copyToClipboard(inventory_panel.getValueAt(xy[0],xy[1])); break;
            case 1: addStock(selected_id); break;
            case 2: editAttribute(selected_id, xy[1]); break;
            case 3: delete(selected_id); break;
        }
    }
}