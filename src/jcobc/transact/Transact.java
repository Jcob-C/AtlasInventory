package jcobc.transact;
import jcobc.transact.layouts.*;
import jcobc.main.*;

import jcobc.home.Home;

public class Transact {

    private static String 
        addToTable,
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
            case "gotoTransact": gotoTransact();
            break;
            case "gotoHome": gotoHome();
            break;
            case "addToCustomerList":
            case "addToInventoryList": gotoItemSelect(action);
            break;
            case "addSelected": addSelected();
            break;
            case "listRowSelected": editListItem();
            break;
            default: Interface.popupMessage("Unmapped Action: "+action);
        }
    }


    private static void gotoTransact() {
        mergeRepeats();
        enableAppropriateButtons();
        transactPage.updateTablesDisplay(inventoryTable, customerTable);
        transactPage.updateTotalPrices(totalPrice(customerTable), totalPrice(inventoryTable));
        Interface.changePage("transact");
    }


    private static void gotoHome() {
        customerTable = null; inventoryTable = null;
        Home.callAction("gotoHome");
    }


    private static double totalPrice(String[][] table) {
        if (table == null) return 0;
        double totalPrice = 0;
        for (String x[] : table) {
            totalPrice += (Main.toDouble(x[2]) * Main.toInteger(x[x.length-1]));
        }
        return totalPrice;
    }


    private static void enableAppropriateButtons() {
        if (customerTable == null && inventoryTable == null) {
            transactPage.setButtonsVisibility(false, false, false, false);
        }
        else if (customerTable == null && inventoryTable != null) {
            transactPage.setButtonsVisibility(false, false, true, true);
        }
        else if (customerTable != null && inventoryTable == null) {
            transactPage.setButtonsVisibility(true, false, false, false);
        }
        else if (customerTable != null && inventoryTable != null) {
            transactPage.setButtonsVisibility(false, true, false, false);
        }
    }


    public static void gotoItemSelect(String action) {
        switch (action) {
            case "addToCustomerList": addToTable = "cus";
            break;
            case "addToInventoryList": addToTable = "inv";
            break;
        }
        itemSelectPage.updateTablesDisplay(Database.updateInventoryCache());
        Interface.changePage("itemSelect");
    }


    private static void addSelected() {
        if (addToTable.equals("cus")) {
            String
            selectedRow[] = Database.inventoryCache()[itemSelectPage.selectedRow()],
            newRow[] = {
                selectedRow[0],
                selectedRow[2],
                selectedRow[6],
                "OK",
                "1",
            };
            customerTable = Main.withNewRow(customerTable, newRow);
        }
        else if (addToTable.equals("inv")) {
            String
            selectedRow[] = Database.inventoryCache()[itemSelectPage.selectedRow()],
            newRow[] = {
                selectedRow[0],
                selectedRow[2],
                selectedRow[6],
                "1",
            };
            inventoryTable = Main.withNewRow(inventoryTable, newRow);
        }
        gotoTransact();
    }


    private static void mergeRepeats() {
        if (customerTable == null) return;
        for (int i = 0; i < customerTable.length; i++) {
            for (int o = 0; o < customerTable.length; o++) {
                if (
                    i != o && 
                    customerTable[i][0].equals(customerTable[o][0]) && 
                    customerTable[i][3].equals(customerTable[o][3])
                    )
                {
                    customerTable[i][4] = String.valueOf(
                        Main.toInteger(customerTable[i][4])
                        +
                        Main.toInteger(customerTable[o][4])
                        );
                    customerTable = Main.withoutRow(customerTable, o);
                }
            }
        }
        if (inventoryTable == null) return;
        for (int i = 0; i < inventoryTable.length; i++) {
            for (int o = 0; o < inventoryTable.length; o++) {
                if (
                    i != o && 
                    inventoryTable[i][0].equals(inventoryTable[o][0])
                    )
                {
                    inventoryTable[i][3] = String.valueOf (
                        Main.toInteger(inventoryTable[i][3])
                        +
                        Main.toInteger(inventoryTable[o][3])
                        );
                    inventoryTable = Main.withoutRow(inventoryTable, o);
                }
            }
        }
        transactPage.updateTablesDisplay(inventoryTable, customerTable);
    }


    private static void editListItem() {
        String selectedTable = transactPage.selectedTable();
        int selectedRow = transactPage.selectedRow();
        if (selectedTable.equals("cus")) {
            String options[] = {"Remove Item","Change Condition","Edit Quantity"};
            int selectedOption = Interface.popupOptionsChosenIndex(options,"");
            switch (selectedOption) {
                case 0:
                    customerTable = Main.withoutRow(customerTable, selectedRow);
                break;
                case 1:
                    if (customerTable[selectedRow][3].equals("OK")) {
                        customerTable[selectedRow][3] = "Defective";
                    }
                    else if (customerTable[selectedRow][3].equals("Defective")) {
                        customerTable[selectedRow][3] = "OK";
                    }
                break;
                case 2:
                    Integer newQuantity =Interface.popupIntegerInput("Enter new Quantity");
                    if (newQuantity != null) {
                        customerTable[selectedRow][4] = String.valueOf(newQuantity);
                    }
                break;
            
            }
        }
        else if (selectedTable.equals("inv")) {
            String options[] = {"Remove Item","Edit Quantity"};
            int selectedOption = Interface.popupOptionsChosenIndex(options,"");
            switch (selectedOption) {
                case 0:
                    inventoryTable = Main.withoutRow(inventoryTable, selectedRow);
                break;
                case 1:
                    Integer newQuantity =Interface.popupIntegerInput("Enter new Quantity");
                    if (newQuantity != null) {
                        inventoryTable[selectedRow][4] = String.valueOf(newQuantity);
                    }
                break;
            }
        } 
        gotoTransact();
    }
}