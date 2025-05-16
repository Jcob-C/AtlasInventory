package celestino.transact;

import java.util.ArrayList;

import base.DB;
import base.Main;
import celestino.ScannerJPanel;
import celestino.TableBrowserJPanel;
import delarama.UserActivity;

public class TransactMain {

    private static final TableBrowserJPanel item_select_panel = new TableBrowserJPanel(
        Main.inventory_columns,
        TransactMain::newItemSelected,
        TransactMain::gotoTransact,
        TransactMain::updateItemSelect,
        TransactMain::refreshItemSelect);
    private static final ScannerJPanel scan_panel = new ScannerJPanel(TransactMain::scanned, TransactMain::gotoTransact);


    public static void createModule() {
        Main.addCard(TransactPage.createPanel(), "transact");
        Main.addCard(item_select_panel, "transact item select");
        Main.addCard(scan_panel, "transact scan");

        item_select_panel.setTitle("SELECT an item to ADD on TRANSACTION");
    }


    public static void gotoScan() {
        scan_panel.clearBuffer();
        Main.changeCard("transact scan");
        scan_panel.requestFocusInWindow();
    }


    public static void gotoTransact() {
        Main.changeCard("transact");
    }


    static void gotoItemSelect() {
        refreshItemSelect();
        Main.changeCard("transact item select");
    }


    static void updateItemSelect() {
        item_select_panel.updateTable(DB.getSearchSortedInventoryTable(item_select_panel.getSearchInput(),item_select_panel.getSortColumnIndex(),item_select_panel.getSortOrder()));
    }


    static void refreshItemSelect() {
        item_select_panel.resetSortFilter();
        item_select_panel.updateTable(DB.getInventoryTable());
    }


    static void scanned(String scan) {
        ArrayList<String> item = DB.getItem(scan);
        switch(TransactPage.getTargetTable()) {
            case "refund":
                TransactPage.addRefundItem(
                    new String[] {
                        item.get(0),
                        item.get(1),
                        item.get(2),
                        item.get(3),
                        "1",
                        "OK"
                    }
                );
                mergeRefundTable();
                updateRefundTotal();
            break;
            case "sell":
                TransactPage.addSellItem(
                    new String[] {
                        item.get(0),
                        item.get(1),
                        item.get(2),
                        item.get(3),
                        "1"
                    }
                );
                mergeSellTable();
                updateSellTotal();
            break;
        }
        gotoTransact();
    }


    static void transact() {
        if (!checkStocks()) return;

        double total = TransactPage.getTransactionTotal();
        ArrayList<ArrayList<String>> sell_table = TransactPage.getSellTable();
        ArrayList<ArrayList<String>> refund_table = TransactPage.getRefundTable();

        if (sell_table.size() <= 0 && refund_table.size() <= 0) return;
        
        if (!Main.popupConfirm("Total Transaction Price: "+total+"\n\nContinue?")) return;

        int sale_id = DB.insertNewSale(new String[]{TransactPage.getCustomerName(),"Celestino",String.valueOf(total),TransactPage.getPaymentMethod()});
        
        UserActivity.Audit_Trail("Transaction complete with sale ID " + sale_id + " for customer " + TransactPage.getCustomerName() + " with total: " + total);
        TransactPage.clearNameField();
        TransactPage.clearMethodField();

        for (ArrayList<String> x : sell_table) {
            DB.insertSaleItems(String.valueOf(sale_id), x.get(0), x.get(3), x.get(4));
            DB.addStock(x.get(0), '-'+x.get(4));
            UserActivity.Audit_Trail("Added sale item with ID of " + x.get(0) + " with price: " + x.get(3) + " with quantity: " + x.get(4) + " with the sale ID: " + sale_id);
        }
        for (ArrayList<String> x : refund_table) {
            if (x.get(5).equals("BAD")) continue;
            DB.insertSaleItems(String.valueOf(sale_id), x.get(0), x.get(3), '-'+x.get(4));
            UserActivity.Audit_Trail("Added refund item with ID of " + x.get(0) + " with price: " + x.get(3) + " with quantity: " + x.get(4) + " with the sale ID: " + sale_id);
        }

        TransactPage.resetRefundTable();
        TransactPage.resetSellTable();

        Main.popupMessage("New Stock & Sale recorded!\n\nSale ID: "+sale_id);
    }


    static boolean checkStocks() {
        boolean output = true;
        ArrayList<ArrayList<String>> order_items = TransactPage.getSellTable();
        for (ArrayList<String> x : order_items) {
            if (!DB.checkStock(x.get(0),x.get(4))) {
                Main.popupMessage("Insufficient stock for " + x.get(1));
                output = false;
            }
        }
        return output;
    }


    static void newItemSelected(int xy[]) {
        if(xy[0] == -1 || xy[1] == -1) return;
        switch(TransactPage.getTargetTable()) {
            case "refund":
                TransactPage.addRefundItem(
                    new String[] {
                        item_select_panel.getValueAt(xy[0], 0),
                        item_select_panel.getValueAt(xy[0], 2),
                        item_select_panel.getValueAt(xy[0], 3),
                        item_select_panel.getValueAt(xy[0], 6),
                        "1",
                        "OK"
                    }
                );
            UserActivity.Audit_Trail("Added a refund item with ID: " + item_select_panel.getValueAt(xy[0], 0));
            break;
            case "sell":
                TransactPage.addSellItem(
                    new String[] {
                        item_select_panel.getValueAt(xy[0], 0),
                        item_select_panel.getValueAt(xy[0], 2),
                        item_select_panel.getValueAt(xy[0], 3),
                        item_select_panel.getValueAt(xy[0], 6),
                        "1"
                    }
                );
            UserActivity.Audit_Trail("Added a sell item with ID: " + item_select_panel.getValueAt(xy[0], 0));    
            break;
        }
        mergeRefundTable();
        mergeSellTable();
        updateSellTotal();
        updateRefundTotal();
        gotoTransact();
    }


