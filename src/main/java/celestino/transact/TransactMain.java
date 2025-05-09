package celestino.transact;

import java.util.ArrayList;

import celestino.TableBrowserJPanel;
import main.DB;
import main.Main;

public class TransactMain {

    private static final TableBrowserJPanel item_select_panel = new TableBrowserJPanel(
        Main.inventory_columns,
        TransactMain::newItemSelected,
        TransactMain::gotoTransact,
        TransactMain::updateItemSelect,
        TransactMain::refreshItemSelect
    );


    public static void createModule() {
        Main.addCard(TransactPage.createPanel(), "transact");
        Main.addCard(item_select_panel, "transact item select");
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


    static void transact() {
        checkStocks();
        double total = TransactPage.getTransactionTotal();
        if (!Main.popupConfirm("Total Transaction Price: "+total+"\n\nContinue?")) return;
        int sale_id = DB.insertNewSale(new String[]{TransactPage.getCustomerName(),"Celestino",String.valueOf(TransactPage.getTransactionTotal())});
        TransactPage.clearNameField();

        ArrayList<ArrayList<String>> sell_table = TransactPage.getSellTable();
        for (ArrayList<String> x : sell_table) {
            DB.insertSaleItems(String.valueOf(sale_id), x.get(0), x.get(3), x.get(4));
        }
        ArrayList<ArrayList<String>> refund_table = TransactPage.getRefundTable();
        for (ArrayList<String> x : refund_table) {
            if (x.get(5).equals("BAD")) continue;
            DB.insertSaleItems(String.valueOf(sale_id), x.get(0), x.get(3), '-'+x.get(4));
        }

        TransactPage.resetRefundTable();
        TransactPage.resetSellTable();

        Main.popupMessage("Sale recorded!\n\nSale ID: "+sale_id);
    }


    static boolean checkStocks() {
        ArrayList<ArrayList<String>> order_items = TransactPage.getSellTable();
        for (ArrayList<String> x : order_items) {
            if (!DB.checkStock(x.get(0),x.get(4))) {
                Main.popupMessage("Insufficient stock for " + x.get(1));
                return false;
            }
        }
        return true;
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
                mergeRefundTable();
            break;
            case 1:
                TransactPage.removeRefundTableRow(xy[0]);
                updateRefundTotal();
            break;
            case 2:
                String new_quantity = Main.popupInput("Enter Quantity:");
                if (Main.toInteger(new_quantity) == null) {
                    Main.popupMessage("Invalid Quantity");
                    return;
                }
                TransactPage.setRefundTableValue(xy[0], 4, new_quantity);
                updateRefundTotal();
            break;
        }
    }


    static void sellItemSelected(int xy[]) {
        int option = Main.popupOption("Selected Item: " + TransactPage.getSellTableValue(xy[0], 1), new String[]{"Remove Item","Edit Quantity"});
        switch (option) {
            case 0:
                TransactPage.removeSellTableRow(xy[0]);
                updateSellTotal();
            break;
            case 1:
                String new_quantity = Main.popupInput("Enter Quantity:");
                if (Main.toInteger(new_quantity) == null) {
                    Main.popupMessage("Invalid Quantity");
                    return;
                }
                TransactPage.setSellTableValue(xy[0], 4, new_quantity);
                updateSellTotal();
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
        TransactPage.setRefundTotal("Total: "+total);
    }

    
    static void updateSellTotal() {
        Double total = 0d;
        ArrayList<ArrayList<String>> x = TransactPage.getSellTable();
        if (x == null) return;
        for(ArrayList<String> y : x) {
            total += Double.parseDouble(y.get(3))* Double.parseDouble(y.get(4));
        }
        TransactPage.setSellTotal("Total: "+total);
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
                TransactPage.removeSellTableRow(new_item_index);
                return true;
            }
        }
        return false;
    }
}
