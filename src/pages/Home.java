package pages;
import modules.Pages;
import modules.Interface;

import java.awt.Color;
import javax.swing.*;

public class Home extends JPanel {

    private final Color darkcolor = Color.BLACK;
    private final Color lightcolor = Color.WHITE;

    
    public Home() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);

        JButton button1 = new JButton("Inventory");
        button1.setBackground(lightcolor);
        button1.setForeground(darkcolor);
        button1.setBounds(Interface.getCenterX(150), 30, 150, 40);
        button1.addActionListener(_ -> Pages.callEvent("inventory"));

        JButton button2 = new JButton("Transaction");
        button2.setBackground(lightcolor);
        button2.setForeground(darkcolor);
        button2.setBounds(Interface.getCenterX(150), 90, 150, 40);
        button2.addActionListener(_ -> Interface.changePage("transaction"));

        JButton button3 = new JButton("Button 3");
        button3.setBackground(lightcolor);
        button3.setForeground(darkcolor);
        button3.setBounds(Interface.getCenterX(150), 150, 150, 40);
        button3.addActionListener(_ -> Pages.callEvent("button3"));

        JButton button4 = new JButton("Button 4");
        button4.setBackground(lightcolor);
        button4.setForeground(darkcolor);
        button4.setBounds(Interface.getCenterX(150), 210, 150, 40);
        button4.addActionListener(_ -> Pages.callEvent("button4"));

        JButton button5 = new JButton("Button 5");
        button5.setBackground(lightcolor);
        button5.setForeground(darkcolor);
        button5.setBounds(Interface.getCenterX(150), 270, 150, 40);
        button5.addActionListener(_ -> Pages.callEvent("button5"));

        JButton button6 = new JButton("Log Out");
        button6.setBackground(lightcolor);
        button6.setForeground(darkcolor);
        button6.setBounds(Interface.getCenterX(150), 330, 150, 40);
        button6.addActionListener(_ -> Pages.callEvent("logout"));

        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(button5);
        add(button6);
    }
}
