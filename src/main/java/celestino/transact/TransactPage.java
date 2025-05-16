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
        refund_total = new JLabel("Refund Total: 0.0"),
        sell_total = new JLabel("Sell Total: 0.0");
    private static final JTextField 
        customer_field = new JTextField(),
        method_field = new JTextField()
    ;
    

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


    static String getPaymentMethod() {
        return method_field.getText();
    }


    static Double getTransactionTotal() {
        return Double.parseDouble(sell_total.getText().substring(12))-Double.parseDouble(refund_total.getText().substring(14));
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


    static void clearMethodField() {
        method_field.setText("");
    }


    static void removeRefundTableRow(int row) {
        refund_table.deleteRow(row);
    }


    static void removeSellTableRow(int row) {
        sell_table.deleteRow(row);
    }


    static void resetSellTable() {
        sell_table.updateTable(null); 
        setSellTotal("Sell Total: 0.0");
    }


    static void resetRefundTable() {
        refund_table.updateTable(null); 
        setRefundTotal("Refund Total: 0.0");
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
            reset_sell = new JButton(Main.refresh_icon),
            refund_scan = new JButton(Main.scanner_icon),
            sell_scan = new JButton(Main.scanner_icon);
        JLabel 
            name_label = new JLabel("Customer:"),
            method_label = new JLabel("Payment Method:")
        ;

        reset_refund.setBounds(146,112,40,40);
        reset_sell.setBounds(557,112,40,40);
        name_label.setBounds(193,37,212,27);
        back_button.setBounds(0,0,45,30);
        top_bar.setBounds(0,0,880,30);
        bottom_bar.setBounds(0,630,880,30);
        transact_button.setBounds(654, 49, 123, 40);
        customer_field.setBounds(323,35,294,32);
        refund_total.setBounds(197,121,238,22);
        sell_total.setBounds(608,121,238,22);
        add_refund.setBounds(44,112,40,40);
        add_sell.setBounds(456,112,40,40);
        refund_pane.setBounds(33,152,401,478);
        sell_pane.setBounds(445,152,401,478);
        method_label.setBounds(111,76,212,27);
        method_field.setBounds(322,73,294,32);  
        refund_scan.setBounds(95,112,40,40);
        sell_scan.setBounds(506,112,40,40);

        method_field.setFont(Main.getFont(16));
        method_label.setFont(Main.getFont(22));
        name_label.setFont(Main.getFont(22));
        transact_button.setFont(Main.getFont(20));
        customer_field.setFont(Main.getFont(16));
        refund_total.setFont(Main.getFont(24));
        sell_total.setFont(Main.getFont(24));

        method_label.setForeground(Color.WHITE);
        reset_refund.setBackground(Main.getMidColor());
        reset_sell.setBackground(Main.getMidColor());
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
        refund_scan.setBackground(Main.getMidColor());
        sell_scan.setBackground(Main.getMidColor());

        panel.add(refund_scan);
        panel.add(sell_scan);
        panel.add(method_field);
        panel.add(method_label);
        panel.add(reset_refund);
        panel.add(reset_sell);
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
        refund_scan.addActionListener(e -> {target_table = "refund"; TransactMain.gotoScan();});
        sell_scan.addActionListener(e -> {target_table = "sell"; TransactMain.gotoScan();});

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
