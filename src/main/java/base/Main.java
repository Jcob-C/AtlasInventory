package base;

import celestino.inventory.InventoryMain;
import celestino.orders.OrdersMain;
import celestino.transact.TransactMain;
import delarama.Panels;
import sandil.LoginCard;
import valmonte.dashboardPanel;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import bernabe.UserProfileMain;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    
    private static final Color theme[] = {
        new Color(252, 153, 51), // light 
        new Color(0, 80, 160), // mid 
        new Color(1, 69, 24) }; // dark
    private static final String font = "Segoe UI";

    private static final JFrame window = new JFrame();
    private static final CardLayout card_layout = new CardLayout();
    private static final JPanel main_panel = new JPanel(card_layout);

    public static final String[] inventory_columns = {"ID","Barcode","Name","Type","Description","Location","Price","Stock"};

    public static final ImageIcon 
        refresh_icon = new ImageIcon("src/main/resources/refresh.png"),
        add_icon = new ImageIcon("src/main/resources/add.png"),
        scanner_icon = new ImageIcon("src/main/resources/scanner.png");

    public static String loggedInID, userFullName, loggedInTheme = "Default";

    public static void main(String[] args) throws Exception {
        setupWindow();
        LoginCard.createModule();
        changeCard("login");
        window.setVisible(true);
        
        try(Connection conn = DB.getConnection()) {
        } catch (SQLException e) {
            e.printStackTrace();
            popupError(e.getMessage());
            System.exit(0);
        }
    }


    public static void gotoDashboard() {
        Main.changeCard("dashboard");
    }


    public static Font getFont(int size) {
        return new Font(font, Font.BOLD, size);
    }


    public static void loadAccountTheme() {
        switch(loggedInTheme) {
            case "Dark":
                theme[2] = new Color(32,32,32);
                theme[1] = new Color(64,64,64);
                theme[0] = new Color(128,128,128);
            break; 
            case "Light":
                theme[2] = new Color(128,128,128);
                theme[1] = new Color(64,64,64);
                theme[0] = new Color(255,255,255);
            break;
        }
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


    public static void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }
    
    
    private static void setupWindow() {
        window.setSize(880, 660);
        window.setTitle("AtlasInventory");
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true);
        window.setResizable(false);
        window.add(main_panel);
        window.setFocusable(true);
    }


    public static void initializeModules() {
        loadAccountTheme();

        addCard(new dashboardPanel(), "dashboard");
        addCard(new valmonte.SalesHistory.salesHistoryPanel(), "SalesHistory");

        InventoryMain.createModule();
        OrdersMain.createOrdersModule();
        TransactMain.createModule();

        Panels.createModule();
       
        UserProfileMain.createModule();
    }
}