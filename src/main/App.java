package main;
import signin.SigninLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;

public class App {

    private static final String initial_card = "signin",
        db_name = "atlasinventory",
        db_password = "celestino04!",
        db_user = "root",
        db_url = "jdbc:mysql://localhost:3306/"+db_name
    ;


    public static Dimension window_size() {
        return window.getSize();
    }


    public static int center_x(int object_Xsize) {
        return (window.getSize().width - object_Xsize) / 2;
    }


    public static int center_y(int object_Ysize) {
        return (window.getSize().height - object_Ysize) / 2;
    }
    
    
    public static void add_card(JPanel new_panel, String card_name) {
        main_panel.add(new_panel, card_name);
    }


    public static void change_card(String card_name) {
        card_layout.show(main_panel, card_name);
    }


    public static Connection db_connection() throws Exception {
        return DriverManager.getConnection(db_url, db_user, db_password);
    }


    private static final JFrame window = new JFrame();
    private static final CardLayout card_layout = new CardLayout();
    private static final JPanel main_panel = new JPanel(card_layout); 


    private static void setup_window() {
        window.setSize(880, 660);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(main_panel);
    }


    public static void main(String[] args) throws Exception {
        setup_window();
        add_card(new SigninLayout(), "signin");
        change_card(initial_card);
        window.setVisible(true);
    }
}