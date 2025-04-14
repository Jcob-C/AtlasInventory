package celestino;

import celestino.inventory.InventoryMain;

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

    public static final String initial_card = "inventory",
        db_name = "db0321",
        db_password = "",
        db_user = "root",
        db_url = "jdbc:mysql://localhost:3306/"+db_name
    ;
    public static final Color theme[] = {
        new Color(255, 255, 255), // light color
        new Color(252, 151, 51), // medium
        new Color(1, 69, 24)  // dark
    };
    
    
    public static Connection db_connection() throws SQLException {
        return DriverManager.getConnection(db_url, db_user, db_password);
    }


    public static boolean popup_confirm(String message) {
        int choice = JOptionPane.showOptionDialog(window, message, "Confirmation", 0, 0, null, null, 1);
        return choice == 0;
    }


    public static void add_card(JPanel card, String card_name) {
        main_panel.add(card, card_name);
    }
    
    
    public static void change_card(String card_name) {
        card_layout.show(main_panel, card_name);
    }


    public static Font get_font(int size) {
        return new Font(default_font, Font.BOLD, size);
    }


    public static Object[][] insert_row(Object[][] table, Object[] new_row) {
        if (table != null && (new_row == null || new_row.length <= 0)) return table;
        else if (new_row != null && (table == null || table.length <= 0)) return new Object[][]{new_row};
        else if ((new_row == null || new_row.length <= 0) && (table == null || table.length <= 0)) return null;

        int
            old_length = table.length,
            new_length = old_length + 1
        ;
        Object new_table[][] = new Object[new_length][table[0].length];

        for (int x = 0; x < old_length; x++) {
            for (int y = 0; y < table[x].length; y++) {
                new_table[x][y] = table[x][y];
            }
        }
        for (int x = 0; !(x >= new_row.length || x >= new_table[old_length].length); x++) {
            new_table[old_length][x] = new_row[x];
        }
        
        return new_table;
    }


    private static final String default_font = "Segoe UI";
    private static final JFrame window = new JFrame();
    private static final CardLayout card_layout = new CardLayout();
    private static final JPanel main_panel = new JPanel(card_layout); 


    private static void initiliaze_cards() {
        new InventoryMain();
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


    public static void main(String[] args) throws Exception {
        setup_window();
        initiliaze_cards();
        change_card(initial_card);
        window.setVisible(true);
        db_connection();
    }
}