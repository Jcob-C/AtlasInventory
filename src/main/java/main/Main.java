package main;

import celestino.inventory.InventoryMain;
import celestino.orders.OrdersMain;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static final String initial_card = "inventory";
    
    private static final Color theme[] = {
        new Color(252, 153, 51), // light (decoration)
        new Color(0, 80, 160), // mid (buttons)
        new Color(1, 69, 24) }; // dark (background)
    private static final String font = "Segoe UI";

    private static final JFrame window = new JFrame();
    private static final CardLayout card_layout = new CardLayout();
    private static final JPanel main_panel = new JPanel(card_layout);

    public static final String[] inventory_columns = {"ID","Barcode","Name","Type","Description","Location","Price","Stock"};

    
    public static void main(String[] args) throws Exception {
        setupWindow();
        initializeModules();
        changeCard(initial_card);
        window.setVisible(true);
        
        try(Connection conn = DB.getConnection()) {
        } catch (SQLException e) {
            e.printStackTrace();
            popupError(e.getMessage());
            System.exit(0);
        }
    }


    public static Font getFont(int size) {
        return new Font(font, Font.BOLD, size);
    }


    public static Color getDarkColor() {
        return theme[2];
    }


    public static Color getMidColor() {
        return theme[1];
    }


    public static Color getLightColor() {
        return theme[0];
    }


    public static boolean popupConfirm(String message) {
        return 0 == JOptionPane.showOptionDialog(window, message, "Confirmation", 0, JOptionPane.QUESTION_MESSAGE, null, null, 1);
    }


    public static int popupOption(String message, String[] options) {
        return JOptionPane.showOptionDialog(window, message, "Choose", 0, JOptionPane.QUESTION_MESSAGE, null, options, 1);
    }


    public static void popupError(String message) {
        JOptionPane.showMessageDialog(window, message, "Error Message", JOptionPane.ERROR_MESSAGE);
    }


    public static void popupMessage(String message) {
        JOptionPane.showMessageDialog(window, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }


    public static String popupInput(String message) {
        return JOptionPane.showInputDialog(message);
    }


    public static void addCard(JPanel card, String card_name) {
        main_panel.add(card, card_name);
    }


    public static void changeCard(String card_name) {
        card_layout.show(main_panel, card_name);
    }


    public static Integer toInteger(String text) {
        try {
            return Integer.parseInt(text);
        } 
        catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("\n" + text + " cannot be an Integer");
            return null;
        }
    }


    public static Double toDouble(String text) {
        try {
            return Double.parseDouble(text);
        } 
        catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("\n" + text + " cannot be a Double");
            return null;
        }
    }
    
    
    private static void setupWindow() {
        window.setSize(880, 660);
        window.setTitle("AtlasInventory");
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setUndecorated(true);
        window.add(main_panel);
        window.setFocusable(true);
    }


    private static void initializeModules() {
        InventoryMain.createModule();
        OrdersMain.createOrdersModule();
    }
}