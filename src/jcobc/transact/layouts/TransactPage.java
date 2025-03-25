package jcobc.transact.layouts;
import jcobc.transact.Transact;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TransactPage extends JPanel {

    private final Color darkcolor = Color.BLACK;
    private final Color lightcolor = Color.WHITE;
    private int offsetX = -8;

    private JTable customerTable, inventoryTable;
    private DefaultTableModel customerTableModel, inventoryTableModel;
    private JLabel customerTotalLabel, inventoryTotalLabel;

    private final JButton 
    orderButton = new JButton("Order"),
    sellButton = new JButton("Sell"), 
    refundButton = new JButton("Refund"), 
    swapButton = new JButton("Swap");
    
    private int selectedRow = -1;
    private String selectedTable = null;

        
    public int getSelectedRow() {
        return selectedRow;
    }


    public String getSelectedTable() {
        return selectedTable;
    }


    public void updateTotalPrices(float newCustomerTotal, float newInventoryTotal) {
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


    public void setButtonsVisibility(boolean refund, boolean swap, boolean order, boolean sell) {
        refundButton.setVisible(refund);
        swapButton.setVisible(swap);
        orderButton.setVisible(order);
        sellButton.setVisible(sell);
    }


    private void backButtonPressed() {
        Transact.callAction("gotoHome");
    }


    private void addToCustomerPressed() {
        Transact.callAction("addToCustomerList");
    }


    private void addToInventoryPressed() {
        Transact.callAction("addToInventoryList");
    }


    private void rowSelected() {
        Transact.callAction("listRowSelected");
    }


    public TransactPage() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);

        JButton backButton = new JButton("Back");
        backButton.setBackground(lightcolor);
        backButton.setForeground(darkcolor);
        backButton.setBounds(offsetX + 10, 10, 70, 30);
        backButton.addActionListener(_ -> backButtonPressed());
        add(backButton);

        JLabel customerLabel = new JLabel("Customer", SwingConstants.CENTER);
        customerLabel.setForeground(lightcolor);
        customerLabel.setBounds(offsetX + 80, 5, 200, 30);
        add(customerLabel);
        
        JButton addCustomerButton = new JButton("Add");
        addCustomerButton.setBackground(lightcolor);
        addCustomerButton.setForeground(darkcolor);
        addCustomerButton.setBounds(offsetX + 220, 10, 70, 30);
        addCustomerButton.addActionListener(_ -> addToCustomerPressed());
        add(addCustomerButton);
        
        String[] columns = {"ID", "Name", "Price", "Condition", "Quantity"};
        customerTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        customerTable = new JTable(customerTableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerTable.setSelectionBackground(darkcolor);
        customerTable.setSelectionForeground(lightcolor);
        customerTable.setBackground(lightcolor);
        customerTable.setForeground(darkcolor);
        customerTable.setBorder(new LineBorder(lightcolor, 2));
        customerTable.setRowHeight(25);
        customerTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectedRow = customerTable.getSelectedRow();
                selectedTable = "customer";
                rowSelected();
            }
        });
        
        JScrollPane customerScrollPane = new JScrollPane(customerTable);
        customerScrollPane.setBounds(offsetX + 10, 50, 280, 300);
        add(customerScrollPane);

        customerTotalLabel = new JLabel("Total Price: 0.00", SwingConstants.CENTER);
        customerTotalLabel.setForeground(lightcolor);
        customerTotalLabel.setBounds(offsetX + 10, 30, 280, 15);
        add(customerTotalLabel);

        JLabel inventoryLabel = new JLabel("Inventory", SwingConstants.CENTER);
        inventoryLabel.setForeground(lightcolor);
        inventoryLabel.setBounds(offsetX + 380, 5, 200, 30);
        add(inventoryLabel);
        
        JButton addInventoryButton = new JButton("Add");
        addInventoryButton.setBackground(lightcolor);
        addInventoryButton.setForeground(darkcolor);
        addInventoryButton.setBounds(offsetX + 520, 10, 70, 30);
        addInventoryButton.addActionListener(_ -> addToInventoryPressed());
        add(addInventoryButton);
        
        String[] columns2 = {"ID", "Name", "Price", "Quantity"};
        inventoryTableModel = new DefaultTableModel(columns2, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        inventoryTable = new JTable(inventoryTableModel);
        inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventoryTable.setSelectionBackground(darkcolor);
        inventoryTable.setSelectionForeground(lightcolor);
        inventoryTable.setBackground(lightcolor);
        inventoryTable.setForeground(darkcolor);
        inventoryTable.setBorder(new LineBorder(lightcolor, 2));
        inventoryTable.setRowHeight(25);
        inventoryTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectedRow = inventoryTable.getSelectedRow();
                selectedTable = "inventory";
                rowSelected();
            }
        });
        
        JScrollPane inventoryScrollPane = new JScrollPane(inventoryTable);
        inventoryScrollPane.setBounds(offsetX + 310, 50, 280, 300);
        add(inventoryScrollPane);

        inventoryTotalLabel = new JLabel("Total Price: 0.00", SwingConstants.CENTER);
        inventoryTotalLabel.setForeground(lightcolor);
        inventoryTotalLabel.setBounds(offsetX + 310, 30, 280, 15);
        add(inventoryTotalLabel);
        
        orderButton.setBackground(lightcolor);
        sellButton.setBackground(lightcolor);
        refundButton.setBackground(lightcolor);
        swapButton.setBackground(lightcolor);
        
        orderButton.setBounds(offsetX + 310, 360, 130, 40);
        sellButton.setBounds(offsetX + 450, 360, 130, 40);
        refundButton.setBounds(offsetX + 20, 360, 130, 40);
        swapButton.setBounds(offsetX + 160, 360, 130, 40);
        
        add(orderButton);
        add(sellButton);
        add(refundButton);
        add(swapButton);
    }
}
