package celestino.orders;

import celestino.PresetJTable;
import main.Main;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.util.ArrayList;

public class OrderViewPage {

    private static final JPanel panel = new JPanel();
    private static final String[] columns = {"ID","Name","Type","Price","Quantity"};
    private static final PresetJTable table = new PresetJTable(columns, OrdersMain::selectOrderItem); 
    private static final JTextField
        customer_name = new JTextField(),
        customer_no = new JTextField(),
        transac_id = new JTextField();
    private static final JLabel
        name_label = new JLabel("Customer's Name:"),
        transac_id_label = new JLabel("Transaction ID:"),
        no_label = new JLabel("Contact:"),
        addr_label = new JLabel("Address:"),
        order_id = new JLabel(),
        order_date = new JLabel(),
        order_status = new JLabel(),      
        order_price = new JLabel();
    private static final JTextArea customer_addr = new JTextArea();


    static void setTable(ArrayList<ArrayList<String>> data) {
        table.updateTable(data);
    }


    static void setOrderInfo(ArrayList<ArrayList<String>> data) {
        order_id.setText("Order ID: " + data.get(0).get(0));
        order_date.setText("Order Date: " + data.get(0).get(1));
        customer_name.setText("" + data.get(0).get(2));
        customer_no.setText("" + data.get(0).get(3));
        customer_addr.setText("" + data.get(0).get(4));
        order_status.setText("Status: " + data.get(0).get(5));
        transac_id.setText("" + data.get(0).get(6));
        order_price.setText("Total Price: " + data.get(0).get(7));
    }


    static String getViewedID() {
        return order_id.getText();
    }


    static String[] getCustomerInfoFields() {
        return new String[] {
            customer_name.getText(),
            customer_no.getText(),
            customer_addr.getText(),
            transac_id.getText()
        };
    }


    static JPanel createPanel() {
        panel.setLayout(null);
        JPanel
            top_bar = new JPanel(),
            bottom_bar = new JPanel(),
            ribbon = new JPanel();
        JButton back_button = new JButton("<");
        JScrollPane 
            addr_pane = new JScrollPane(customer_addr),
            table_pane = new JScrollPane(table)
        ;

        panel.setBackground(Main.getDarkColor());
        top_bar.setBackground(Main.getLightColor());
        bottom_bar.setBackground(Main.getLightColor());
        back_button.setBackground(Main.getMidColor());
        back_button.setForeground(Color.WHITE);
        order_id.setForeground(Color.BLACK);
        order_date.setForeground(Color.BLACK);
        order_price.setForeground(Color.BLACK);
        order_status.setForeground(Color.BLACK);
        ribbon.setBackground(Main.getLightColor());
        no_label.setForeground(Color.WHITE);
        transac_id_label.setForeground(Color.WHITE);
        name_label.setForeground(Color.WHITE);
        addr_label.setForeground(Color.WHITE);

        ribbon.setBounds(0,39,880,96);
        back_button.setBounds(0,0,45,30);
        top_bar.setBounds(0,0,880,30);
        table_pane.setBounds(457,135,395,495);
        bottom_bar.setBounds(0,630,880,30);
        order_id.setBounds(45,40,291,48);
        order_date.setBounds(45,98,437,27);  
        order_price.setBounds(457,98,400,27);
        order_status.setBounds(457,56,357,27);
        customer_name.setBounds(45,294,395,38);
        customer_no.setBounds(45,401,205,38);
        transac_id.setBounds(45,187,275,38);
        addr_pane.setBounds(45,509,395,92);
        no_label.setBounds(45,370,409,27);
        transac_id_label.setBounds(45,155,409,27);
        name_label.setBounds(45,262,409,27);
        addr_label.setBounds(45,477,409,27);

        order_id.setFont(Main.getFont(40));
        order_date.setFont(Main.getFont(21));
        order_price.setFont(Main.getFont(21));
        order_status.setFont(Main.getFont(21));
        customer_name.setFont(Main.getFont(20));
        customer_addr.setFont(Main.getFont(20));
        customer_no.setFont(Main.getFont(20));
        transac_id.setFont(Main.getFont(20));
        no_label.setFont(Main.getFont(21));
        transac_id_label.setFont(Main.getFont(21));
        name_label.setFont(Main.getFont(21));
        addr_label.setFont(Main.getFont(21));
        
        order_id.setText("Order: 1000");
        order_price.setText("Price: 1000000");
        order_status.setText("Status: Pending Payment");
        order_date.setText("Date&Time: 0000-00-00 00:00:00");
        customer_name.setText("Celestino, Ralph Jacob Capili");
        customer_no.setText("09123456789");
        customer_addr.setText("1396, Purok 5, A. Luna Street, Bambang, Bocaue, Bulacan");
        transac_id.setText("123456789012345");

        customer_addr.setLineWrap(true);
        customer_addr.setWrapStyleWord(true);

        panel.add(transac_id);
        panel.add(addr_pane);
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
        panel.add(ribbon);
        panel.add(transac_id_label);
        panel.add(name_label);
        panel.add(no_label);
        panel.add(addr_label);


        back_button.addActionListener(e -> OrdersMain.updateViewedOrder());

        return panel;
    }
}
