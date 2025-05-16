package celestino.transact;

import celestino.PresetJTable;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import base.Main;

public class TransactPage {

    private static final JPanel panel = new JPanel(null);
    private static String target_table = null;
    
    private static final PresetJTable
        refund_table = new PresetJTable(
            new String[] {"ID","Name","Type","Price","Quantity","Condition"},
            TransactMain::refundItemSelected
        ),
        sell_table = new PresetJTable(
            new String[] {"ID","Name","Type","Price","Quantity"},
            TransactMain::sellItemSelected
        );
    private static final JLabel 
        refund_total = new JLabel("Total: 0.0"),
        sell_total = new JLabel("Total: 0.0");
    private static final JTextField customer_field = new JTextField();
    

    static void setRefundTotal(String total) {
        refund_total.setText(total);
    }


    static void setSellTotal(String total) {
        sell_total.setText(total);
    }


    static String getTargetTable() {
        return target_table;
    }

    
    static String getCustomerName() {
        return customer_field.getText();
    }


    static ArrayList<ArrayList<String>> getRefundTable() {
        return refund_table.getTable();
    }


    static ArrayList<ArrayList<String>> getSellTable() {
        return sell_table.getTable();
    }


    static void addRefundItem(String[] item) {
        refund_table.addRow(item);
    }


    static void addSellItem(String[] item) {
        sell_table.addRow(item);
    }


    static Double getTransactionTotal() {
        return Double.parseDouble(sell_total.getText().substring(7))-Double.parseDouble(refund_total.getText().substring(7));
    }


    static int getRefundRowCount() {
        return refund_table.getRowCount();
    }


    static int getSellRowCount() {
        return sell_table.getRowCount();
    }


    static String getRefundTableValue(int x, int y) {
        return String.valueOf(refund_table.getValueAt(x, y));
    }


    static String getSellTableValue(int x, int y) {
        return String.valueOf(sell_table.getValueAt(x, y));
    }


    static void setRefundTableValue(int x, int y, String value) {
        refund_table.setValueAt(value, x, y);
    }


    static void setSellTableValue(int x, int y, String value) {
        sell_table.setValueAt(value, x, y);
    }


    static void clearNameField() {
        customer_field.setText("");
    }


    static void removeRefundTableRow(int row) {
        refund_table.deleteRow(row);
    }


    static void removeSellTableRow(int row) {
        sell_table.deleteRow(row);
    }


    static void resetSellTable() {
        sell_table.updateTable(null); 
        setSellTotal("Total: 0.0");
    }


    static void resetRefundTable() {
        refund_table.updateTable(null); 
        setRefundTotal("Total: 0.0");
    }


    static JPanel createPanel() {
        panel.setBackground(Main.getDarkColor());
        JPanel 
            top_bar = new JPanel(),
            bottom_bar = new JPanel();
        JScrollPane 
            refund_pane = new JScrollPane(refund_table),
            sell_pane = new JScrollPane(sell_table);
        JButton
            add_refund = new JButton(Main.add_icon),
            add_sell = new JButton(Main.add_icon),
            transact_button = new JButton("Transact"),
            back_button = new JButton("<"),
            reset_refund = new JButton(Main.refresh_icon),
            reset_sell = new JButton(Main.refresh_icon);
        JLabel 
            name_label = new JLabel("Customer:"),
            refund_label = new JLabel("Refund"),
            sell_label = new JLabel("Sell")
        ;

        reset_refund.setBounds(33,112,40,40);
        reset_sell.setBounds(445,112,40,40);
        refund_label.setBounds(82,117,88,27);
        sell_label.setBounds(511,117,58,27);
        name_label.setBounds(50,34,212,27);
        back_button.setBounds(0,0,45,30);
        top_bar.setBounds(0,0,880,30);
        bottom_bar.setBounds(0,630,880,30);
        transact_button.setBounds(489, 30, 123, 40);
        customer_field.setBounds(175,32,294,32);
        refund_total.setBounds(233,117,202,27);
        sell_total.setBounds(645,117,202,27);
        add_refund.setBounds(170,112,40,40);
        add_sell.setBounds(582,112,40,40);
        refund_pane.setBounds(33,152,401,478);
        sell_pane.setBounds(445,152,401,478);

        refund_label.setFont(Main.getFont(22));
        sell_label.setFont(Main.getFont(22));
        name_label.setFont(Main.getFont(22));
        transact_button.setFont(Main.getFont(20));
        customer_field.setFont(Main.getFont(16));
        refund_total.setFont(Main.getFont(24));
        sell_total.setFont(Main.getFont(24));

        reset_refund.setBackground(Main.getMidColor());
        reset_sell.setBackground(Main.getMidColor());
        refund_label.setForeground(Color.WHITE);
        sell_label.setForeground(Color.WHITE);
        name_label.setForeground(Color.WHITE);
        top_bar.setBackground(Main.getLightColor());
        bottom_bar.setBackground(Main.getLightColor());
        transact_button.setBackground(Main.getMidColor());
        transact_button.setForeground(Color.WHITE);
        add_refund.setBackground(Main.getMidColor());
        add_sell.setBackground(Main.getMidColor());
        refund_total.setForeground(Color.WHITE);
        sell_total.setForeground(Color.WHITE);
        back_button.setBackground(Main.getMidColor());
        back_button.setForeground(Color.WHITE);

        panel.add(reset_refund);
        panel.add(reset_sell);
        panel.add(refund_label);
        panel.add(sell_label);
        panel.add(name_label);
        panel.add(back_button);
        panel.add(transact_button);
        panel.add(customer_field);
        panel.add(refund_total);
        panel.add(sell_total);
        panel.add(refund_pane);
        panel.add(sell_pane);
        panel.add(add_refund);
        panel.add(add_sell);
        panel.add(top_bar);
        panel.add(bottom_bar);

        
        reset_sell.addActionListener(e -> resetSellTable());
        reset_refund.addActionListener(e -> resetRefundTable());
        transact_button.addActionListener(e -> TransactMain.transact());
        add_refund.addActionListener(e -> addToRefund());
        add_sell.addActionListener(e -> addToSell());
        back_button.addActionListener(e -> Main.gotoDashboard());

        return panel;
    }


    private static void addToRefund() {
        target_table = "refund";
        TransactMain.gotoItemSelect();
    }


    private static void addToSell() {
        target_table = "sell";
        TransactMain.gotoItemSelect();
    }
}
