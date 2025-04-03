package jcobc.transact.layouts;
import jcobc.transact.Transact;
import jcobc.main.Interface;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class ItemSelectPage extends JPanel {

    private final Color darkcolor = Color.BLACK;
    private final Color lightcolor = Color.WHITE;
    private int selectedRow;

    private JTable table;
    private DefaultTableModel tableModel;


    public int selectedRow() {
        return selectedRow;
    }


    public void updateTablesDisplay(Object[][] data) {
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }


    private void rowSelected() {
        Transact.callAction("addSelected");
    }


    public ItemSelectPage() {
        setLayout(null);
        setBackground(Interface.mediumColor);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Interface.darkColor);
        backButton.setForeground(Interface.lightColor);
        backButton.setBounds(30, 10, 80, 30);
        backButton.addActionListener(_ -> Interface.goPrevPage());

        String[] columns = {"ID", "Barcode", "Name", "Location", "Type", "Description", "Price", "Stock"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionBackground(Interface.lightColor);
        table.setSelectionForeground(Interface.darkColor);
        table.setBackground(Interface.darkColor);
        table.setForeground(Interface.lightColor);
        table.setBorder(new LineBorder(Interface.darkColor, 2));
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    selectedRow = row;
                    rowSelected();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(Interface.centerX(750), 50, 750, 500);

        add(backButton);
        add(scrollPane);
    }    
}