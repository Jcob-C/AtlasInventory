package celestino;

import celestino.inventory.InventoryMain;
import celestino.orders.OrdersMain;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private static final String initial_card = "inventory",
        db_name = "db0321",
        db_password = "",
        db_user = "root",
        db_port = "3306",
        db_host = "localhost";
    private static final Color theme[] = {
        new Color(255, 255, 255), // light
        new Color(108, 108, 108), // mid
        new Color(40, 40, 40) }; // dark
    private static final String default_font = "Segoe UI";
    private static final JFrame window = new JFrame();
    private static final CardLayout card_layout = new CardLayout();
    private static final JPanel main_panel = new JPanel(card_layout);
    
    public static final InventoryMain inventory_module = new InventoryMain();
    public static final OrdersMain orders_module = new OrdersMain();


    public static void main(String[] args) throws Exception {
        setup_window();
        change_card(initial_card);
        window.setVisible(true);
        
        try(Connection conn = db_connection()) {
        } catch (SQLException e) {
            e.printStackTrace();
            popup_error(e.getMessage());
            System.exit(0);
        }
    }
    
    
    public static Connection db_connection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_name,
            db_user,
            db_password
        );
    }


    public static Font get_font(int size) {
        return new Font(default_font, Font.BOLD, size);
    }


    public static Color get_dark_color() {
        return theme[2];
    }


    public static Color get_mid_color() {
        return theme[1];
    }


    public static Color get_light_color() {
        return theme[0];
    }


    public static boolean popup_confirm(String message) {
        return 0 == JOptionPane.showOptionDialog(window, message, "Confirmation", 0, JOptionPane.QUESTION_MESSAGE, null, null, 1);
    }


    public static int popup_option(String message, String[] options) {
        return JOptionPane.showOptionDialog(window, message, "Choose", 0, JOptionPane.QUESTION_MESSAGE, null, options, 1);
    }


    public static void popup_error(String message) {
        JOptionPane.showMessageDialog(window, message, "Error Message", JOptionPane.ERROR_MESSAGE);
    }


    public static void popup_message(String message) {
        JOptionPane.showMessageDialog(window, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }


    public static String popup_input(String message) {
        return JOptionPane.showInputDialog(message);
    }


    public static void add_card(JPanel card, String card_name) {
        main_panel.add(card, card_name);
    }


    public static void change_card(String card_name) {
        card_layout.show(main_panel, card_name);
    }


    public static Integer to_integer(String text) {
        try {
            return Integer.parseInt(text);
        } 
        catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("\n" + text + " cannot be an Integer");
            return null;
        }
    }


    public static Double to_double(String text) {
        try {
            return Double.parseDouble(text);
        } 
        catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("\n" + text + " cannot be a Double");
            return null;
        }
    }
    
    
    private static void setup_window() {
        window.setSize(880, 660);
        window.setTitle("AtlasInventory");
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setUndecorated(true);
        window.add(main_panel);
    }
}