package delarama;

import base.DB;
import javax.swing.*; 
import javax.swing.table.DefaultTableModel;
import java.awt.*; 
import java.sql.*;
import base.Main;

public class Account_Manager extends JPanel {
    private static final DefaultTableModel model = new DefaultTableModel();
    private static final JTextField searchField = new JTextField();
    private static final String font_style = "Segoe UI";
    private final JTable table = new JTable(model);
    
    public Account_Manager() {
        setLayout(null);
        setBounds(0, 0, 880, 660);
        setBackground(Main.getDarkColor());
        
        
        model.addColumn("ID");
        model.addColumn("Username");
        model.addColumn("Acc_Password");
        model.addColumn("User_Role");
        model.addColumn("Full_Name");
        model.addColumn("Email");
        model.addColumn("Contact_no");
        model.addColumn("Address");
        
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(13);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(40, 136, 800, 458);
    
        JPanel panel1 = new JPanel(null);
        panel1.setBackground(Main.getLightColor());
        panel1.setBounds(0, 0, 880, 30);
    
        JPanel panel2 = new JPanel();
        panel2.setBackground(Main.getLightColor());
        panel2.setBounds(0, 630, 880, 30);
    
        JLabel searchLabel = new JLabel("Search by ID:");
        searchLabel.setBounds(50, 49, 139, 34);
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font(font_style, Font.BOLD, 12));
    
        searchField.setBounds(132, 49, 139, 34);
    
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(281, 50, 79, 33);
        searchButton.setBackground(Main.getMidColor());
        searchButton.setForeground(Color.WHITE);
    
        JButton createButton = new JButton("Create");
        createButton.setBounds(457, 50, 79, 33);
        createButton.setBackground(Main.getMidColor());
        createButton.setForeground(Color.WHITE);
    
        JButton auditButton = new JButton("Audit Trail");
        auditButton.setBounds(734, 50, 105, 33);
        auditButton.setBackground(Main.getMidColor());
        auditButton.setForeground(Color.WHITE);
    
        JButton updateButton = new JButton("Update");
        updateButton.setVisible(false);
        updateButton.setBounds(553, 50, 79, 33);
        updateButton.setBackground(Main.getMidColor());
        updateButton.setForeground(Color.WHITE);
    
        JButton deleteButton = new JButton("Delete");
        deleteButton.setVisible(false);
        deleteButton.setBounds(644, 50, 79, 33);
        deleteButton.setBackground(Main.getMidColor());
        deleteButton.setForeground(Color.WHITE);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(734, 93, 105, 33);
        refreshButton.setBackground(Main.getMidColor());
        refreshButton.setForeground(Color.WHITE);

        JButton backButton = new JButton("<");
        backButton.setBounds(0, 0, 45, 30);
        backButton.setBackground(Main.getMidColor());
        backButton.setForeground(Color.WHITE);
        panel1.add(backButton);
        
        JButton closeButton = new JButton("X");
        closeButton.setBounds(835, 0, 45, 30);
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        panel1.add(closeButton);
    
        add(panel1);
        add(panel2);
        add(scroll);
        add(searchLabel);
        add(searchField);
        add(searchButton);
        add(createButton);
        add(auditButton);
        add(refreshButton);
        add(updateButton);
        add(deleteButton);
    
        LoadAllAccounts();
        
        searchButton.addActionListener(e -> {
            String input = searchField.getText().trim();
            if (input.isEmpty()) {
                LoadAllAccounts();
            } else {
                searchAccount();
            }
        });

        createButton.addActionListener(e -> {
            Panels.allFalseVisibility();
            Panels.createPanel.setVisible(true);
        });
    
        auditButton.addActionListener(e -> {
            Panels.allFalseVisibility();
            Panels.auditTrailPanel.setVisible(true);
        });
        
        updateButton.addActionListener(e -> updateSelectedCell());
        
        deleteButton.addActionListener(e -> deleteSelectedRow());
    
        refreshButton.addActionListener(e -> LoadAllAccounts());
    
        closeButton.addActionListener(e -> System.exit(0));

        backButton.addActionListener(e -> Main.changeCard("dashboard"));
    
        table.getSelectionModel().addListSelectionListener(e -> {
            boolean selected = table.getSelectedRow() != -1;
            updateButton.setVisible(selected);
            deleteButton.setVisible(selected);
        });
    }
    
    private void popupWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "WARNING", JOptionPane.WARNING_MESSAGE);
    }

    
    private int popupConfirmation(String message, String title) {
        return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
    }
    
    private void updateSelectedCell() {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        
        if (row != -1 && col != -1) {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
    
            Object newValue = table.getValueAt(row, col);
            String columnName = model.getColumnName(col);
            int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        
            if (columnName.equals("ID")) return;
        
            boolean isEmpty = false;
            if (newValue == null) {
                isEmpty = true;
            } else if (newValue.toString().trim().isEmpty()) {
                isEmpty = true;
            }
            
            if (isEmpty) {
                popupWarning("Empty input is not allowed");
                LoadAllAccounts();
                return;
            }
            
            if (columnName.equals("Username")) {
                try (Connection conn = DB.getConnection()) {
                    PreparedStatement checkStmt = conn.prepareStatement("SELECT ID FROM Accounts WHERE Username = ? AND ID != ?");
                    checkStmt.setString(1, newValue.toString().trim());
                    checkStmt.setInt(2, id);
                    ResultSet rs = checkStmt.executeQuery();
        
                    if (rs.next()) {
                        popupWarning("Username already exists!");
                        LoadAllAccounts();
                        return;
                    }
                    rs.close();
                    checkStmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    LoadAllAccounts();
                    return;
                }
            }
        
            try (Connection conn = DB.getConnection()) {
                String query = "UPDATE Accounts SET `" + columnName + "` = ? WHERE ID = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setObject(1, newValue);
                stmt.setInt(2, id);
                stmt.executeUpdate();
                AuditTrail.Audit_Trail("Updated " + columnName + " for ID: " + id);
            } catch (Exception e) {
                e.printStackTrace();
                LoadAllAccounts();
            }
        }
    }
    
    private void deleteSelectedRow() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int confirm = popupConfirmation("Are you sure you want to delete this account?", "Confirm Deletion");
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                try (Connection conn = DB.getConnection()) {
                    PreparedStatement stmt = conn.prepareStatement("DELETE FROM Accounts WHERE Id = ?");
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                    model.removeRow(row);
                    AuditTrail.Audit_Trail("Deleted account with ID: " + id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    static void LoadAllAccounts() {
        try (Connection conn = DB.getConnection()) {
            model.setRowCount(0);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Accounts");
    
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("ID"),
                        rs.getString("Username"),
                        rs.getString("Acc_Password"),
                        rs.getString("User_Role"),
                        rs.getString("Full_name"),
                        rs.getString("Email"),
                        rs.getString("Contact_no"),
                        rs.getString("Address")
                });
            }
    
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void searchAccount() {
        int id;
        try {
            id = Integer.parseInt(searchField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Try to input a number instead.", "WARNING", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        try (Connection conn = DB.getConnection()) {
            model.setRowCount(0);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Accounts WHERE ID = ?");
            stmt.setInt(1, id);
    
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("ID"),
                        rs.getString("Username"),
                        rs.getString("Acc_Password"),
                        rs.getString("User_Role"),
                        rs.getString("Full_name"),
                        rs.getString("Email"),
                        rs.getString("Contact_no"),
                        rs.getString("Address")
                });
            } else {
                JOptionPane.showMessageDialog(null, "Account not found!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
    
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}