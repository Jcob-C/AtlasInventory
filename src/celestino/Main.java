package celestino;
import celestino.signin.SigninLayout;
import celestino.dashb.DashbLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

    public static final String initial_card = "signin",
        db_name = "atlasinventory",
        db_password = "celestino04!",
        db_user = "root",
        db_url = "jdbc:mysql://localhost:3306/"+db_name
    ;
    
    
    public static Connection db_connection() throws Exception {
        return DriverManager.getConnection(db_url, db_user, db_password);
    }


    public static int center_x(int object_Xsize) {
        return ((window.getSize().width - object_Xsize) / 2) - 8;
    }


    public static int center_y(int object_Ysize) {
        return ((window.getSize().height - object_Ysize) / 2) - 20;
    }


    public static boolean popup_confirm(String message) {
        int choice = JOptionPane.showOptionDialog(window, message, "Confirmation", 0, 0, null, null, 1);
        return choice == 0;
    }
    
    
    public static void change_card(String card_name) {
        card_layout.show(main_panel, card_name);
    }


    private static final JFrame window = new JFrame();
    private static final CardLayout card_layout = new CardLayout();
    private static final JPanel main_panel = new JPanel(card_layout); 


    private static void initiliaze_cards() {
        main_panel.add(new SigninLayout(), "signin");
        main_panel.add(new DashbLayout(), "dashboard");
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
    }
}