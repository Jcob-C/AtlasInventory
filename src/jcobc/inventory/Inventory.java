package jcobc.inventory;
import jcobc.inventory.layouts.*;
import jcobc.main.*;

public class Inventory {

    private static String 
        cachedInventory[][] = null,
        columnNames[] = {"ID","Barcode","Name","Location","Type","Description","Price","Stock"};

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
            case "newItem": createNewItem(); 
            break;
            case "sortInventory": sortInventory(); 
            break;
            case "cellSelected": cellSelected(); 
            break;
            default: System.out.println("Unmapped Action: "+action);
        }
    }


    public static String[][] cachedInventory() {
        return cachedInventory;
    }


    public static void updateCachedInventory() {
        cachedInventory = Database.getTable("inventory", 8);
    }


    private static void cellSelected() {
        int selectedCell[] = inventoryPage.getSelected(),
            selectedID = Integer.parseInt(cachedInventory[selectedCell[0]][0]); 
        String selectedCellContent = cachedInventory[selectedCell[0]][selectedCell[1]],
            selectedColumnName = columnNames[selectedCell[1]];
            
        switch (Interface.popupOptionsChoiceIndex(new String[]{"Edit Attribute","Delete Row"}, selectedColumnName+": "+selectedCellContent)) {
            case 0:
                String newValue = Interface.popupInput("Enter the new "+selectedColumnName);
                if (
                    newValue != null && 
                    Database.editInventoryAttribute(selectedID, selectedCell[1], newValue, columnDataType(selectedCell[1]))
                    ) 
                {
                    Interface.popupMessage("Item Attribute Updated");
                } 
            break;
            case 1:
                if (Database.deleteRow("inventory", selectedID))
                    Interface.popupMessage("Selected Row Deleted");
            break;
        }
        updateCachedInventory();
        inventoryPage.updateTable(cachedInventory);
    }


    private static void gotoInventory() {
        updateCachedInventory();
        inventoryPage.updateTable(cachedInventory);
        Interface.changePage("inventory");
    }


    private static void createNewItem() {
        if (Database.inventoryInsert(newItemPage.getNewItemInput())) {
            Interface.popupMessage("New Item Added");
            newItemPage.clearNewItemInput();
            gotoInventory();
        }
    }


    private static void sortInventory() {
        int column = Interface.popupOptionsChoiceIndex(new String[]
            {"ID","Barcode","Name","Location","Type","Price","Stock"}, "Sort by?");
        if (column == -1) 
            return;

        int ascending = Interface.popupOptionsChoiceIndex(new String[]
            {"Ascending","Descending"}, "Order by?");
        if (ascending == -1) 
            return;

        if (column == 5 || column == 6) 
            numSortCachedInventory(column+1, ascending==0);
        else 
            stringSortCachedInventory(column, ascending==0);

        inventoryPage.updateTable(cachedInventory);
    }


    private static void stringSortCachedInventory(int column, boolean ascending) {
        boolean unsorted = true;
        String[] holder = null;
        while (unsorted) {
            unsorted = false;
            for (int i = 1; i < cachedInventory.length; i++) {
                int comparison = cachedInventory[i-1][column].compareToIgnoreCase(cachedInventory[i][column]);
                if ( 
                    (comparison > 0 && ascending) ||
                    (comparison < 0 && !ascending) 
                    ) 
                {
                    holder = cachedInventory[i-1];
                    cachedInventory[i-1] = cachedInventory[i];
                    cachedInventory[i] = holder;
                    unsorted = true;
                }
            }
        }
    }


    private static void numSortCachedInventory(int column, boolean ascending) {
        boolean unsorted = true;
        String[] holder = null;
        while (unsorted) {
            unsorted = false;
            for (int i = 1; i < cachedInventory.length; i++) {
                double 
                    prev = toDouble(cachedInventory[i-1][column]), 
                    current = toDouble(cachedInventory[i][column]);
                if ( 
                    (prev > current && ascending) ||
                    (prev < current && !ascending) 
                    ) 
                {
                    holder = cachedInventory[i-1];
                    cachedInventory[i-1] = cachedInventory[i];
                    cachedInventory[i] = holder;
                    unsorted = true;
                }
            }
        }
    }
    
    
    private static String columnDataType(int index) {
        switch (index) {
            case 6: return "double";
            case 7: return "int";
            default: return "string";
        }
    }

    
    private static double toDouble(String numString) {
        try {
            return Double.parseDouble(numString);
        } 
        catch (Exception _) {
            Interface.popupMessage("Double.parseDouble() Error");
        } 
        return 0d;
    }
}