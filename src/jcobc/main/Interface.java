package jcobc.main;

import jcobc.home.Home;
import jcobc.inventory.Inventory;
import jcobc.transact.Transact;
import jcobc.history.History;

import javax.swing.*;
import java.awt.*;

public class Interface {
    
    public static final Color lightColor = Color.WHITE;
    public static final Color mediumColor = Color.DARK_GRAY;
    public static final Color darkColor = Color.BLACK;

    private static final String initialPage = "login";

    private static final JFrame frame = new JFrame();
    private static final CardLayout cardLayout = new CardLayout();
    private static final JPanel mainPanel = new JPanel(cardLayout);

    private static String prevPage = null; 
    private static String currentPage = null;


    public static void createWindow() {
        frame.add(mainPanel);
        frame.setTitle("Inventory System");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        loadAllPages();
        changePage(initialPage);
        frame.setVisible(true);
    }


    public static void goPrevPage() {
        String pageHolder = currentPage;
        currentPage = prevPage;
        prevPage = pageHolder;
        cardLayout.show(mainPanel, currentPage);
    }


    public static void changePage(String name) {
        prevPage = currentPage;
        currentPage = name;
        cardLayout.show(mainPanel, name);
    }
    

    public static void addPage(JPanel newPage, String name) {
        mainPanel.add(newPage, name);
    }


    public static int centerX(int xSize) {
        return ((frame.getWidth() - xSize) / 2)-8;
    }


    public static int popupOptionsChosenIndex(String[] options, String message) {
        return JOptionPane.showOptionDialog(
            null,                       
            message,        
            frame.getTitle(),              
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,  
            null,                          
            options,                       
            options[0]                     
        );
    }


    public static void popupMessage(String message) {
        JOptionPane.showMessageDialog(null, message, frame.getTitle(), JOptionPane.INFORMATION_MESSAGE);
    }


    public static String popupInput(String message) {
        return JOptionPane.showInputDialog(message);
    }


    public static Integer popupIntegerInput(String message) {
        return Main.toInteger(JOptionPane.showInputDialog(message));
    }


    public static Double popupDoubleInput(String message) {
        return Main.toDouble(JOptionPane.showInputDialog(message));
    }


    private static void loadAllPages() {
        Inventory.loadPages();
        Home.loadPages();
        Transact.loadPages();
        History.loadPages();
    }
}