package sandil;

import base.DB;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import base.Main;
import delarama.AuditTrail;

public class Login extends JPanel{
    private final String font_style = "Segoe UI";

    public Login() {
        setLayout(null);
        setBackground(Main.getDarkColor());

        JPanel upperPanel = new JPanel();
        upperPanel.setBounds(0,0,880,30);
        upperPanel.setBackground(Main.getLightColor());

        JPanel lowerPanel = new JPanel();
        lowerPanel.setBounds(250,271,381,316);
        lowerPanel.setBackground(Main.getLightColor());
        lowerPanel.setLayout(null);

        JPanel lowerPanel2 = new JPanel();
        lowerPanel2.setBounds(0,630,880,30);
        lowerPanel2.setBackground(Main.getLightColor());
        lowerPanel2.setLayout(null);

        ImageIcon image = new ImageIcon("src/main/resources/Atlas-Feeds-Logo-d.png");

        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(image);
        imageLabel.setBounds(268,79,345,143);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(44, 37, 215, 27);
        userLabel.setForeground(Color.BLACK);
        userLabel.setFont(new Font(font_style, Font.BOLD, 12));
        lowerPanel.add(userLabel);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(44, 134, 215, 27);
        passLabel.setForeground(Color.BLACK);
        passLabel.setFont(new Font(font_style, Font.BOLD, 12));
        lowerPanel.add(passLabel);

        JTextField userField = new JTextField();
        userField.setBounds(44, 68, 292, 38);
        lowerPanel.add(userField);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(44, 166, 292, 38); 
        lowerPanel.add(passField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(128, 240, 123, 40);
        loginButton.setBackground(Main.getMidColor());
        loginButton.setForeground(Color.WHITE);
        lowerPanel.add(loginButton);

        JButton closeButton = new JButton("X");
        closeButton.setBounds(835, 0, 45, 30);
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        upperPanel.add(closeButton);
        upperPanel.setLayout(null);

        add(upperPanel);
        add(lowerPanel);
        add(lowerPanel2);
        add(imageLabel);

        loginButton.addActionListener(e -> {
            String user = userField.getText();
            String password = new String(passField.getPassword());

            boolean isAuthenticated = Authentication(user, password);

            if (isAuthenticated) {
                userField.setText("");
                passField.setText("");
                Main.popupMessage("Login successful!");
                Main.gotoDashboard();
            } else {
                Main.popupError("Invalid username or password");
            }
        });

        closeButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public static boolean Authentication(String username, String password) {
        try (Connection conn = DB.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Accounts WHERE BINARY Username = ? AND BINARY Acc_Password = ?");

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Main.loggedInID = rs.getString("ID");
                Main.userFullName = rs.getString("Full_Name");

                AuditTrail.Audit_Trail("Logged in");
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}