package celestino.orders;

import celestino.PresetJTable;
import main.Main;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class OrderCreatePage {

    private static final JPanel panel = new JPanel();
    private static final String[] columns = {"ID","Name","Type","Price","Quantity"};
    private static final PresetJTable table = new PresetJTable(columns, OrdersMain::selectOrderItem); 


    static void addItem(String[] item) {
        table.addRow(item);
    }


    static void removeItem(int index) {
        table.deleteRow(index);
    }


    static void clearTable() {
        table.updateTable(null);
    }


    static int getRowCount() {
        return table.getRowCount();
    }


    static void setValue(int x, int y, String new_value) {
        table.setValueAt(new_value, x, y);
    }


    static String getValue(int x, int y) {
        return String.valueOf(table.getValueAt(x,y));
    }


    static JPanel createPanel() {
        panel.setLayout(null);
        JPanel
            top_bar = new JPanel(),
            bottom_bar = new JPanel(),
            ribbon_box = new JPanel();
        JButton
            order_button = new JButton("Order"),
            clear_button = new JButton("Clear"),
            add_button = new JButton("Add Item"),
            back_button = new JButton("<");
        JTextField
            customer_name_field = new JTextField(),
            address_field = new JTextField(),
            contact_field = new JTextField();
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

        back_button.setBounds(0,0,45,30);
        top_bar.setBounds(0,0,880,30);
        table_pane.setBounds(35,47,467,565);
        bottom_bar.setBounds(0,630,880,30);
        ribbon_box.setBounds(524,47,334,565);
        add_button.setBounds(541,66,113,39);
        order_button.setBounds(645,507,92,39);

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
