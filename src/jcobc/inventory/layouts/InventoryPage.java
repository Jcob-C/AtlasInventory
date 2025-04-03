package jcobc.inventory.layouts;
import jcobc.inventory.Inventory;
import jcobc.main.Interface;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class InventoryPage extends JPanel {

    private final Integer selectedCell[] = new Integer[2];
    private DefaultTableModel tableModel = null;


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


    public InventoryPage() {
        setLayout(null);
        setBackground(Interface.mediumColor);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Interface.darkColor);
        backButton.setForeground(Interface.lightColor);
        backButton.setBounds(Interface.centerX(80)-325, 10, 80, 30);

        JButton sortButton = new JButton("Sort");
        sortButton.setBackground(Interface.darkColor);
        sortButton.setForeground(Interface.lightColor);
        sortButton.setBounds(Interface.centerX(80)+325, 10, 80, 30);

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(Interface.darkColor);
        searchButton.setForeground(Interface.lightColor);
        searchButton.setBounds(Interface.centerX(80)+225, 10, 80, 30);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(Interface.darkColor);
        refreshButton.setForeground(Interface.lightColor);
        refreshButton.setBounds(Interface.centerX(80)-225, 10, 80, 30);
        
        JButton topMiddleButton = new JButton("Add New");
        topMiddleButton.setBackground(Interface.darkColor);
        topMiddleButton.setForeground(Interface.lightColor);
        topMiddleButton.setBounds(Interface.centerX(120), 10, 120, 30);

        String[] columns = {"ID","Barcode","Name","Location","Type","Description","Price","Stock"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setSelectionBackground(Interface.lightColor);
        table.setSelectionForeground(Interface.darkColor);
        table.setBackground(Interface.darkColor);
        table.setForeground(Interface.lightColor);
        table.setBorder(new LineBorder(Interface.darkColor, 2));
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(Interface.centerX(750), 50, 750, 500);

        backButton.addActionListener(_ -> Inventory.callAction("gotoHome"));
        sortButton.addActionListener(_ -> Inventory.callAction("sortInventory"));
        searchButton.addActionListener(_ -> Inventory.callAction("search"));
        refreshButton.addActionListener(_ -> Inventory.callAction("gotoInventory"));
        topMiddleButton.addActionListener(_ -> Inventory.callAction("gotoNewItem"));
        table.addMouseListener (
            new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int 
                        row = table.getSelectedRow(),
                        col = table.getSelectedColumn();
                    if (row != -1 && col != -1) {
                        selectedCell[0] = row;
                        selectedCell[1] = col;
                        Inventory.callAction("cellSelected");
                    }
                }
            }
        );

        add(refreshButton);
        add(searchButton);
        add(backButton);
        add(sortButton);
        add(topMiddleButton);
        add(scrollPane);
    }    
}
