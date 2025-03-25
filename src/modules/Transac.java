package modules;

public class Transac {

    private static String 
        selectedTable, 
        customerTable[][], 
        inventoryTable[][];


    static void gotoTransactionPage() {
        customerTable = null;
        inventoryTable = null;
        Pages.TransactionPage.updateTotalPrices(0,0);
        Pages.TransactionPage.updateTablesDisplay(inventoryTable, customerTable);
        Interface.changePage("transaction");
    }


    static void gotoItemSelection(String table) {
        selectedTable = table;
        Inventory.updateCachedInventory();
        Pages.itemSelectPage.updateTable(Inventory.getCachedInventory());
        Interface.changePage("itemSelect");
    }


    static void addSelectedItem() {
        String cachedInventory[][] = Inventory.getCachedInventory();
        int selectedRow = Pages.itemSelectPage.getSelectedRow();
        String[] newRow = new String[] {
            cachedInventory[selectedRow][0],
            cachedInventory[selectedRow][3],
            cachedInventory[selectedRow][6],
            "OK",
            "1"
        };
        int rowIndex;
        switch (selectedTable) {
            case "addToCustomerList":
                rowIndex = findIDIn(customerTable, newRow[0]);
                if (findIDIn(customerTable, newRow[0]) == -1) {
                    customerTable = addToTable(customerTable, newRow);
                }
                else {
                    addQuantity("customer", rowIndex);
                }
            break;
            case "addToInventoryList":
                rowIndex = findIDIn(inventoryTable, newRow[0]);
                if (findIDIn(inventoryTable, newRow[0]) == -1) {
                    inventoryTable = addToTable(inventoryTable, newRow);
                }
                else {
                    addQuantity("inventory", rowIndex);
                }
            break;
        }
        Pages.TransactionPage.updateTablesDisplay(inventoryTable, customerTable);
        Pages.TransactionPage.updateTotalPrices(getTotalPrice(customerTable),getTotalPrice(inventoryTable));
        Interface.changePage("transaction");
    }


    static void listRowSelected() {
        System.out.println("selected row in list table");
    }


    private static float getTotalPrice(String[][] table) {
        if(table == null) return 0;
        float totalPrice = 0;
        for (String[] x : table) {
            totalPrice += Float.parseFloat(x[2]) * Float.parseFloat(x[4]);
        }
        return totalPrice;
    }


    private static void addQuantity(String table, int index) {
        switch (table) {
            case "customer": 
                customerTable[index][4] = ""+(Integer.parseInt(customerTable[index][4])+1);
            break;
            case "inventory": 
                inventoryTable[index][4] = ""+(Integer.parseInt(inventoryTable[index][4])+1);
            break;
        }
    }


    private static int findIDIn(String[][] table, String id) {
        if (table == null) return -1;
        for (int i = 0; i < table.length; i++) {
            if (table[i][0].equals(id)) {
                return i;
            }
        }
        return -1;
    }


    private static String[][] addToTable(String[][] table, String[] newRow) {
        if (table == null) {
            return new String[][]{newRow};
        }
        String[][] newTable = new String[table.length+1][table[0].length];
        for (int i = 0; i < table.length; i++) {
            for (int o = 0; o < table[i].length; o++) {
                newTable[i][o] = table[i][o];
            }
        }
        for (int i = 0; i < newRow.length; i++) {
            newTable[table.length][i] = newRow[i];
        }
        return newTable;
    }
}