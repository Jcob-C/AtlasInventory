package jcobc.history;

import jcobc.history.layouts.*;
import jcobc.main.*;

import jcobc.home.Home;

public class History {

    private static final HistoryMenuPage historyMenuPage = new HistoryMenuPage();
    private static final TransactionsPage transactionsPage = new TransactionsPage();
    private static final ItemsListPage itemsListPage = new ItemsListPage();

    private static String[][] salesTableCache = null;


    public static void loadPages() {
        Interface.addPage(historyMenuPage, "historyMenu");
        Interface.addPage(transactionsPage, "transactions");
        Interface.addPage(itemsListPage, "itemsList");
    }


    public static void callAction(String action) {
        switch (action) {
            case "gotoHistoryMenu": Interface.changePage("historyMenu");
            break;
            case "gotoHome": Home.callAction("gotoHome");
            break;
            case "transactions":
            case "refreshSales": gotoTransactions();
            break;
            case "saleRowSelected": salesRowSelected();
            break;
            case "searchSaleID": searchTransactID();
            break;
            default: Interface.popupMessage("Unmapped Home Action: "+action);
        }
    }


    private static void gotoTransactions() {
        salesTableCache = Main.numSorted(Database.table("sales", 4), 0, false);
        transactionsPage.updateTableDisplay(salesTableCache);
        Interface.changePage("transactions");
    }


    private static void searchTransactID() {
        Integer searchID = Interface.popupIntegerInput("Enter Transaction ID:");
        if (searchID == null) return;
        gotoTransactions();
        String 
            ID = ""+searchID,
            newTable[][] = null;
        for (String x[] : salesTableCache) {
            if (x[0].equals(ID)) {
                newTable = new String[][]{x};
            }
        }
        salesTableCache = newTable;
        transactionsPage.updateTableDisplay(salesTableCache);
    }


    private static void salesRowSelected() {
        itemsListPage.updateTableDisplay(Database.itemsList(salesTableCache[transactionsPage.selectedRow()][0]));
        Interface.changePage("itemsList");
    }
}
