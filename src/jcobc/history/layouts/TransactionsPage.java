package jcobc.history.layouts;
import jcobc.history.History;
import jcobc.main.Interface;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TransactionsPage extends JPanel {

    private Integer selectedRow = null;
    private DefaultTableModel tableModel = null;


    public Integer selectedRow() {
        return selectedRow;
    }


    public void updateTableDisplay(Object[][] data) {
        tableModel.setRowCount(0);
        if (data == null) return;
        
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }


    public TransactionsPage() {
        setLayout(null);
        setBackground(Interface.mediumColor);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Interface.darkColor);
        backButton.setForeground(Interface.lightColor);
        backButton.setBounds(Interface.centerX(80)-325, 10, 80, 30);

        JButton searchButton = new JButton("Search ID");
        searchButton.setBackground(Interface.darkColor);
        searchButton.setForeground(Interface.lightColor);
        searchButton.setBounds(Interface.centerX(100)+325, 10, 100, 30);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(Interface.darkColor);
        refreshButton.setForeground(Interface.lightColor);
        refreshButton.setBounds(Interface.centerX(80)-225, 10, 80, 30);

        String[] columns = {"ID","Date & Time","Customer Name","Total Price"};
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

        backButton.addActionListener(_ -> History.callAction("gotoHistoryMenu"));
        searchButton.addActionListener(_ -> History.callAction("searchSaleID"));
        refreshButton.addActionListener(_ -> History.callAction("refreshSales"));
        table.addMouseListener (
            new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        selectedRow = row;
                        History.callAction("saleRowSelected");
                    }
                }
            }
        );

        add(refreshButton);
        add(searchButton);
        add(backButton);
        add(scrollPane);
    }    
}
