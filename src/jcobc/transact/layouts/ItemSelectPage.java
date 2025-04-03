package jcobc.transact.layouts;
import jcobc.transact.Transact;
import jcobc.main.Interface;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class ItemSelectPage extends JPanel {

    private int selectedRow;
    private DefaultTableModel tableModel = null;


    public int selectedRow() {
        return selectedRow;
    }


    public void updateTablesDisplay(Object[][] data) {
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }


    public ItemSelectPage() {
        setLayout(null);
        setBackground(Interface.mediumColor);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Interface.darkColor);
        backButton.setForeground(Interface.lightColor);
        backButton.setBounds(30, 10, 80, 30);

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

        backButton.addActionListener(_ -> Interface.goPrevPage());
        table.addMouseListener (
            new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        selectedRow = row;
                        Transact.callAction("addSelected");
                    }
                }
            }
        );

        add(backButton);
        add(scrollPane);
    }    
}