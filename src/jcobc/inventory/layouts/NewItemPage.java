package jcobc.inventory.layouts;
import jcobc.inventory.Inventory;
import jcobc.main.Interface;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class NewItemPage extends JPanel {

    private final Color darkcolor = Color.BLACK;
    private final Color lightcolor = Color.WHITE;
    
    private JTextField tfBarcode, tfName, tfLocation, tfType, tfDescription, tfPrice, tfStock;
    

    public String[] newItemInputs() {
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


    private void backButton() {
        Inventory.callAction("gotoInventory");
    }


    private void submitButton() {
        Inventory.callAction("newItem");
    }


    public NewItemPage() {
        setLayout(null);
        setBackground(Interface.mediumColor);
        
        int labelHeight = 20;
        int fieldHeight = 30;
        int verticalGap = 15;
        int leftX = 100;
        int rightX = 450;
        int startY = 50;
        int fieldWidth = 240;
        
        JLabel lblBarcode = new JLabel("Barcode:");
        lblBarcode.setForeground(Interface.lightColor);
        lblBarcode.setBounds(leftX, startY, fieldWidth, labelHeight);
        add(lblBarcode);
        
        tfBarcode = new JTextField();
        tfBarcode.setBounds(leftX, startY + labelHeight, fieldWidth, fieldHeight);
        tfBarcode.setBackground(Interface.lightColor);
        tfBarcode.setForeground(Interface.darkColor);
        tfBarcode.setBorder(new LineBorder(Interface.darkColor, 2));
        add(tfBarcode);
        
        JLabel lblName = new JLabel("Name:");
        lblName.setForeground(Interface.lightColor);
        lblName.setBounds(leftX, startY + labelHeight + fieldHeight + verticalGap, fieldWidth, labelHeight);
        add(lblName);
        
        tfName = new JTextField();
        tfName.setBounds(leftX, startY + labelHeight + fieldHeight + verticalGap + labelHeight, fieldWidth, fieldHeight);
        tfName.setBackground(Interface.lightColor);
        tfName.setForeground(Interface.darkColor);
        tfName.setBorder(new LineBorder(Interface.darkColor, 2));
        add(tfName);
        
        JLabel lblLocation = new JLabel("Location:");
        lblLocation.setForeground(Interface.lightColor);
        lblLocation.setBounds(leftX, startY + 2 * (labelHeight + fieldHeight + verticalGap), fieldWidth, labelHeight);
        add(lblLocation);
        
        tfLocation = new JTextField();
        tfLocation.setBounds(leftX, startY + 2 * (labelHeight + fieldHeight + verticalGap) + labelHeight, fieldWidth, fieldHeight);
        tfLocation.setBackground(Interface.lightColor);
        tfLocation.setForeground(Interface.darkColor);
        tfLocation.setBorder(new LineBorder(Interface.darkColor, 2));
        add(tfLocation);
        
        JLabel lblType = new JLabel("Type:");
        lblType.setForeground(Interface.lightColor);
        lblType.setBounds(rightX, startY, fieldWidth, labelHeight);
        add(lblType);
        
        tfType = new JTextField();
        tfType.setBounds(rightX, startY + labelHeight, fieldWidth, fieldHeight);
        tfType.setBackground(Interface.lightColor);
        tfType.setForeground(Interface.darkColor);
        tfType.setBorder(new LineBorder(Interface.darkColor, 2));
        add(tfType);
        
        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setForeground(Interface.lightColor);
        lblDescription.setBounds(rightX, startY + labelHeight + fieldHeight + verticalGap, fieldWidth, labelHeight);
        add(lblDescription);
        
        tfDescription = new JTextField();
        tfDescription.setBounds(rightX, startY + labelHeight + fieldHeight + verticalGap + labelHeight, fieldWidth, fieldHeight);
        tfDescription.setBackground(Interface.lightColor);
        tfDescription.setForeground(Interface.darkColor);
        tfDescription.setBorder(new LineBorder(Interface.darkColor, 2));
        add(tfDescription);
        
        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setForeground(Interface.lightColor);
        lblPrice.setBounds(rightX, startY + 2 * (labelHeight + fieldHeight + verticalGap), fieldWidth, labelHeight);
        add(lblPrice);
        
        tfPrice = new JTextField();
        tfPrice.setBounds(rightX, startY + 2 * (labelHeight + fieldHeight + verticalGap) + labelHeight, fieldWidth, fieldHeight);
        tfPrice.setBackground(Interface.lightColor);
        tfPrice.setForeground(Interface.darkColor);
        tfPrice.setBorder(new LineBorder(Interface.darkColor, 2));
        add(tfPrice);
        
        JLabel lblStock = new JLabel("Stock:");
        lblStock.setForeground(Interface.lightColor);
        lblStock.setBounds(rightX, startY + 2 * (labelHeight + fieldHeight + verticalGap) + fieldHeight + 2 * verticalGap, fieldWidth, labelHeight);
        add(lblStock);
        
        tfStock = new JTextField();
        tfStock.setBounds(rightX, startY + 2 * (labelHeight + fieldHeight + verticalGap) + fieldHeight + 2 * verticalGap + labelHeight, fieldWidth, fieldHeight);
        tfStock.setBackground(Interface.lightColor);
        tfStock.setForeground(Interface.darkColor);
        tfStock.setBorder(new LineBorder(Interface.darkColor, 2));
        add(tfStock);
        
        int buttonsY = startY + 2 * (labelHeight + fieldHeight + verticalGap) + fieldHeight + 2 * verticalGap + labelHeight + fieldHeight + 20;
        JButton cancelButton = new JButton("Back");
        cancelButton.setBackground(Interface.darkColor);
        cancelButton.setForeground(Interface.lightColor);
        cancelButton.setBounds((600 - 80) / 2 - 50, buttonsY, 80, 30);
        cancelButton.addActionListener(_ -> backButton());
        add(cancelButton);
        
        JButton submitButton = new JButton("Add");
        submitButton.setBackground(Interface.darkColor);
        submitButton.setForeground(Interface.lightColor);
        submitButton.setBounds((600 - 80) / 2 + 50, buttonsY, 80, 30);
        submitButton.addActionListener(_ -> submitButton());
        add(submitButton);
    }
}
