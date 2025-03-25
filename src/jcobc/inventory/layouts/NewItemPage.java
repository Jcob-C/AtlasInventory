package jcobc.inventory.layouts;
import jcobc.inventory.Inventory;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class NewItemPage extends JPanel {

    private final Color darkcolor = Color.BLACK;
    private final Color lightcolor = Color.WHITE;
    
    private JTextField tfBarcode, tfName, tfLocation, tfType, tfDescription, tfPrice, tfStock;
    

    public String[] getNewItemInput() {
        String newItem[] = new String[7];
        newItem[0] = tfBarcode.getText();
        newItem[1] = tfName.getText();
        newItem[2] = tfLocation.getText();
        newItem[3] = tfType.getText();
        newItem[4] = tfDescription.getText();
        newItem[5] = tfPrice.getText();
        newItem[6] = tfStock.getText();
        return newItem;
    }

    
    public void clearNewItemInput() {
        tfBarcode.setText(null);
        tfName.setText(null);
        tfLocation.setText(null);
        tfType.setText(null);
        tfDescription.setText(null);
        tfPrice.setText(null);
        tfStock.setText(null);
    }


    public NewItemPage() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);
        
        int labelHeight = 20;
        int fieldHeight = 30;
        int verticalGap = 15;
        int leftX = 50;
        int rightX = 310;
        int startY = 50;
        int fieldWidth = 240;
        
        JLabel lblBarcode = new JLabel("Barcode:");
        lblBarcode.setForeground(lightcolor);
        lblBarcode.setBounds(leftX, startY, fieldWidth, labelHeight);
        add(lblBarcode);
        
        tfBarcode = new JTextField();
        tfBarcode.setBounds(leftX, startY + labelHeight, fieldWidth, fieldHeight);
        tfBarcode.setBackground(darkcolor);
        tfBarcode.setForeground(lightcolor);
        tfBarcode.setBorder(new LineBorder(lightcolor, 2));
        add(tfBarcode);
        
        JLabel lblName = new JLabel("Name:");
        lblName.setForeground(lightcolor);
        lblName.setBounds(leftX, startY + labelHeight + fieldHeight + verticalGap, fieldWidth, labelHeight);
        add(lblName);
        
        tfName = new JTextField();
        tfName.setBounds(leftX, startY + labelHeight + fieldHeight + verticalGap + labelHeight, fieldWidth, fieldHeight);
        tfName.setBackground(darkcolor);
        tfName.setForeground(lightcolor);
        tfName.setBorder(new LineBorder(lightcolor, 2));
        add(tfName);
        
        JLabel lblLocation = new JLabel("Location:");
        lblLocation.setForeground(lightcolor);
        lblLocation.setBounds(leftX, startY + 2 * (labelHeight + fieldHeight + verticalGap), fieldWidth, labelHeight);
        add(lblLocation);
        
        tfLocation = new JTextField();
        tfLocation.setBounds(leftX, startY + 2 * (labelHeight + fieldHeight + verticalGap) + labelHeight, fieldWidth, fieldHeight);
        tfLocation.setBackground(darkcolor);
        tfLocation.setForeground(lightcolor);
        tfLocation.setBorder(new LineBorder(lightcolor, 2));
        add(tfLocation);
        
        JLabel lblType = new JLabel("Type:");
        lblType.setForeground(lightcolor);
        lblType.setBounds(rightX, startY, fieldWidth, labelHeight);
        add(lblType);
        
        tfType = new JTextField();
        tfType.setBounds(rightX, startY + labelHeight, fieldWidth, fieldHeight);
        tfType.setBackground(darkcolor);
        tfType.setForeground(lightcolor);
        tfType.setBorder(new LineBorder(lightcolor, 2));
        add(tfType);
        
        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setForeground(lightcolor);
        lblDescription.setBounds(rightX, startY + labelHeight + fieldHeight + verticalGap, fieldWidth, labelHeight);
        add(lblDescription);
        
        tfDescription = new JTextField();
        tfDescription.setBounds(rightX, startY + labelHeight + fieldHeight + verticalGap + labelHeight, fieldWidth, fieldHeight);
        tfDescription.setBackground(darkcolor);
        tfDescription.setForeground(lightcolor);
        tfDescription.setBorder(new LineBorder(lightcolor, 2));
        add(tfDescription);
        
        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setForeground(lightcolor);
        lblPrice.setBounds(rightX, startY + 2 * (labelHeight + fieldHeight + verticalGap), fieldWidth, labelHeight);
        add(lblPrice);
        
        tfPrice = new JTextField();
        tfPrice.setBounds(rightX, startY + 2 * (labelHeight + fieldHeight + verticalGap) + labelHeight, fieldWidth, fieldHeight);
        tfPrice.setBackground(darkcolor);
        tfPrice.setForeground(lightcolor);
        tfPrice.setBorder(new LineBorder(lightcolor, 2));
        add(tfPrice);
        
        JLabel lblStock = new JLabel("Stock:");
        lblStock.setForeground(lightcolor);
        lblStock.setBounds(rightX, startY + 2 * (labelHeight + fieldHeight + verticalGap) + fieldHeight + 2 * verticalGap, fieldWidth, labelHeight);
        add(lblStock);
        
        tfStock = new JTextField();
        tfStock.setBounds(rightX, startY + 2 * (labelHeight + fieldHeight + verticalGap) + fieldHeight + 2 * verticalGap + labelHeight, fieldWidth, fieldHeight);
        tfStock.setBackground(darkcolor);
        tfStock.setForeground(lightcolor);
        tfStock.setBorder(new LineBorder(lightcolor, 2));
        add(tfStock);
        
        int buttonsY = startY + 2 * (labelHeight + fieldHeight + verticalGap) + fieldHeight + 2 * verticalGap + labelHeight + fieldHeight + 20;
        JButton cancelButton = new JButton("Back");
        cancelButton.setBackground(lightcolor);
        cancelButton.setForeground(darkcolor);
        cancelButton.setBounds((600 - 80) / 2 - 50, buttonsY, 80, 30);
        cancelButton.addActionListener(_ -> Inventory.callAction("gotoInventory"));
        add(cancelButton);
        
        JButton submitButton = new JButton("Add");
        submitButton.setBackground(lightcolor);
        submitButton.setForeground(darkcolor);
        submitButton.setBounds((600 - 80) / 2 + 50, buttonsY, 80, 30);
        submitButton.addActionListener(_ -> Inventory.callAction("newItem"));
        add(submitButton);
    }
}
