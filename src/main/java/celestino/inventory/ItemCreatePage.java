package celestino.inventory;

import main.Main;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ItemCreatePage {

    private static final JTextField
        name_field = new JTextField(),
        barcode_field = new JTextField(),
        location_field = new JTextField(),
        stock_field = new JTextField(),
        type_field = new JTextField(),
        price_field = new JTextField();
    private static final JTextArea desc_field = new JTextArea();
    private static final JPanel panel = new JPanel();


    static void setBarcodeInput(String barcode) {
        barcode_field.setText(barcode);
    } 

    static String[] getNewItemInput() {
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


    static void clearNewItemFields() {
        barcode_field.setText("");
        name_field.setText("");
        type_field.setText("");
        desc_field.setText("");
        location_field.setText("");
        stock_field.setText("");
        price_field.setText("");
    }   


    static JPanel createPanel() {
        panel.setLayout(null);
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
            back_button = new JButton("<"),
            add_button = new JButton("Create"),
            clear_button = new JButton(new ImageIcon("src/main/resources/refresh.png"));
        JScrollPane desc_pane = new JScrollPane(desc_field);

        panel.setBackground(Main.getDarkColor());
        top_bar.setBackground(Main.getLightColor());
        bottom_bar.setBackground(Main.getLightColor());
        ribbon_bar.setBackground(Main.getLightColor());
        name_label.setForeground(Color.BLACK);
        barcode_label.setForeground(Color.BLACK);
        location_label.setForeground(Color.BLACK);
        stock_label.setForeground(Color.BLACK);
        type_label.setForeground(Color.BLACK);
        desc_label.setForeground(Color.BLACK);
        price_label.setForeground(Color.BLACK);
        back_button.setBackground(Main.getMidColor());
        back_button.setForeground(Color.WHITE);
        add_button.setBackground(Main.getMidColor());
        add_button.setForeground(Color.WHITE);
        clear_button.setBackground(Main.getMidColor());

        price_label.setFont(Main.getFont(16));
        name_label.setFont(Main.getFont(16));
        barcode_label.setFont(Main.getFont(16));
        location_label.setFont(Main.getFont(16));
        stock_label.setFont(Main.getFont(16));
        type_label.setFont(Main.getFont(16));
        desc_label.setFont(Main.getFont(16));
        add_button.setFont(Main.getFont(18));
        price_field.setFont(Main.getFont(16));
        name_field.setFont(Main.getFont(16));
        barcode_field.setFont(Main.getFont(16));
        location_field.setFont(Main.getFont(16));
        stock_field.setFont(Main.getFont(16));
        type_field.setFont(Main.getFont(16));
        desc_field.setFont(Main.getFont(16));
        
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
        ribbon_bar.setBounds(42,166,797,328);
        back_button.setBounds(0,0,45,30);
        clear_button.setBounds(65,430,40,40);
        add_button.setBounds(400,430,92,40);

        desc_field.setLineWrap(true);
        desc_field.setWrapStyleWord(true);

        panel.add(price_field);
        panel.add(name_field);
        panel.add(barcode_field);
        panel.add(location_field);
        panel.add(stock_field);
        panel.add(type_field);
        panel.add(price_label);
        panel.add(name_label);
        panel.add(barcode_label);
        panel.add(location_label);
        panel.add(stock_label);
        panel.add(type_label);
        panel.add(desc_label);
        panel.add(desc_pane);
        panel.add(back_button);
        panel.add(clear_button);
        panel.add(add_button);
        panel.add(top_bar);
        panel.add(bottom_bar);
        panel.add(ribbon_bar);

        add_button.addActionListener(e -> InventoryMain.createItem());
        back_button.addActionListener(e -> InventoryMain.gotoInventory());
        clear_button.addActionListener(e -> clearNewItemFields());

        return panel;
    }
}