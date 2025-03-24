package pages;

import modules.Pages;
import modules.Interface;
import java.awt.Color;
import javax.swing.*;

public class Transaction extends JPanel {

    private final Color darkcolor = Color.BLACK;
    private final Color lightcolor = Color.WHITE;
    
    
    public Transaction() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);
        
        JButton orderButton = new JButton("Order");
        orderButton.setBackground(lightcolor);
        orderButton.setForeground(darkcolor);
        orderButton.setBounds(Interface.getCenterX(150), 30, 150, 40);
        orderButton.addActionListener(_ -> Pages.callEvent("newOrder"));
        
        JButton sellButton = new JButton("Sell");
        sellButton.setBackground(lightcolor);
        sellButton.setForeground(darkcolor);
        sellButton.setBounds(Interface.getCenterX(150), 100, 150, 40);
        sellButton.addActionListener(_ -> Pages.callEvent("sell"));
        
        JButton refundButton = new JButton("Refund");
        refundButton.setBackground(lightcolor);
        refundButton.setForeground(darkcolor);
        refundButton.setBounds(Interface.getCenterX(150), 170, 150, 40);
        refundButton.addActionListener(_ -> Pages.callEvent("refund"));
        
        JButton swapButton = new JButton("Swap");
        swapButton.setBackground(lightcolor);
        swapButton.setForeground(darkcolor);
        swapButton.setBounds(Interface.getCenterX(150), 240, 150, 40);
        swapButton.addActionListener(_ -> Pages.callEvent("swap"));
        
        JButton backButton = new JButton("Back");
        backButton.setBackground(lightcolor);
        backButton.setForeground(darkcolor);
        backButton.setBounds(Interface.getCenterX(150), 310, 150, 40);
        backButton.addActionListener(_ -> Interface.changePage("home"));
        
        add(orderButton);
        add(sellButton);
        add(refundButton);
        add(swapButton);
        add(backButton);
    }
}
