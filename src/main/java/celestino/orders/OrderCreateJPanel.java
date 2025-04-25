package celestino.orders;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import celestino.Main;
import celestino.PresetJTable;

public class OrderCreateJPanel extends JPanel {

    public OrderCreateJPanel(String[] columns, OrdersMain parent) {
        setLayout(null);

        PresetJTable table = new PresetJTable(columns, parent::select_new_order_item);
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

        setBackground(Main.get_dark_color());
        top_bar.setBackground(Main.get_mid_color());
        bottom_bar.setBackground(Main.get_mid_color());
        ribbon_box.setBackground(Main.get_mid_color());
        order_button.setBackground(Main.get_dark_color());
        clear_button.setBackground(Main.get_dark_color());
        add_button.setBackground(Main.get_dark_color());
        back_button.setBackground(Main.get_dark_color());
        back_button.setForeground(Main.get_light_color());

        back_button.setBounds(0,0,45,30);
        top_bar.setBounds(0,0,880,30);
        table_pane.setBounds(35,47,467,565);
        bottom_bar.setBounds(0,630,880,30);
        ribbon_box.setBounds(524,47,334,565);
        add_button.setBounds(541,66,113,39);
        order_button.setBounds(645,507,92,39);

        add(order_button);
        add(clear_button);
        add(add_button);
        add(back_button);
        add(customer_name_field);
        add(address_field);
        add(contact_field);
        add(table_pane);
        add(ribbon_box);
        add(bottom_bar);
        add(top_bar);

        back_button.addActionListener(e -> parent.goto_orders());
    }
}
