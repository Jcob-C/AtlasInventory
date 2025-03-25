package jcobc.main.layouts;
import jcobc.main.Home;
import jcobc.main.Interface;

import java.awt.Color;
import javax.swing.*;

public class HomePage extends JPanel {

    private final Color darkcolor = Color.BLACK;
    private final Color lightcolor = Color.WHITE;
    
    
    public HomePage() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);

        JButton button1 = new JButton("Inventory");
        button1.setBackground(lightcolor);
        button1.setForeground(darkcolor);
        button1.setBounds(Interface.centerX(150), 30, 150, 40);
        button1.addActionListener(_ -> Home.callAction("gotoInventory"));

        JButton button2 = new JButton("Transaction");
        button2.setBackground(lightcolor);
        button2.setForeground(darkcolor);
        button2.setBounds(Interface.centerX(150), 90, 150, 40);
        button2.addActionListener(_ -> Home.callAction("gotoTransact"));

        JButton button3 = new JButton("Button 3");
        button3.setBackground(lightcolor);
        button3.setForeground(darkcolor);
        button3.setBounds(Interface.centerX(150), 150, 150, 40);
        button3.addActionListener(_ -> Home.callAction("button3"));

        JButton button4 = new JButton("Button 4");
        button4.setBackground(lightcolor);
        button4.setForeground(darkcolor);
        button4.setBounds(Interface.centerX(150), 210, 150, 40);
        button4.addActionListener(_ -> Home.callAction("button4"));

        JButton button5 = new JButton("Button 5");
        button5.setBackground(lightcolor);
        button5.setForeground(darkcolor);
        button5.setBounds(Interface.centerX(150), 270, 150, 40);
        button5.addActionListener(_ -> Home.callAction("button5"));

        JButton button6 = new JButton("Log Out");
        button6.setBackground(lightcolor);
        button6.setForeground(darkcolor);
        button6.setBounds(Interface.centerX(150), 330, 150, 40);
        button6.addActionListener(_ -> Home.callAction("logout"));

        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(button5);
        add(button6);
    }
}
