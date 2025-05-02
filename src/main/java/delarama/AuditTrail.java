package delarama;

import java.sql.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.Main;

public class AuditTrail extends JPanel {
    private final DefaultTableModel model = new DefaultTableModel();
    private final JTextField searchField = new JTextField();
    private final String font_style = "Segoe UI";
    
    public AuditTrail() {
        setLayout(null);
        setBackground(new Color(1, 69, 24));
        setBounds(0,0,880, 660);

        model.addColumn("ID");
        model.addColumn("Full Name");
        model.addColumn("Activity");
        model.addColumn("Date");
        model.addColumn("Time");

        JTable table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(52);
        table.getColumnModel().getColumn(1).setPreferredWidth(211);
        table.getColumnModel().getColumn(2).setPreferredWidth(282);
        table.getColumnModel().getColumn(3).setPreferredWidth(129);
        table.getColumnModel().getColumn(4).setPreferredWidth(129);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(40, 136, 800, 458);

        JPanel panel1 = new JPanel(null);
        panel1.setBackground(new Color(255, 153, 51));
        panel1.setBounds(0, 0, 880, 30);

        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(255, 153, 51));
        panel2.setBounds(0, 630, 880, 30);

        JLabel title = new JLabel("Audit Trail");
        title.setFont(new Font(font_style, Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 0, 200, 20);
        panel1.add(title);

        JLabel searchLabel = new JLabel("Search by ID:");
        searchLabel.setBounds(1, 49, 139, 34);
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font(font_style, Font.BOLD, 12));

        searchField.setBounds(132, 49, 139, 34);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(281, 50, 79, 33);
        searchButton.setBackground(new Color(255, 153, 51));
        searchButton.setForeground(Color.WHITE);

        JButton createButton = new JButton("Create");
        createButton.setBounds(457, 50, 79, 33);
        createButton.setBackground(new Color(255, 153, 51));
        createButton.setForeground(Color.WHITE);

        JButton updeleteButton = new JButton("Update & Delete");
        updeleteButton.setBounds(564, 50, 143, 33);
        updeleteButton.setBackground(new Color(255, 153, 51));
        updeleteButton.setForeground(Color.WHITE);

        JButton accountButton = new JButton("Account");
        accountButton.setBounds(734, 50, 105, 33);
        accountButton.setBackground(new Color(255, 153, 51));
        accountButton.setForeground(Color.WHITE);

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
        add(updeleteButton);
        add(accountButton);

        LoadAllActivities();

        searchButton.addActionListener(e -> {
            String input = searchField.getText().trim();
            if (input.isEmpty()) {
                LoadAllActivities();
            } else {
                searchAccount();
            }
        });
        
        createButton.addActionListener(e -> Main.changeCard("account create"));

        updeleteButton.addActionListener(e ->  Main.changeCard("updelete"));

        accountButton.addActionListener(e -> Main.changeCard("accounts"));

        closeButton.addActionListener(e -> System.exit(0));
    }

    private void popupWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "WARNING", JOptionPane.WARNING_MESSAGE);
    }

    private void popupError(String message) {
        JOptionPane.showMessageDialog(this, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public void LoadAllActivities() {
        try (Connection conn = DBC.getConnection()) {
            model.setRowCount(0);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Activity ORDER BY activity_id DESC");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("Id"),
                    rs.getString("Full_name"),
                    rs.getString("Activity"),
                    rs.getString("Ddate"),
                    rs.getString("Ttime")
                });
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchAccount() {
        int id;

        try {
            id = Integer.parseInt(searchField.getText().trim());
        } catch (NumberFormatException e) {
            popupWarning("Invalid Input! Try to input a number instead.");
            return;
        }

        try (Connection conn = DBC.getConnection()) {
            model.setRowCount(0);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Activity WHERE Id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("Id"),
                    rs.getString("Full_name"),
                    rs.getString("Activity"),
                    rs.getString("Ddate"),
                    rs.getString("Ttime")
                });
            } else {
                popupError("Account not found!");
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}