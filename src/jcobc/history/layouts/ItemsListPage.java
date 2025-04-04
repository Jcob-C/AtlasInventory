package jcobc.history.layouts;
import jcobc.history.History;
import jcobc.main.Interface;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class ItemsListPage extends JPanel {

    private DefaultTableModel tableModel = null;


    public void updateTableDisplay(String[][] data) {
        tableModel.setRowCount(0);
        if (data == null) return;
        String newTable[][] = new String[data.length][4];
        for (int i = 0; i < data.length; i++) {
            newTable[i][0] = data[i][2];
            newTable[i][1] = data[i][3];
            newTable[i][2] = data[i][4];
            newTable[i][3] = data[i][5];
        }
        for (String[] row : newTable) {
            tableModel.addRow(row);
        }
    }


    public ItemsListPage() {
        setLayout(null);
        setBackground(Interface.mediumColor);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Interface.darkColor);
        backButton.setForeground(Interface.lightColor);
        backButton.setBounds(Interface.centerX(80)-325, 10, 80, 30);

        String[] columns = {"Item ID","Item Name","Sold Price","Quantity Sold"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Makes all cells uneditable
            }
        };
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

        backButton.addActionListener(_ -> History.callAction("transactions"));

        add(backButton);
        add(scrollPane);
    }    
}
