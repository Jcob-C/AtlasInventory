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
    private static final PresetJTable table = new PresetJTable(columns, OrdersMain::select_order_item); 


    static void add_item(String[] item) {
        table.add_row(item);
    }


    static void remove_item(int index) {
        table.delete_row(index);
    }


    static void clear_table() {
        table.update_table(null);
    }


    static int get_row_count() {
        return table.getRowCount();
    }


    static void set_value(int x, int y, String new_value) {
        table.setValueAt(new_value, x, y);
    }


    static String get_value(int x, int y) {
        return String.valueOf(table.getValueAt(x,y));
    }


    static JPanel create_panel() {
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

        panel.setBackground(Main.get_dark_color());
        top_bar.setBackground(Main.get_light_color());
        bottom_bar.setBackground(Main.get_light_color());
        ribbon_box.setBackground(Main.get_light_color());
        order_button.setBackground(Main.get_mid_color());
        order_button.setForeground(Color.WHITE);
        clear_button.setBackground(Main.get_mid_color());
        add_button.setBackground(Main.get_mid_color());
        add_button.setForeground(Color.WHITE);
        back_button.setBackground(Main.get_mid_color());
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

        back_button.addActionListener(e -> OrdersMain.goto_orders());
        add_button.addActionListener(e -> OrdersMain.goto_order_item_select());
        order_button.addActionListener(e -> OrdersMain.submit_order());

        return panel;
    }
}
