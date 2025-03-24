package pages;
import modules.Pages;
import modules.Interface;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Login extends JPanel {

    private final Color darkcolor = Color.BLACK;
    private final Color lightcolor = Color.WHITE;

    private final JTextField userField = new JTextField();
    private final JPasswordField passField = new JPasswordField();


    public void clearInputAccount() {
        userField.setText(null);
        passField.setText(null);
    }


    public String[] getInputAccount() {
        String password = "";
        for (char x : passField.getPassword())
            password += x;
        return new String[] {userField.getText(), password};
    }


    public Login() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(Interface.getCenterX(200),60,200,40);
        usernameLabel.setForeground(lightcolor);

        userField.setBounds(Interface.getCenterX(200),100,200,40);
        userField.setBorder(new LineBorder(lightcolor, 2));
        userField.setBackground(darkcolor);
        userField.setForeground(lightcolor);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(Interface.getCenterX(200),160,200,40);
        passwordLabel.setForeground(lightcolor);

        passField.setBounds(Interface.getCenterX(200),200,200,40);
        passField.setBorder(new LineBorder(lightcolor, 2));
        passField.setBackground(darkcolor);
        passField.setForeground(lightcolor);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBackground(lightcolor);
        loginButton.setForeground(darkcolor);
        loginButton.setBounds(Interface.getCenterX(100),300,100,20);
        loginButton.addActionListener(_ -> Pages.callEvent("login"));

        add(passwordLabel);
        add(usernameLabel);
        add(loginButton);
        add(userField);
        add(passField);
    }
}