    static void refundItemSelected(int xy[]) {
        int option = Main.popupOption("Selected Item: " + TransactPage.getRefundTableValue(xy[0], 1), new String[]{"Change Condition","Remove Item","Edit Quantity"});
        switch (option) {
            case 0:
                if (TransactPage.getRefundTableValue(xy[0], 5).equals("OK")) {
                    TransactPage.setRefundTableValue(xy[0], 5, "BAD");
                }
                else {
                    TransactPage.setRefundTableValue(xy[0], 5, "OK");
                }
                UserActivity.Audit_Trail("Change the refund item condition of " + TransactPage.getRefundTableValue(xy[0], 1) + " to " + (TransactPage.getRefundTableValue(xy[0], 5).equals("OK") ? "BAD" : "OK"));
                mergeRefundTable();
            break;
            case 1:
                TransactPage.removeRefundTableRow(xy[0]);
                UserActivity.Audit_Trail("Removed the refund item " + TransactPage.getRefundTableValue(xy[0], 1));
                updateRefundTotal();
            break;
            case 2:
                String new_quantity = Main.popupInput("Enter Quantity:");
                if (Main.toInteger(new_quantity) != null && Main.toInteger(new_quantity) >= 1) {
                    TransactPage.setRefundTableValue(xy[0], 4, new_quantity);
                    UserActivity.Audit_Trail("Changed the refund item " + TransactPage.getRefundTableValue(xy[0], 1) + " quantity to " + new_quantity);
                    updateRefundTotal();
                }
                else {
                    Main.popupMessage("Invalid Quantity");
                }
            break;
        }
    }


    static void sellItemSelected(int xy[]) {
        int option = Main.popupOption("Selected Item: " + TransactPage.getSellTableValue(xy[0], 1), new String[]{"Remove Item","Edit Quantity"});
        switch (option) {
            case 0:
                TransactPage.removeSellTableRow(xy[0]);
                UserActivity.Audit_Trail("Removed sell item " + TransactPage.getSellTableValue(xy[0], 1));
                updateSellTotal();
            break;
            case 1:
                String new_quantity = Main.popupInput("Enter Quantity:");
                if (Main.toInteger(new_quantity) != null && Main.toInteger(new_quantity) >= 1) {
                    TransactPage.setSellTableValue(xy[0], 4, new_quantity);
                    UserActivity.Audit_Trail("Changed the sell item " + TransactPage.getSellTableValue(xy[0], 1) + " quantity to " + new_quantity);
                    updateSellTotal();
                }
                else {
                    Main.popupMessage("Invalid Quantity");
                }
                
            break;
        }
    }


    static void updateRefundTotal() {
        Double total = 0d;
        ArrayList<ArrayList<String>> x = TransactPage.getRefundTable();
        if (x == null) return;
        for(ArrayList<String> y : x) {
            total += Double.parseDouble(y.get(3))* Double.parseDouble(y.get(4));
        }
        TransactPage.setRefundTotal("Refund Total: "+total);
    }

    
    static void updateSellTotal() {
        Double total = 0d;
        ArrayList<ArrayList<String>> x = TransactPage.getSellTable();
        if (x == null) return;
        for(ArrayList<String> y : x) {
            total += Double.parseDouble(y.get(3))* Double.parseDouble(y.get(4));
        }
        TransactPage.setSellTotal("Sell Total: "+total);
    }


    static boolean mergeRefundTable() {
        int new_item_index = TransactPage.getRefundRowCount()-1;
        for(int i = new_item_index-1; i >= 0; i--) {
            if(TransactPage.getRefundTableValue(i, 0).equals(TransactPage.getRefundTableValue(new_item_index, 0)) && TransactPage.getRefundTableValue(i, 5).equals(TransactPage.getRefundTableValue(new_item_index, 5))) {
                TransactPage.setRefundTableValue(i, 4, String.valueOf(
                        Main.toInteger(TransactPage.getRefundTableValue(i, 4))
                        +
                        Main.toInteger(TransactPage.getRefundTableValue(new_item_index, 4))
                    )
                );
                UserActivity.Audit_Trail("Merged refund item quantities for " + TransactPage.getRefundTableValue(i, 1) + " new quantities " + (Main.toInteger(TransactPage.getRefundTableValue(i, 4)) + Main.toInteger(TransactPage.getRefundTableValue(new_item_index, 4))));
                TransactPage.removeRefundTableRow(new_item_index);
                return true;
            }
        }
        return false;
    }


    static boolean mergeSellTable() {
        int new_item_index = TransactPage.getSellRowCount()-1;
        for(int i = new_item_index-1; i >= 0; i--) {
            if(TransactPage.getSellTableValue(i, 0).equals(TransactPage.getSellTableValue(new_item_index, 0))) {
                TransactPage.setSellTableValue(i, 4, String.valueOf(
                        Main.toInteger(TransactPage.getSellTableValue(i, 4))
                        +
                        Main.toInteger(TransactPage.getSellTableValue(new_item_index, 4))
                    )
                );
                UserActivity.Audit_Trail("Merged sell item quantities for " + TransactPage.getSellTableValue(i, 1) + " new quantities " + (Main.toInteger(TransactPage.getSellTableValue(i, 4)) + Main.toInteger(TransactPage.getSellTableValue(new_item_index, 4))));
                TransactPage.removeSellTableRow(new_item_index);
                return true;
            }
        }
        return false;
    }
}
