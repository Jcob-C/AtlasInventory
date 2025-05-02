package celestino.orders;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.Color;
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
        order_status = new JTextField(),
        customer_name = new JTextField(),
        customer_no = new JTextField(),
        customer_addr = new JTextField(),
        transac_id = new JTextField()
    ;


    static void set_table(ArrayList<ArrayList<String>> data) {
        table.updateTable(data);
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

        panel.setBackground(Main.getDarkColor());
        top_bar.setBackground(Main.getLightColor());
        bottom_bar.setBackground(Main.getLightColor());
        back_button.setBackground(Main.getMidColor());
        back_button.setForeground(Color.WHITE);
        order_id.setBackground(Main.getLightColor());
        order_id.setForeground(Color.BLACK);
        order_date.setBackground(Main.getLightColor());
        order_date.setForeground(Color.BLACK);
        order_price.setBackground(Main.getLightColor());
        order_price.setForeground(Color.BLACK);
        order_status.setBackground(Main.getLightColor());
        order_status.setForeground(Color.BLACK);
        customer_name.setBackground(Main.getLightColor());
        customer_name.setForeground(Color.BLACK);
        customer_no.setBackground(Main.getLightColor());
        customer_no.setForeground(Color.BLACK);
        customer_addr.setBackground(Main.getLightColor());
        customer_addr.setForeground(Color.BLACK);
        transac_id.setBackground(Main.getLightColor());
        transac_id.setForeground(Color.BLACK);

        back_button.setBounds(0,0,45,30);
        top_bar.setBounds(0,0,880,30);
        table_pane.setBounds(324,28,520,537);
        bottom_bar.setBounds(0,630,880,30);
        order_id.setBounds(24,281,95,31);
        order_date.setBounds(24,318,230,31);  
        order_price.setBounds(24,392,137,31);
        order_status.setBounds(24,355,218,31);
        customer_name.setBounds(24,504,287,31);
        customer_no.setBounds(24,541,170,31);
        customer_addr.setBounds(24,578,600,31);
        transac_id.setBounds(24,430,250,31);

        order_id.setFont(Main.getFont(14));
        order_date.setFont(Main.getFont(14));
        order_price.setFont(Main.getFont(14));
        order_status.setFont(Main.getFont(14));
        customer_name.setFont(Main.getFont(14));
        customer_addr.setFont(Main.getFont(14));
        customer_no.setFont(Main.getFont(14));
        transac_id.setFont(Main.getFont(14));

        order_id.setEditable(false);
        order_date.setEditable(false);
        order_price.setEditable(false);
        order_status.setEditable(false);
        customer_name.setEditable(false);
        customer_addr.setEditable(false);
        customer_no.setEditable(false);
        transac_id.setEditable(false);

        order_id.setCaretColor(Main.getLightColor());
        order_date.setCaretColor(Main.getLightColor());
        order_price.setCaretColor(Main.getLightColor());
        order_status.setCaretColor(Main.getLightColor());
        customer_name.setCaretColor(Main.getLightColor());
        customer_addr.setCaretColor(Main.getLightColor());
        customer_no.setCaretColor(Main.getLightColor());
        transac_id.setCaretColor(Main.getLightColor());
        
        order_id.setText("Order: 100");
        order_price.setText("Price: 1000000");
        order_status.setText("Status: Pending Payment");
        order_date.setText("Date&Time: 0000-00-00 00:00:00");
        customer_name.setText("Customer: Celestino, Ralph Jacob Capili");
        customer_no.setText("Contact: 09123456789");
        customer_addr.setText("Address: 1396, Purok 5, A. Luna Street, Bambang, Bocaue, Bulacan");
        transac_id.setText("Transaction ID: 123456789012345");

        panel.add(transac_id);
        panel.add(customer_addr);
        panel.add(customer_no);
        panel.add(customer_name);
        panel.add(order_status);
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
