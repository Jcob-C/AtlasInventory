package jcobc.home.layouts;
import jcobc.home.Home;
import jcobc.main.Interface;

import javax.swing.*;

public class HomePage extends JPanel {
    
    
    public HomePage() {
        setLayout(null);
        setBackground(Interface.mediumColor);

        JButton button1 = new JButton("Inventory");
        button1.setBackground(Interface.darkColor);
        button1.setForeground(Interface.lightColor);
        button1.setBounds(Interface.centerX(150), 80, 150, 40);

        JButton button2 = new JButton("Transact");
        button2.setBackground(Interface.darkColor);
        button2.setForeground(Interface.lightColor);
        button2.setBounds(Interface.centerX(150), 140, 150, 40);       

        JButton button3 = new JButton("History");
        button3.setBackground(Interface.darkColor);
        button3.setForeground(Interface.lightColor);
        button3.setBounds(Interface.centerX(150), 200, 150, 40);     

        JButton button4 = new JButton("---");
        button4.setBackground(Interface.darkColor);
        button4.setForeground(Interface.lightColor);
        button4.setBounds(Interface.centerX(150), 260, 150, 40);        

        JButton button5 = new JButton("---");
        button5.setBackground(Interface.darkColor);
        button5.setForeground(Interface.lightColor);
        button5.setBounds(Interface.centerX(150), 320, 150, 40);      

        JButton button6 = new JButton("Log Out");
        button6.setBackground(Interface.darkColor);
        button6.setForeground(Interface.lightColor);
        button6.setBounds(Interface.centerX(150), 380, 150, 40);

        button6.addActionListener(_ -> Home.callAction("logout"));
        button1.addActionListener(_ -> Home.callAction("gotoInventory"));
        button2.addActionListener(_ -> Home.callAction("gotoTransact"));
        button3.addActionListener(_ -> Home.callAction("gotoHistoryMenu"));

        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(button5);
        add(button6);
    }
}
