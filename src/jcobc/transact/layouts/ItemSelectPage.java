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


    public int getSelectedRow() {
        return selectedRow;
    }


    public void updateTable(Object[][] data) {
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }


    public ItemSelectPage() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);

        JButton backButton = new JButton("Back");
        backButton.setBackground(lightcolor);
        backButton.setForeground(darkcolor);
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
                if (row != -1) {
                    selectedRow = row;
                    Transact.callAction("addSelected");
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(Interface.centerX(560), 50, 560, 350);

        add(backButton);
        add(scrollPane);
    }    
}