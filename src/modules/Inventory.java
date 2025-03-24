package modules;

public class Inventory {

    private static String 
        cachedInventory[][] = null,
        columnNames[] = {"ID","Barcode","Name","Location","Type","Description","Price","Stock"};


    static void cellSelected() {
        int selectedCell[] = Pages.InventoryPage.getSelected(),
            selectedID = Integer.parseInt(cachedInventory[selectedCell[0]][0]); 
        String selectedCellContent = cachedInventory[selectedCell[0]][selectedCell[1]],
            selectedColumnName = columnNames[selectedCell[1]];
            
        switch (Interface.popupOptions(new String[]{"Edit Attribute","Delete Row"}, selectedColumnName+": "+selectedCellContent)) {
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
    }


    static void gotoInventory() {
        updateCachedInventory();
        Interface.changePage("inventory");
    }


    static void createNewItem() {
        if (Database.inventoryInsert(Pages.NewItemPage.getNewItemInput())) {
            Interface.popupMessage("New Item Added");
            Pages.NewItemPage.clearNewItemInput();
            gotoInventory();
        }
    }


    static void sortInventory() {
        int column = Interface.popupOptions(new String[]
            {"ID","Barcode","Name","Location","Type","Price","Stock"}, "Sort by?");
        if (column == -1) 
            return;

        int ascending = Interface.popupOptions(new String[]
            {"Ascending","Descending"}, "Order by?");
        if (ascending == -1) 
            return;

        if (column == 5 || column == 6) 
            sortNums(column+1, ascending==0);
        else 
            sortString(column, ascending==0);

        Pages.InventoryPage.updateTable(cachedInventory);
    }


    static String[] getCachedInventoryRow(int i) {
        return cachedInventory[i];
    }


    static void updateCachedInventory() {
        cachedInventory = Database.getTable("inventory", 8);
        Pages.InventoryPage.updateTable(cachedInventory);
    }


    static String[][] getCachedInventory() {
        return cachedInventory;
    }
 

    private static String columnDataType(int index) {
        switch (index) {
            case 6: return "double";
            case 7: return "int";
            default: return "string";
        }
    }


    private static void sortString(int column, boolean ascending) {
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


    private static void sortNums(int column, boolean ascending) {
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
