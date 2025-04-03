package jcobc.transact.layouts;
import jcobc.main.Interface;
import jcobc.transact.Transact;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TransactPage extends JPanel {

    private int offsetX = -8;
    private DefaultTableModel customerTableModel, inventoryTableModel;
    private JLabel customerTotalLabel, inventoryTotalLabel;

    private final JButton
        sellButton = new JButton("Sell"), 
        refundButton = new JButton("Refund"), 
        swapButton = new JButton("Swap");
    
    private int selectedRow = -1;
    private String selectedTable = null;

        
    public int selectedRow() {
        return selectedRow;
    }


    public String selectedTable() {
        return selectedTable;
    }


    public void updateTotalPrices(double newCustomerTotal, double newInventoryTotal) {
        customerTotalLabel.setText("Total Price: "+newCustomerTotal);
        inventoryTotalLabel.setText("Total Price: "+newInventoryTotal);
    }


    public void updateTablesDisplay(String[][] inventoryData, String[][] customerData) {
        inventoryTableModel.setRowCount(0);
        customerTableModel.setRowCount(0);
        if (inventoryData != null) {
            for (int i = 0; i < inventoryData.length; i++) {
                inventoryTableModel.addRow(inventoryData[i]);
            }
        }
        if (customerData != null) {
            for (int i = 0; i < customerData.length; i++) {
                customerTableModel.addRow(customerData[i]);
            }
        }
    }


    public void setButtonsVisibility(boolean refund, boolean swap, boolean sell) {
        refundButton.setVisible(refund);
        swapButton.setVisible(swap);
        sellButton.setVisible(sell);
    }


    public TransactPage() {
        setLayout(null);
        setBackground(Interface.mediumColor);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Interface.darkColor);
        backButton.setForeground(Interface.lightColor);
        backButton.setBounds(offsetX + 20, 10, 70, 30);

        JLabel customerLabel = new JLabel("From Customer", SwingConstants.CENTER);
        customerLabel.setForeground(Interface.lightColor);
        customerLabel.setBounds(offsetX + 125, 5, 200, 30);
        
        JButton addCustomerButton = new JButton("Add");
        addCustomerButton.setBackground(Interface.darkColor);
        addCustomerButton.setForeground(Interface.lightColor);
        addCustomerButton.setBounds(offsetX + 290, 10, 70, 30);
        
        String[] columns = {"ID", "Name", "Price", "Condition", "Quantity"};
        customerTableModel = new DefaultTableModel(columns, 0);
        
        JTable customerTable = new JTable(customerTableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerTable.setSelectionBackground(Interface.lightColor);
        customerTable.setSelectionForeground(Interface.darkColor);
        customerTable.setBackground(Interface.darkColor);
        customerTable.setForeground(Interface.lightColor);
        customerTable.setBorder(new LineBorder(Interface.darkColor, 2));
        customerTable.setRowHeight(25);
        customerTable.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane customerScrollPane = new JScrollPane(customerTable);
        customerScrollPane.setBounds(offsetX + 15, 50, 375, 450);
        
        customerTotalLabel = new JLabel("Total Price: 0.00", SwingConstants.CENTER);
        customerTotalLabel.setForeground(Interface.lightColor);
        customerTotalLabel.setBounds(offsetX + 80, 30, 280, 15);
        
        JLabel inventoryLabel = new JLabel("From Inventory", SwingConstants.CENTER);
        inventoryLabel.setForeground(Interface.lightColor);
        inventoryLabel.setBounds(offsetX + 517, 5, 200, 30);
        
        JButton addInventoryButton = new JButton("Add");
        addInventoryButton.setBackground(Interface.darkColor);
        addInventoryButton.setForeground(Interface.lightColor);
        addInventoryButton.setBounds(offsetX + 690, 10, 70, 30);
        
        String[] columns2 = {"ID", "Name", "Price", "Quantity"};
        inventoryTableModel = new DefaultTableModel(columns2, 0);
        
        JTable inventoryTable = new JTable(inventoryTableModel);
        inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventoryTable.setSelectionBackground(Interface.lightColor);
        inventoryTable.setSelectionForeground(Interface.darkColor);
        inventoryTable.setBackground(Interface.darkColor);
        inventoryTable.setForeground(Interface.lightColor);
        inventoryTable.setBorder(new LineBorder(Interface.darkColor, 2));
        inventoryTable.setRowHeight(25);
        inventoryTable.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane inventoryScrollPane = new JScrollPane(inventoryTable);
        inventoryScrollPane.setBounds(offsetX + 410, 50, 375, 450);
        
        inventoryTotalLabel = new JLabel("Total Price: 0.00", SwingConstants.CENTER);
        inventoryTotalLabel.setForeground(Interface.lightColor);
        inventoryTotalLabel.setBounds(offsetX + 475, 30, 280, 15);
        
        sellButton.setBackground(Interface.darkColor);
        refundButton.setBackground(Interface.darkColor);
        swapButton.setBackground(Interface.darkColor);
        sellButton.setForeground(Interface.lightColor);
        refundButton.setForeground(Interface.lightColor);
        swapButton.setForeground(Interface.lightColor);
        sellButton.setBounds(Interface.centerX(130)+300, 510, 130, 40);
        refundButton.setBounds(Interface.centerX(130)-300, 510, 130, 40);
        swapButton.setBounds(Interface.centerX(130), 510, 130, 40);

        backButton.addActionListener(_ -> Transact.callAction("gotoHome"));
        addCustomerButton.addActionListener(_ -> Transact.callAction("addToCustomerList"));
        addInventoryButton.addActionListener(_ -> Transact.callAction("addToInventoryList"));
        sellButton.addActionListener(_ -> Transact.callAction("transact"));
        refundButton.addActionListener(_ -> Transact.callAction("transact"));
        swapButton.addActionListener(_ -> Transact.callAction("transact"));
        customerTable.addMouseListener (
            new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    selectedRow = customerTable.getSelectedRow();
                    selectedTable = "cus";
                    Transact.callAction("listRowSelected");
                }
            }
        );
        inventoryTable.addMouseListener (
            new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    selectedRow = inventoryTable.getSelectedRow();
                    selectedTable = "inv";
                    Transact.callAction("listRowSelected");
                }
            }
        );

        add(inventoryTotalLabel);
        add(inventoryScrollPane);
        add(addInventoryButton);
        add(inventoryLabel);
        add(customerTotalLabel);
        add(customerScrollPane);
        add(addCustomerButton);
        add(customerLabel);
        add(backButton);
        add(sellButton);
        add(refundButton);
        add(swapButton);
    }
}
