package celestino.inventory.ui;

import celestino.Main;
import celestino.inventory.InventoryMain;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ItemCreatePanel extends JPanel {

    private final JTextField
        name_field = new JTextField(),
        barcode_field = new JTextField(),
        location_field = new JTextField(),
        stock_field = new JTextField(),
        type_field = new JTextField(),
        price_field = new JTextField();
    private final JTextArea desc_field = new JTextArea();
    

    public String[] get_new_item_inputs() {
        String new_item_inputs[] = new String[7];
        new_item_inputs[0] = barcode_field.getText();
        new_item_inputs[1] = name_field.getText();
        new_item_inputs[2] = type_field.getText();
        new_item_inputs[3] = desc_field.getText();
        new_item_inputs[4] = location_field.getText();
        new_item_inputs[5] = price_field.getText();
        new_item_inputs[6] = stock_field.getText();
        return new_item_inputs; 
    }


    public void clear_new_item_fields() {
        barcode_field.setText("");
        name_field.setText("");
        type_field.setText("");
        desc_field.setText("");
        location_field.setText("");
        stock_field.setText("");
        price_field.setText("");
    }   


    public ItemCreatePanel(InventoryMain parent) {
        setLayout(null);
        JPanel 
            top_bar = new JPanel(),
            bottom_bar = new JPanel(),
            ribbon_bar = new JPanel();
        JLabel
            name_label = new JLabel("Name:"),
            barcode_label = new JLabel("Barcode:"),
            location_label = new JLabel("Location:"),
            stock_label = new JLabel("Stock:"),
            type_label = new JLabel("Type:"),
            desc_label = new JLabel("Description:"),
            price_label = new JLabel("Price:");
        JButton 
            inventory_button = new JButton("<"),
            add_button = new JButton("Create"),
            clear_button = new JButton("Clear");
        JScrollPane desc_pane = new JScrollPane(desc_field);

        setBackground(Main.get_dark_color());
        top_bar.setBackground(Main.get_mid_color());
        bottom_bar.setBackground(Main.get_mid_color());
        ribbon_bar.setBackground(Main.get_mid_color());

        name_label.setForeground(Main.get_dark_color());
        barcode_label.setForeground(Main.get_dark_color());
        location_label.setForeground(Main.get_dark_color());
        stock_label.setForeground(Main.get_dark_color());
        type_label.setForeground(Main.get_dark_color());
        desc_label.setForeground(Main.get_dark_color());
        price_label.setForeground(Main.get_dark_color());

        inventory_button.setBackground(Main.get_dark_color());
        inventory_button.setForeground(Main.get_light_color());
        add_button.setBackground(Main.get_dark_color());
        add_button.setForeground(Main.get_light_color());
        clear_button.setBackground(Main.get_dark_color());
        clear_button.setForeground(Main.get_light_color());

        price_label.setFont(Main.get_font(16));
        name_label.setFont(Main.get_font(16));
        barcode_label.setFont(Main.get_font(16));
        location_label.setFont(Main.get_font(16));
        stock_label.setFont(Main.get_font(16));
        type_label.setFont(Main.get_font(16));
        desc_label.setFont(Main.get_font(16));
        add_button.setFont(Main.get_font(18));
        clear_button.setFont(Main.get_font(15));
        price_field.setFont(Main.get_font(16));
        name_field.setFont(Main.get_font(16));
        barcode_field.setFont(Main.get_font(16));
        location_field.setFont(Main.get_font(16));
        stock_field.setFont(Main.get_font(16));
        type_field.setFont(Main.get_font(16));
        desc_field.setFont(Main.get_font(16));
        
        name_label.setBounds(84,230,71,21);
        barcode_label.setBounds(63,278,93,21);
        location_label.setBounds(63,326,92,21);
        stock_label.setBounds(84,375,71,21);
        type_label.setBounds(524,230,71,21);
        desc_label.setBounds(415,278,125,21);
        price_label.setBounds(559,375,71,21);
        price_field.setBounds(625,369,136,32);
        name_field.setBounds(155,224,338,32);
        barcode_field.setBounds(155,272,188,32);
        location_field.setBounds(155,321,211,32);
        stock_field.setBounds(155,369,136,32);
        type_field.setBounds(585,224,230,32);
        desc_pane.setBounds(540,272,257,81);
        top_bar.setBounds(0,0,880,30);
        bottom_bar.setBounds(0,630,880,30);
        ribbon_bar.setBounds(42,191,797,278);
        inventory_button.setBounds(0,0,45,30);
        clear_button.setBounds(50,420,70,40);
        add_button.setBounds(399,401,92,40);

        desc_field.setLineWrap(true);
        desc_field.setWrapStyleWord(true);

        add(price_field);
        add(name_field);
        add(barcode_field);
        add(location_field);
        add(stock_field);
        add(type_field);
        add(price_label);
        add(name_label);
        add(barcode_label);
        add(location_label);
        add(stock_label);
        add(type_label);
        add(desc_label);
        add(desc_pane);
        add(inventory_button);
        add(clear_button);
        add(add_button);
        add(top_bar);
        add(bottom_bar);
        add(ribbon_bar);

        add_button.addActionListener(e -> parent.create_button());
        inventory_button.addActionListener(e -> parent.goto_inventory());
        clear_button.addActionListener(e -> clear_new_item_fields());
    }
}