package sandil;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import base.Main;

public class Login extends JPanel{
    private final String font_style = "Segoe UI";
    
    public void Log() {

        setLayout(null);
        setBounds(0, 0, 880, 660);
        setBackground(Main.getDarkColor());

        JPanel upperPanel = new JPanel();
        upperPanel.setBounds(0,0,880,30);
        upperPanel.setBackground(Main.getLightColor());

        JPanel lowerPanel = new JPanel();
        lowerPanel.setBounds(250,271,381,316);
        lowerPanel.setBackground(Main.getLightColor());

        ImageIcon image = new ImageIcon("src/main/resources/Atlas-Feeds-Logo-d.png");

        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(image);
        imageLabel.setBounds(268,79,345,143);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(204, 308, 215, 27);
        userLabel.setForeground(Color.BLACK);
        userLabel.setFont(new Font(font_style, Font.BOLD, 12));
        lowerPanel.add(userLabel);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(294, 405, 215, 27);
        passLabel.setForeground(Color.BLACK);
        passLabel.setFont(new Font(font_style, Font.BOLD, 12));
        lowerPanel.add(passLabel);

        JTextField userField = new JTextField();
        userField.setBounds(294, 339, 292, 38);
        lowerPanel.add(userField);

         JTextField passField = new JTextField();
        userField.setBounds(294, 437, 292, 38);
        lowerPanel.add(passField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(378,511,123,40);
        loginButton.setBackground(Main.getMidColor());
        loginButton.setForeground(Color.WHITE);
        lowerPanel.add(loginButton);

        JButton closeButton = new JButton("X");
        closeButton.setBounds(835,0,45,30);
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        upperPanel.add(closeButton);

        add(upperPanel);
        add(lowerPanel);
 
    }

    public static void Authentication(String username, String password) {

    }
}
