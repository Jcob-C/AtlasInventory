package jcobc.history.layouts;
import jcobc.history.History;
import jcobc.main.Interface;

import javax.swing.*;

public class HistoryMenuPage extends JPanel {
    
    
    public HistoryMenuPage() {
        setLayout(null);
        setBackground(Interface.mediumColor);

        JButton button1 = new JButton("Transactions");
        button1.setBackground(Interface.darkColor);
        button1.setForeground(Interface.lightColor);
        button1.setBounds(Interface.centerX(150), 80, 150, 40);

        JButton button2 = new JButton("---");
        button2.setBackground(Interface.darkColor);
        button2.setForeground(Interface.lightColor);
        button2.setBounds(Interface.centerX(150), 140, 150, 40);       

        JButton button3 = new JButton("---");
        button3.setBackground(Interface.darkColor);
        button3.setForeground(Interface.lightColor);
        button3.setBounds(Interface.centerX(150), 200, 150, 40);     

        JButton button4 = new JButton("Back");
        button4.setBackground(Interface.darkColor);
        button4.setForeground(Interface.lightColor);
        button4.setBounds(Interface.centerX(150), 260, 150, 40);

        button4.addActionListener(_ -> History.callAction("gotoHome"));
        button1.addActionListener(_ -> History.callAction("transactions"));

        add(button1);
        add(button2);
        add(button3);
        add(button4);
    }
}