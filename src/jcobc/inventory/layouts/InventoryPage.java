package jcobc.inventory.layouts;
import jcobc.inventory.Inventory;
import jcobc.main.Interface;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class InventoryPage extends JPanel {

    private final Color darkcolor = Color.BLACK;
    private final Color lightcolor = Color.WHITE;

    private final Integer selectedCell[] = new Integer[2];

    private JTable table;
    private DefaultTableModel tableModel;


    public Integer[] selectedCell() {
        return selectedCell;
    }


    public void updateTableDisplay(Object[][] data) {
        
        tableModel.setRowCount(0);
        if (data == null) return;
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
    
    
    private void cellSelect(int row, int column) {
        selectedCell[0] = row;
        selectedCell[1] = column;
        Inventory.callAction("cellSelected");
    }


    private void sortButton() {
        Inventory.callAction("sortInventory");
    }


    private void backButton() {
        Inventory.callAction("gotoHome");
    }


    private void addNewButton() {
        Inventory.callAction("gotoNewItem");
    }


    private void searchButton() {
        Inventory.callAction("search");
    }


    private void refreshButton() {
        Inventory.callAction("gotoInventory");
    }


    public InventoryPage() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);

        JButton backButton = new JButton("Back");
        backButton.setBackground(lightcolor);
        backButton.setForeground(darkcolor);
        backButton.setBounds(Interface.centerX(80)-325, 10, 80, 30);
        backButton.addActionListener(_ -> backButton());

        JButton sortButton = new JButton("Sort");
        sortButton.setBackground(lightcolor);
        sortButton.setForeground(darkcolor);
        sortButton.setBounds(Interface.centerX(80)+325, 10, 80, 30);
        sortButton.addActionListener(_ -> sortButton());

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(lightcolor);
        searchButton.setForeground(darkcolor);
        searchButton.setBounds(Interface.centerX(80)+225, 10, 80, 30);
        searchButton.addActionListener(_ -> searchButton());

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(lightcolor);
        refreshButton.setForeground(darkcolor);
        refreshButton.setBounds(Interface.centerX(80)-225, 10, 80, 30);
        refreshButton.addActionListener(_ -> refreshButton());
        
        JButton topMiddleButton = new JButton("Add New");
        topMiddleButton.setBackground(lightcolor);
        topMiddleButton.setForeground(darkcolor);
        topMiddleButton.setBounds(Interface.centerX(120), 10, 120, 30);
        topMiddleButton.addActionListener(_ -> addNewButton());

        String[] columns = {"ID", "Barcode", "Name", "Location", "Type", "Description", "Price", "Stock"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionBackground(darkcolor);
        table.setSelectionForeground(lightcolor);
        table.setBackground(lightcolor);
        table.setForeground(darkcolor);
        table.setBorder(new LineBorder(lightcolor, 2));
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                if (row != -1 && col != -1) {
                    cellSelect(row, col);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(Interface.centerX(750), 50, 750, 500);

        add(refreshButton);
        add(searchButton);
        add(backButton);
        add(sortButton);
        add(topMiddleButton);
        add(scrollPane);
    }    
}
