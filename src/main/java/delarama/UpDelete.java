package delarama;

import javax.swing.*;

import main.Main;

import java.awt.*;
import java.sql.*;

public class UpDelete extends JPanel {
    private final JTextField userField = new JTextField();
    private final JTextField roleField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField contactField = new JTextField();
    private final JTextField addressField = new JTextField();
    private final JTextField searchField = new JTextField();
    private final JTextField passField = new JTextField();
    private final String font_style = "Segoe UI";

    public UpDelete() {
        setLayout(null);
        setBackground(new Color(1, 69, 24));
        setBounds(0,0,880, 660);

        JPanel panel1 = new JPanel(null);
        panel1.setBackground(new Color(255, 153, 51));
        panel1.setBounds(0, 0, 880, 30);

        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(255, 153, 51));
        panel2.setBounds(0, 630, 880, 30);

        JPanel panel3 = new JPanel(null);
        panel3.setBackground(new Color(255, 153, 51));
        panel3.setBounds(40, 66, 802, 528);
        
        JLabel searchLabel = new JLabel("Search by ID:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font(font_style, Font.BOLD, 12));
        searchLabel.setBounds(85, 153, 130, 39);
        add(searchLabel);

        searchField.setBounds(184, 157, 257, 36);
        add(searchField);
        
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font(font_style, Font.BOLD, 12));
        userLabel.setBounds(85, 252, 130, 39);
        add(userLabel);

        userField.setBounds(171, 254, 257, 36);
        add(userField);
        
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font(font_style, Font.BOLD, 12));
        passLabel.setBounds(85, 316, 130, 39);
        add(passLabel);

        passField.setBounds(171, 318, 257, 36);
        add(passField);
        
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setForeground(Color.WHITE);
        roleLabel.setFont(new Font(font_style, Font.BOLD, 12));
        roleLabel.setBounds(85, 381, 130, 39);
        add(roleLabel);

        roleField.setBounds(171, 383, 257, 36);
        add(roleField);
        
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font(font_style, Font.BOLD, 12));
        nameLabel.setBounds(85, 446, 130, 39);
        add(nameLabel);

        nameField.setBounds(172, 448, 257, 36);
        add(nameField);
        
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(new Font(font_style, Font.BOLD, 12));
        emailLabel.setBounds(475, 252, 130, 39);
        add(emailLabel);

        emailField.setBounds(548, 254, 257, 36);
        add(emailField);
        
        JLabel contactLabel = new JLabel("Contact #:");
        contactLabel.setForeground(Color.WHITE);
        contactLabel.setFont(new Font(font_style, Font.BOLD, 12));
        contactLabel.setBounds(475, 319, 130, 39);
        add(contactLabel);

        contactField.setBounds(551, 323, 257, 36);
        add(contactField);
        
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setForeground(Color.WHITE);
        addressLabel.setFont(new Font(font_style, Font.BOLD, 12));
        addressLabel.setBounds(475, 383, 130, 39);
        add(addressLabel);

        addressField.setBounds(551, 383, 257, 36);
        add(addressField);
        
        JButton searchButton = new JButton("Search");
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(1, 69, 24));
        searchButton.setBounds(466, 157, 123, 38);
        add(searchButton);

        JButton updateButton = new JButton("Update");
        updateButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(1, 69, 24));
        updateButton.setBounds(510, 506, 123, 38);
        add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(1, 69, 24));
        deleteButton.setBounds(668, 506, 123, 38);
        add(deleteButton);

        JButton backButton = new JButton("<-");
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(Color.BLUE);
        backButton.setBounds(0, 0, 45, 30);
        panel1.add(backButton);
        
        add(panel1);
        add(panel2);
        add(panel3);
        
        searchButton.addActionListener(e -> searchAccount());
        updateButton.addActionListener(e -> updateAccount());
        deleteButton.addActionListener(e -> deleteAccount());
        backButton.addActionListener(e -> Main.changeCard("accounts"));
    }

    private void popupWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "WARNING", JOptionPane.WARNING_MESSAGE);
    }

    private void popupInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
    }

    private void popupError(String message) {
        JOptionPane.showMessageDialog(this, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public void searchAccount() {
        try (Connection conn = DBC.getConnection()) {
            int id = Integer.parseInt(searchField.getText().trim());

            PreparedStatement search = conn.prepareStatement("SELECT * FROM Accounts WHERE Id = ?");
            search.setInt(1, id);
            ResultSet rs = search.executeQuery();

            if (rs.next()) {
                userField.setText(rs.getString("Username"));
                passField.setText(rs.getString("Password"));
                roleField.setText(rs.getString("Role"));
                nameField.setText(rs.getString("Full_Name"));
                emailField.setText(rs.getString("Email"));
                contactField.setText(rs.getString("Contact_no"));
                addressField.setText(rs.getString("Address"));
            } else {
                popupError("Account not found!");
            }

            rs.close();
            search.close();
        } catch (NumberFormatException e) {
            popupWarning("Invalid ID. Please enter a number.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAccount() {
        try (Connection conn = DBC.getConnection()) {
            int id = Integer.parseInt(searchField.getText().trim());

            PreparedStatement update = conn.prepareStatement("UPDATE Accounts SET Username=?, Password=?, Role=?, Full_Name=?, Email=?, Contact_no=?, Address=? WHERE Id=?");
            update.setString(1, userField.getText());
            update.setString(2, passField.getText());
            update.setString(3, roleField.getText());
            update.setString(4, nameField.getText());
            update.setString(5, emailField.getText());
            update.setString(6, contactField.getText());
            update.setString(7, addressField.getText());
            update.setInt(8, id);

            int rows = update.executeUpdate();
            if (rows > 0) {
                popupInfo("Account has been updated successfully!");
                UserSession.Audit_Trail("Updated the account of: " + nameField.getText());
                clearFields();
            } else {
                popupError("Update failed!");
            }

            update.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount() {
        try (Connection conn = DBC.getConnection()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to delete this account?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(searchField.getText().trim());
                
                PreparedStatement fetch = conn.prepareStatement("SELECT Full_Name FROM Accounts WHERE Id = ?");
                fetch.setInt(1, id);
                ResultSet rs = fetch.executeQuery();

                if (rs.next()) {
                    String fullname = rs.getString("Full_Name");

                    PreparedStatement delete = conn.prepareStatement("DELETE FROM Accounts WHERE Id = ?");
                    delete.setInt(1, id);
                    int rows = delete.executeUpdate();

                    if (rows > 0) {
                        popupInfo("Account has been deleted successfully!");
                        UserSession.Audit_Trail("Deleted the account of: " + fullname);
                        clearFields();
                    } else {
                        popupError("Delete failed!");
                    }

                    delete.close();
                } else {
                    popupError("Account not found!");
                }

                rs.close();
                fetch.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFields() {
        searchField.setText("");
        userField.setText("");
        passField.setText("");
        roleField.setText("");
        nameField.setText("");
        emailField.setText("");
        contactField.setText("");
        addressField.setText("");
    }
}