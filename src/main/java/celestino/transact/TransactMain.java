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


    static void submitOrder() {

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

    }


    static void sellItemSelected(int xy[]) {

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
            if(TransactPage.getRefundTableValue(i, 0).equals(TransactPage.getRefundTableValue(new_item_index, 0))) {
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
