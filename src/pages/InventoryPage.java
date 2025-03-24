package pages;
import modules.Pages;
import modules.Interface;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class InventoryPage extends JPanel {

    private final Color darkcolor = Color.BLACK;
    private final Color lightcolor = Color.WHITE;

    private final int selectedCell[] = new int[2];

    private JTable table;
    private DefaultTableModel tableModel;


    public int[] getSelected() {
        return selectedCell;
    }


    public void updateTable(Object[][] data) {
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
    
    
    private void cellSelected(int row, int column) {
        selectedCell[0] = row;
        selectedCell[1] = column;
        Pages.callEvent("cellSelected");
    }


    private void sortInventory() {
        Pages.callEvent("sortInventory");
    }


    private void backButtonPressed() {
        Interface.changePage("home");
    }


    private void gotoNewItemPage() {
        Interface.changePage("newitem");
    }


    public InventoryPage() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);

        JButton backButton = new JButton("Back");
        backButton.setBackground(lightcolor);
        backButton.setForeground(darkcolor);
        backButton.setBounds(30, 10, 80, 30);
        backButton.addActionListener(_ -> backButtonPressed());

        JButton sortButton = new JButton("Sort");
        sortButton.setBackground(lightcolor);
        sortButton.setForeground(darkcolor);
        sortButton.setBounds(475, 10, 80, 30);
        sortButton.addActionListener(_ -> sortInventory());
        
        JButton topMiddleButton = new JButton("Add New");
        topMiddleButton.setBackground(lightcolor);
        topMiddleButton.setForeground(darkcolor);
        topMiddleButton.setBounds(Interface.getCenterX(120), 10, 120, 30);
        topMiddleButton.addActionListener(_ -> gotoNewItemPage());

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
                    cellSelected(row, col);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(Interface.getCenterX(560), 50, 560, 350);

        add(backButton);
        add(sortButton);
        add(topMiddleButton);
        add(scrollPane);
    }    
}
