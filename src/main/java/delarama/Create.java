package delarama;

import javax.swing.*;

import main.Main;

import java.sql.*;
import java.awt.*;

public class Create extends JPanel {

    static JTextField userField, roleField, nameField,
    emailField, contactField, addressField;
    static JPasswordField passField;
    private final String font_style = "Segoe UI";

    public Create() {
        setBounds(0,0,880, 660);
        setBackground(new Color(1, 69, 24));
        setLayout(null);

        JPanel panel1 = new JPanel(null);
        panel1.setBackground(new Color(255, 153, 51));
        panel1.setBounds(0, 0, 880, 30);

        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(255, 153, 51));
        panel2.setBounds(0, 630, 880, 30);

        ImageIcon image = new ImageIcon("Resources_File/Atlas-Feeds-Logo.png");

        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(image);
        imageLabel.setBounds(511, 247, 345, 143);

        JPanel panel3 = new JPanel(null);
        panel3.setBackground(new Color(255, 153, 51));
        panel3.setBounds(40, 66, 449, 528);

        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setBounds(227, 66, 161, 66);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font(font_style, Font.BOLD, 15));
        add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(66, 133, 120, 37);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font(font_style, Font.BOLD, 12));
        add(userLabel);

        userField = new JTextField();
        userField.setBounds(188, 136, 240, 34);
        add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(66, 193, 120, 37);
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font(font_style, Font.BOLD, 12));
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(188, 196, 240, 34);
        add(passField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(66, 254, 120, 37);
        roleLabel.setForeground(Color.WHITE);
        roleLabel.setFont(new Font(font_style, Font.BOLD, 12));
        add(roleLabel);

        roleField = new JTextField();
        roleField.setBounds(188, 257, 240, 34);
        add(roleField);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(66, 313, 120, 37);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font(font_style, Font.BOLD, 12));
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(188, 316, 240, 34);
        add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(66, 370, 120, 37);
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(new Font(font_style, Font.BOLD, 12));
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(188, 373, 240, 34);
        add(emailField);

        JLabel contactLabel = new JLabel("Contact#:");
        contactLabel.setBounds(66, 428, 120, 37);
        contactLabel.setForeground(Color.WHITE);
        contactLabel.setFont(new Font(font_style, Font.BOLD, 12));
        add(contactLabel);

        contactField = new JTextField();
        contactField.setBounds(188, 431, 240, 34);
        add(contactField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(66, 482, 120, 37);
        addressLabel.setForeground(Color.WHITE);
        addressLabel.setFont(new Font(font_style, Font.BOLD, 12));
        add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(188, 485, 240, 34);
        add(addressField);

        JButton createButton = new JButton("Add Account");
        createButton.setBounds(346, 534, 123, 38);
        createButton.setBackground(new Color(1, 69, 24));
        createButton.setForeground(Color.WHITE);
        add(createButton);

        JButton backButton = new JButton("<-");
        backButton.setBounds(0, 0, 45, 30);
        backButton.setBackground(Color.BLUE);
        backButton.setForeground(Color.WHITE);
        panel1.add(backButton);

        add(panel1);
        add(panel2);
        add(panel3);
        add(imageLabel);

        createButton.addActionListener(e -> AddAccounts());

        backButton.addActionListener(e -> Main.change_card("accounts"));
    }

    private static void popupWarning(String message) {
        JOptionPane.showMessageDialog(null, message, "WARNING", JOptionPane.WARNING_MESSAGE);
    }

    private static void popupInformation(String message) {
        JOptionPane.showMessageDialog(null, message, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void AddAccounts() {
        try (Connection conn = DBC.getConnection()) {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String role = roleField.getText();
            String fullname = nameField.getText();
            String email = emailField.getText();
            String contact = contactField.getText();
            String address = addressField.getText();

            PreparedStatement check = conn.prepareStatement("SELECT * FROM Accounts WHERE Username = ?");
            check.setString(1, username);

            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                popupWarning("Username is already existing");
            } else {
                PreparedStatement insert = conn.prepareStatement(
                        "INSERT INTO Accounts (Username, Password, Role, Full_Name, Email, Contact_no, Address) VALUES (?,?,?,?,?,?,?)");

                insert.setString(1, username);
                insert.setString(2, password);
                insert.setString(3, role);
                insert.setString(4, fullname);
                insert.setString(5, email);
                insert.setString(6, contact);
                insert.setString(7, address);

                insert.executeUpdate();
                insert.close();

                popupInformation("Added Account Successfully!");
                UserSession.Audit_Trail("Created account for: " + fullname);
                clearFields();
            }

            rs.close();
            check.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearFields() {
        userField.setText("");
        passField.setText("");
        roleField.setText("");
        nameField.setText("");
        emailField.setText("");
        contactField.setText("");
        addressField.setText("");
    }
}