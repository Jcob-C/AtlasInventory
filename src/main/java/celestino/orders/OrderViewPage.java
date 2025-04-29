package celestino.orders;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.util.ArrayList;

import celestino.PresetJTable;
import main.Main;

public class OrderViewPage {

    private static final JPanel panel = new JPanel();
    private static final String[] columns = {"ID","Name","Type","Price","Quantity"};
    private static final PresetJTable table = new PresetJTable(columns, OrdersMain::select_order_item); 
    private static final JTextField 
        order_id = new JTextField(),
        order_date = new JTextField(),
        order_price = new JTextField(),
        customer_name = new JTextField(),
        customer_no = new JTextField(),
        customer_addr = new JTextField()
    ;


    static void set_table(ArrayList<ArrayList<String>> data) {
        table.update_table(data);
    }


    static void set_order_info(ArrayList<ArrayList<String>> data) {
        
    }


    static JPanel create_panel() {
        panel.setLayout(null);
        JPanel
            top_bar = new JPanel(),
            bottom_bar = new JPanel();
        JButton back_button = new JButton("<");
        JScrollPane table_pane = new JScrollPane(table);

        panel.setBackground(Main.get_dark_color());
        top_bar.setBackground(Main.get_mid_color());
        bottom_bar.setBackground(Main.get_mid_color());
        back_button.setBackground(Main.get_dark_color());
        back_button.setForeground(Main.get_light_color());
        order_id.setBackground(Main.get_mid_color());
        order_id.setForeground(Main.get_dark_color());
        order_date.setBackground(Main.get_mid_color());
        order_date.setForeground(Main.get_dark_color());
        order_price.setBackground(Main.get_mid_color());
        order_price.setForeground(Main.get_dark_color());
        customer_name.setBackground(Main.get_mid_color());
        customer_name.setForeground(Main.get_dark_color());
        customer_no.setBackground(Main.get_mid_color());
        customer_no.setForeground(Main.get_dark_color());
        customer_addr.setBackground(Main.get_mid_color());
        customer_addr.setForeground(Main.get_dark_color());

        back_button.setBounds(0,0,45,30);
        top_bar.setBounds(0,0,880,30);
        table_pane.setBounds(183,155,514,475);
        bottom_bar.setBounds(0,630,880,30);
        order_id.setBounds(105,39,199,28);
        order_date.setBounds(313,39,227,28);  
        order_price.setBounds(549,39,226,28);
        customer_name.setBounds(105,78,475,28);
        customer_no.setBounds(587,78,188,28);
        customer_addr.setBounds(105,116,671,28);

        order_id.setFont(Main.get_font(21));
        order_date.setFont(Main.get_font(21));
        order_price.setFont(Main.get_font(21));
        customer_name.setFont(Main.get_font(21));
        customer_addr.setFont(Main.get_font(21));
        customer_no.setFont(Main.get_font(21));

        order_id.setEditable(false);
        order_date.setEditable(false);
        order_price.setEditable(false);
        customer_name.setEditable(false);
        customer_addr.setEditable(false);
        customer_no.setEditable(false);

        order_id.setCaretColor(Main.get_mid_color());
        order_date.setCaretColor(Main.get_mid_color());
        order_price.setCaretColor(Main.get_mid_color());
        customer_name.setCaretColor(Main.get_mid_color());
        customer_addr.setCaretColor(Main.get_mid_color());
        customer_no.setCaretColor(Main.get_mid_color());

        panel.add(customer_addr);
        panel.add(customer_no);
        panel.add(customer_name);
        panel.add(order_price);
        panel.add(order_date);
        panel.add(order_id);
        panel.add(back_button);
        panel.add(table_pane);
        panel.add(bottom_bar);
        panel.add(top_bar);

        back_button.addActionListener(e -> OrdersMain.goto_orders());

        return panel;
    }
}
