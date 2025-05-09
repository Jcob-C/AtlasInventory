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
import javax.swing.JTextField;

public class OrderCreatePage {

    private static final JPanel panel = new JPanel();
    private static final String[] columns = {"ID","Name","Type","Price","Quantity"};
    private static final PresetJTable table = new PresetJTable(columns, OrdersMain::selectOrderItem); 
    private static final JTextField
        customer_name_field = new JTextField(),
        address_field = new JTextField(),
        contact_field = new JTextField();
    private static final JLabel total_price = new JLabel("Total: 99999999");


    static ArrayList<ArrayList<String>> getTable() {
        return table.getTable();
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
        JPanel
            top_bar = new JPanel(),
            bottom_bar = new JPanel(),
            ribbon_box = new JPanel();
        JButton
            order_button = new JButton("Submit Order"),
            clear_button = new JButton("Clear"),
            add_button = new JButton(new ImageIcon("src/main/resources/add.png")),
            back_button = new JButton("<");
        JScrollPane table_pane = new JScrollPane(table);

        panel.setBackground(Main.getDarkColor());
        top_bar.setBackground(Main.getLightColor());
        bottom_bar.setBackground(Main.getLightColor());
        ribbon_box.setBackground(Main.getLightColor());
        order_button.setBackground(Main.getMidColor());
        order_button.setForeground(Color.WHITE);
        clear_button.setBackground(Main.getMidColor());
        add_button.setBackground(Main.getMidColor());
        add_button.setForeground(Color.WHITE);
        back_button.setBackground(Main.getMidColor());
        back_button.setForeground(Color.WHITE);
        total_price.setForeground(Color.BLACK);

        total_price.setBounds(652,97,200,27);
        back_button.setBounds(0,0,45,30);
        top_bar.setBounds(0,0,880,30);
        table_pane.setBounds(457,124,395,506);
        bottom_bar.setBounds(0,630,880,30);
        ribbon_box.setBounds(440,0,440,645);
        add_button.setBounds(457,84,40,40);
        order_button.setBounds(688,28,164,40);

        order_button.setFont(Main.getFont(15));
        total_price.setFont(Main.getFont(20));

        panel.add(total_price);
        panel.add(order_button);
        panel.add(clear_button);
        panel.add(add_button);
        panel.add(back_button);
        panel.add(customer_name_field);
        panel.add(address_field);
        panel.add(contact_field);
        panel.add(table_pane);
        panel.add(ribbon_box);
        panel.add(bottom_bar);
        panel.add(top_bar);

        back_button.addActionListener(e -> OrdersMain.gotoOrders());
        add_button.addActionListener(e -> OrdersMain.gotoOrderItemSelect());
        order_button.addActionListener(e -> OrdersMain.submitOrder());

        return panel;
    }
}
