package celestino.inventory.ui;

import celestino.Main;
import celestino.inventory.InventoryMain;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AddItemPanel extends JPanel {

    public String[] get_new_item_inputs() {
        String new_item_inputs[] = new String[6];
        new_item_inputs[0] = barcode_field.getText();
        new_item_inputs[1] = name_field.getText();
        new_item_inputs[2] = type_field.getText();
        new_item_inputs[3] = desc_field.getText();
        new_item_inputs[4] = location_field.getText();
        new_item_inputs[5] = stock_field.getText();
        return new_item_inputs;
    }


    public void clear_new_item_inputs() {
        barcode_field.setText("");
        name_field.setText("");
        type_field.setText("");
        desc_field.setText("");
        location_field.setText("");
        stock_field.setText("");
    }   


    public AddItemPanel(InventoryMain parent) {
        setLayout(null);

        JPanel 
            top_bar = new JPanel(),
            bottom_bar = new JPanel(),
            ribbon_bar = new JPanel()
        ;
        JLabel
            name_label = new JLabel("Name:"),
            barcode_label = new JLabel("Barcode:"),
            location_label = new JLabel("Location:"),
            stock_label = new JLabel("Stock:"),
            type_label = new JLabel("Type:"),
            desc_label = new JLabel("Description:")
        ;
        JButton 
            inventory_button = new JButton("<"),
            add_button = new JButton("Create")
        ;

        setBackground(Main.theme[2]);
        top_bar.setBackground(Main.theme[1]);
        bottom_bar.setBackground(Main.theme[1]);
        ribbon_bar.setBackground(Main.theme[1]);
        name_label.setForeground(Main.theme[0]);
        barcode_label.setForeground(Main.theme[0]);
        location_label.setForeground(Main.theme[0]);
        stock_label.setForeground(Main.theme[0]);
        type_label.setForeground(Main.theme[0]);
        desc_label.setForeground(Main.theme[0]);
        inventory_button.setBackground(Main.theme[2]);
        inventory_button.setForeground(Main.theme[0]);
        add_button.setBackground(Main.theme[2]);
        add_button.setForeground(Main.theme[0]);

        name_label.setFont(Main.get_font(16));
        barcode_label.setFont(Main.get_font(16));
        location_label.setFont(Main.get_font(16));
        stock_label.setFont(Main.get_font(16));
        type_label.setFont(Main.get_font(16));
        desc_label.setFont(Main.get_font(16));
        add_button.setFont(Main.get_font(18));

        name_label.setBounds(84,230,71,21);
        barcode_label.setBounds(63,278,93,21);
        location_label.setBounds(63,326,92,21);
        stock_label.setBounds(84,375,71,21);
        type_label.setBounds(524,230,71,21);
        desc_label.setBounds(415,278,125,21);
        name_field.setBounds(155,224,338,32);
        barcode_field.setBounds(155,272,188,32);
        location_field.setBounds(155,321,211,32);
        stock_field.setBounds(155,369,136,32);
        type_field.setBounds(585,224,230,32);
        desc_pane.setBounds(540,272,257,129);
        top_bar.setBounds(0,0,880,30);
        bottom_bar.setBounds(0,630,880,30);
        ribbon_bar.setBounds(42,191,797,278);
        inventory_button.setBounds(0,0,45,30);
        add_button.setBounds(350,401,92,40);

        name_field.setFont(Main.get_font(16));
        barcode_field.setFont(Main.get_font(16));
        location_field.setFont(Main.get_font(16));
        stock_field.setFont(Main.get_font(16));
        type_field.setFont(Main.get_font(16));
        desc_field.setFont(Main.get_font(16));

        desc_field.setLineWrap(true);
        desc_field.setWrapStyleWord(true);

        add(name_label);
        add(barcode_label);
        add(location_label);
        add(stock_label);
        add(type_label);
        add(desc_label);
        add(desc_pane);
        add(name_field);
        add(barcode_field);
        add(location_field);
        add(stock_field);
        add(type_field);
        add(inventory_button);
        add(add_button);
        add(top_bar);
        add(bottom_bar);
        add(ribbon_bar);

        add_button.addActionListener(e -> parent.call_action("create item"));
        inventory_button.addActionListener(e -> parent.call_action("inventory"));
    }
    
    
    private final JTextField
        name_field = new JTextField(),
        barcode_field = new JTextField(),
        location_field = new JTextField(),
        stock_field = new JTextField(),
        type_field = new JTextField()  
    ;
    private final JTextArea desc_field = new JTextArea();
    private final JScrollPane desc_pane = new JScrollPane(desc_field);
}