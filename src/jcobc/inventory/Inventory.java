package jcobc.inventory;
import jcobc.inventory.layouts.*;
import jcobc.main.*;

import jcobc.home.Home;

public class Inventory {

    private static String
        columnNames[] = {
            "ID","Barcode","Name","Location","Type","Description","Price","Stock"
        };

    private static final InventoryPage inventoryPage = new InventoryPage();
    private static final NewItemPage newItemPage = new NewItemPage();


    public static void loadPages() {
        Interface.addPage(inventoryPage, "inventory");
        Interface.addPage(newItemPage, "newItem");
    }


    public static void callAction(String action) {
        switch(action) {
            case "gotoInventory": gotoInventory(); 
            break;
            case "gotoNewItem": Interface.changePage("newItem");
            break;
            case "gotoHome": Home.callAction("gotoHome");
            break;
            case "newItem": insertNewItem(); 
            break;
            case "sortInventory": sortInventory(); 
            break;
            case "cellSelected": cellSelected(); 
            break;
            case "search": filterInventoryCache();
            break; 
            default: Interface.popupMessage("Unmapped Inventory Action: "+action);
        }
    }


    private static void gotoInventory() {
        inventoryPage.updateTableDisplay(Database.updateInventoryCache());
        Interface.changePage("inventory");
    }

    
    private static void insertNewItem() {
        boolean inserted = Database.inventoryInsert(newItemPage.newItemInputs());
        if (inserted) {
            newItemPage.clearNewItemInput();
            Interface.popupMessage("New Item Added");
            gotoInventory();
        }
    }


    private static void sortInventory() {
        String[] 
            options1 = {"ID","Barcode","Name","Location","Type","Price","Stock"},
            options2 = {"Ascending", "Descending"};

        int choice1 = Interface.popupOptionsChosenIndex(options1, "Sort by:");
        if (choice1 == -1) return;
        int choice2 = Interface.popupOptionsChosenIndex(options2, "Order by:");
        if (choice2 == -1) return;

        boolean ascending = true;
        if (choice2 == 1) ascending = false;
        if (choice1 > 4) choice1 ++;
        
        if (choice1 > 4 || choice1 == 0) {
            inventoryPage.updateTableDisplay(Main.numSorted(Database.inventoryCache(), choice1, ascending));
        }
        else {
            inventoryPage.updateTableDisplay(Main.strSorted(Database.inventoryCache(), choice1, ascending));
        }
    }


    private static void cellSelected() {
        String 
            inventoryCache[][] = Database.inventoryCache(),
            options[] = {"Restock","Edit","Delete"};
        Integer
            selectedCell[] = inventoryPage.selectedCell(),
            selectedRow = selectedCell[0],
            selectedColumn = selectedCell[1],
            selectedID = Main.toInteger(inventoryCache[selectedRow][0]),
            optionChosen = 
                Interface.popupOptionsChosenIndex(
                    options,
                    columnNames[selectedColumn]+": "+inventoryCache[selectedRow][selectedColumn]
                );
        switch (optionChosen) {
            case 0:
                int newStock = Interface.popupIntegerInput("Enter amount to add:");
                Database.addStock(newStock, selectedID);
            break;
            case 1:
                String newValue = Interface.popupInput(
                    "Enter the new "+columnNames[selectedColumn]
                );
                Database.editInventoryAttribute(selectedID, selectedColumn, newValue);
            break;
            case 2:
                Database.deleteRow("inventory", selectedID);
            break;
        }    
        gotoInventory();
    }


    private static void filterInventoryCache() {
        String 
            searchWord = Interface.popupInput("Enter the keyword:"),
            newInventoryCache[][] = null,
            inventoryCache[][] = Database.updateInventoryCache();
        if (searchWord == null) return;
        for (String x[] : inventoryCache) {
            for (String y : x) {
                if (y.contains(searchWord)) {
                    newInventoryCache = Main.withNewRow(newInventoryCache, x);
                    break;
                }
            }
        }
        Database.setInventoryCache(newInventoryCache);
        inventoryPage.updateTableDisplay(Database.inventoryCache());
    }
}