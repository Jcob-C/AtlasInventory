package jcobc.transact;
import jcobc.transact.layouts.*;
import jcobc.main.*;
import jcobc.inventory.Inventory;

public class Transact {

    private static String 
        selectedTable, 
        customerTable[][], 
        inventoryTable[][];

    private static final TransactPage transactPage = new TransactPage();
    private static final ItemSelectPage itemSelectPage = new ItemSelectPage();
    
    
    public static void loadPages() {
        Interface.addPage(transactPage, "transact");
        Interface.addPage(itemSelectPage, "itemSelect");
    }


    public static void callAction(String action) {
        switch(action) {
            case "gotoTransact": gotoTransactionPage();
            break;
            case "gotoHome": Home.callAction("gotoHome");
            break;
            case "addToCustomerList": 
            case "addToInventoryList": gotoItemSelection(action);
            break;
            case "addSelected": addSelectedItem(); 
            break;
            case "listRowSelected": listItemSelected();
            break;
            case "sell": 
            break;
            case "refund":
            break;
            case "order":
            break;
            case "swap":
            break;
            default: System.out.println("Unmapped Action: "+action);
        }
    }


    private static void gotoTransactionPage() {
        clearTransactionPage();
        Interface.changePage("transact");
    }


    private static void gotoItemSelection(String table) {
        selectedTable = table;
        Inventory.updateCachedInventory();
        itemSelectPage.updateTable(Inventory.cachedInventory());
        Interface.changePage("itemSelect");
    }


    private static void clearTransactionPage() {
        customerTable = null;
        inventoryTable = null;
        transactPage.updateTotalPrices(0,0);
        transactPage.updateTablesDisplay(inventoryTable, customerTable);
        transactPage.setButtonsVisibility(false,false,false,false);
    }


    private static void updateTransactionPage() {
        updateDynamicButtons();
        transactPage.updateTablesDisplay(inventoryTable, customerTable);
        transactPage.updateTotalPrices(totalPrice(customerTable,4),totalPrice(inventoryTable,3));
    }


    private static void addSelectedItem() {
        String cachedInventory[][] = Inventory.cachedInventory();
        int selectedRow = itemSelectPage.getSelectedRow();
        String[] 
        newRow = new String[] {
            cachedInventory[selectedRow][0],
            cachedInventory[selectedRow][2],
            cachedInventory[selectedRow][6],
            "OK",
            "1" }, 
        newRow2 = new String[] {
            cachedInventory[selectedRow][0],
            cachedInventory[selectedRow][2],
            cachedInventory[selectedRow][6],
            "1" };
        int rowIndex;
        switch (selectedTable) {
            case "addToCustomerList":
                rowIndex = idRowIndex(customerTable, newRow[0]);
                if (idRowIndex(customerTable, newRow[0]) == -1 || customerTable[rowIndex][3].equals("Defective")) {
                    customerTable = tableWithNewRow(customerTable, newRow);
                }
                else addToQuantity("customer", rowIndex);
            break;
            case "addToInventoryList":
                rowIndex = idRowIndex(inventoryTable, newRow[0]);
                if (idRowIndex(inventoryTable, newRow[0]) == -1) {
                    inventoryTable = tableWithNewRow(inventoryTable, newRow2);
                }
                else addToQuantity("inventory", rowIndex);
            break;
        }
        updateTransactionPage();
        Interface.changePage("transact");
    }


    private static void listItemSelected() {
        int selectedRow = transactPage.getSelectedRow();
        String 
        selectedTable = transactPage.getSelectedTable(),
        options[] = null;
        
        if (selectedTable.equals("customer")) 
            options = new String[]
                {"Remove Item", "Edit Quantity", "Change Condition"};
        else if (selectedTable.equals("inventory"))
            options = new String[]
                {"Remove Item", "Edit Quantity"};

        int optionSelected = Interface.popupOptionsChoiceIndex(options, "");  

        switch (optionSelected) {
            case 0:
                if (selectedTable.equals("customer"))
                    customerTable = tableWithoutRow(customerTable, selectedRow);
                else
                    inventoryTable = tableWithoutRow(inventoryTable, selectedRow);
            break;
            case 1:

            break;
            case 2:
                changeItemCondition(selectedRow);
            break;
        }
        updateTransactionPage();
    }


    private static void changeItemCondition(int rowIndex) {
        switch (customerTable[rowIndex][3]) {
            case "OK": customerTable[rowIndex][3] = "Defective";
            break;
            case "Defective": customerTable[rowIndex][3] = "OK";
            break;
        }
    }


    private static void updateDynamicButtons() {
        if (inventoryTable == null && customerTable == null) {
            transactPage.setButtonsVisibility(false, false, false, false);
        }
        else if (inventoryTable != null && customerTable != null) {
            transactPage.setButtonsVisibility(false, true, false, false);
        }
        else if (inventoryTable == null && customerTable != null) {
            transactPage.setButtonsVisibility(true, false, false, false);
        }
        else if (inventoryTable != null && customerTable == null) {
            transactPage.setButtonsVisibility(false, false, true, true);
        }
    }


    private static void addToQuantity(String table, int index) {
        switch (table) {
            case "customer": 
                customerTable[index][4] = ""+(Integer.parseInt(customerTable[index][4])+1);
            break;
            case "inventory": 
                inventoryTable[index][3] = ""+(Integer.parseInt(inventoryTable[index][3])+1);
            break;
        }
    }


    private static float totalPrice(String[][] table, int quantityIndex) {
        if(table == null) return 0;
        float totalPrice = 0;
        for (String[] x : table) {
            totalPrice += Float.parseFloat(x[2]) * Float.parseFloat(x[quantityIndex]);
        }
        return totalPrice;
    }


    private static int idRowIndex(String[][] table, String id) {
        if (table == null) return -1;
        for (int i = 0; i < table.length; i++) {
            if (table[i][0].equals(id)) {
                return i;
            }
        }
        return -1;
    }


    private static String[][] tableWithNewRow(String[][] table, String[] newRow) {
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


    private static String[][] tableWithoutRow(String[][] table, int rowIndex) {
        if (table == null || table.length == 1) return null;
        String[][] newTable = new String[table.length-1][table[0].length];
        int offset = 0;
        for (int i = 0; i < newTable.length; i++) {
            if (rowIndex == i) {
                offset = 1;
            }
            for (int o = 0; o < table[i].length; o++) {
                newTable[i][o] = table[i+offset][o];
            }
        }
        return newTable;
    }
}