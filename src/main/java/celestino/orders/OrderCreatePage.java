package celestino.orders;

import celestino.PresetJTable;
import main.Main;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class OrderCreatePage {

    private static final JPanel panel = new JPanel();
    private static final String[] columns = {"ID","Name","Type","Price","Quantity"};
    private static final PresetJTable table = new PresetJTable(columns, OrdersMain::selectOrderItem);
    private static final JTextField
        customer_name_field = new JTextField(),
        contact_field = new JTextField(),
        payment_id = new JTextField();
    private static final JTextArea address_field = new JTextArea();
    private static final JLabel total_price = new JLabel("Total: 0.0");


    static String[] getOrderInfo() {
        return new String[] {
            customer_name_field.getText(),
            contact_field.getText(),
            address_field.getText(),
            payment_id.getText(),
            total_price.getText().substring(7)
        };
    }


    static ArrayList<ArrayList<String>> getTable() {
        return table.getTable();
    }


    static void clearInputs() {
        customer_name_field.setText("");
        contact_field.setText("");
        address_field.setText("");
        payment_id.setText("");
    }


    static void addItem(String[] item) {
        table.addRow(item);
        OrdersMain.updateTotal();
    }


    static void removeItem(int index) {
        table.deleteRow(index);
        OrdersMain.updateTotal();
    }


    static void clearTable() {
        table.updateTable(null);
        OrdersMain.updateTotal();
    }


    static int getRowCount() {
        return table.getRowCount();
    }


    static void setValue(int x, int y, String new_value) {
        table.setValueAt(new_value, x, y);
        OrdersMain.updateTotal();
    }


    static String getValue(int x, int y) {
        return String.valueOf(table.getValueAt(x,y));
    }


    static void setTotalLabel(String text) {
        total_price.setText(text);
    }


    static JPanel createPanel() {
        panel.setLayout(null);
        JLabel
            payment_label = new JLabel("Payment ID:"),
            name_label = new JLabel("Customer:"),
            contact_label = new JLabel("Contact:"),
            address_label = new JLabel("Address:");
        JPanel
            top_bar = new JPanel(),
            bottom_bar = new JPanel(),
            ribbon_box = new JPanel();
        JButton
            clear_order_button = new JButton(Main.refreshIcon),
            order_button = new JButton("Submit Order"),
            clear_button = new JButton(Main.refreshIcon),
            add_button = new JButton(Main.addIcon),
            back_button = new JButton("<");
        JScrollPane 
            address_pane = new JScrollPane(address_field),
            table_pane = new JScrollPane(table)
        ;

        panel.setBackground(Main.getDarkColor());
        top_bar.setBackground(Main.getLightColor());
        bottom_bar.setBackground(Main.getLightColor());
        ribbon_box.setBackground(Main.getLightColor());
        order_button.setBackground(Main.getMidColor());
        order_button.setForeground(Color.WHITE);
        clear_order_button.setBackground(Main.getMidColor());
        clear_button.setBackground(Main.getMidColor());
        add_button.setBackground(Main.getMidColor());
        add_button.setForeground(Color.WHITE);
        back_button.setBackground(Main.getMidColor());
        back_button.setForeground(Color.WHITE);
        total_price.setForeground(Color.BLACK);
        payment_label.setForeground(Color.WHITE);
        name_label.setForeground(Color.WHITE);
        contact_label.setForeground(Color.WHITE);
        address_label.setForeground(Color.WHITE);

        clear_order_button.setBounds(509,84,40,40);
        clear_button.setBounds(22,548,40,40);
        payment_label.setBounds(23,124,215,27);
        name_label.setBounds(23,210,215,27);
        contact_label.setBounds(23,296,215,27);
        address_label.setBounds(23,382,215,27);
        address_pane.setBounds(23,414,395,92);
        contact_field.setBounds(23,328,205,38);
        customer_name_field.setBounds(23,242,395,38);
        payment_id.setBounds(23,156,275,38);
        total_price.setBounds(652,97,200,27);
        back_button.setBounds(0,0,45,30);
        top_bar.setBounds(0,0,880,30);
        table_pane.setBounds(457,124,395,506);
        bottom_bar.setBounds(0,630,880,30);
        ribbon_box.setBounds(440,0,440,645);
        add_button.setBounds(457,84,40,40);
        order_button.setBounds(688,28,164,40);

        payment_label.setFont(Main.getFont(20));
        name_label.setFont(Main.getFont(20));
        contact_label.setFont(Main.getFont(20));
        address_label.setFont(Main.getFont(20));
        address_field.setFont(Main.getFont(19));
        order_button.setFont(Main.getFont(15));
        total_price.setFont(Main.getFont(20));
        contact_field.setFont(Main.getFont(19));
        customer_name_field.setFont(Main.getFont(19));
        payment_id.setFont(Main.getFont(19));

        address_field.setLineWrap(true);
        address_field.setWrapStyleWord(true);
        
        panel.add(clear_order_button);
        panel.add(payment_label);
        panel.add(name_label);
        panel.add(contact_label);
        panel.add(address_label);
        panel.add(address_pane);
        panel.add(payment_id);
        panel.add(total_price);
        panel.add(order_button);
        panel.add(clear_button);
        panel.add(add_button);
        panel.add(back_button);
        panel.add(customer_name_field);
        panel.add(contact_field);
        panel.add(table_pane);
        panel.add(ribbon_box);
        panel.add(bottom_bar);
        panel.add(top_bar);

        clear_order_button.addActionListener(e -> clearTable());
        clear_button.addActionListener(e -> clearInputs());
        back_button.addActionListener(e -> OrdersMain.gotoOrders());
        add_button.addActionListener(e -> OrdersMain.gotoOrderItemSelect());
        order_button.addActionListener(e -> OrdersMain.submitOrder());

        return panel;
    }
}
