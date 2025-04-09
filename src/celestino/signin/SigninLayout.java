package celestino.signin;
import celestino.Main;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SigninLayout extends JPanel {


    public SigninLayout() {
        setLayout(null);
        setBackground(new Color(2, 69, 24));

        top_bar.setBounds(0,0,880,30);
        bottom_bar.setBounds(0,630,880,30);
        box.setBounds(0, 119, 395, 419);
        title.setBounds(138, 153, 163, 45);
        int fields_Xsize = 215, fields_Ysize = 32;
        username_field.setBounds(157, 254, fields_Xsize, fields_Ysize);
        password_field.setBounds(157, 328, fields_Xsize, fields_Ysize);
        signin_button.setBounds(177, 417, 84, 37);
        button_x.setBounds(835,0,45,30);
        username_label.setBounds(53,260,91,21);
        password_label.setBounds(58,334,86,21);
        logo.setBounds(436,248,399,165);
        
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        username_field.setFont(new Font("Segoe UI", Font.BOLD, 16));
        password_field.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signin_button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button_x.setFont(new Font("Segoe UI", Font.BOLD, 15));
        username_label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        password_label.setFont(new Font("Segoe UI", Font.BOLD, 18));

        bottom_bar.setBackground(new Color(252, 153, 51));
        top_bar.setBackground(new Color(252, 153, 51));
        box.setBackground(new Color(252, 153, 51));
        title.setForeground(Color.WHITE);
        signin_button.setBackground(new Color(2, 69, 24));  
        signin_button.setForeground(Color.WHITE);
        button_x.setBackground(Color.RED);
        button_x.setForeground(Color.WHITE);  
        username_label.setForeground(Color.WHITE); 
        password_label.setForeground(Color.WHITE);

        signin_button.addActionListener(_ -> signin_button_action());
        button_x.addActionListener(_ -> button_x_action());
        
        add(logo);
        add(password_label);
        add(username_label);
        add(button_x);
        add(signin_button);
        add(password_field);
        add(username_field);
        add(title);
        add(box);
        add(top_bar);
        add(bottom_bar); 
    }
    
    
    private final JPanel top_bar = new JPanel();
    private final JPanel bottom_bar = new JPanel();
    private final JButton button_x = new JButton("X");
    private final JLabel title = new JLabel("SIGN IN", SwingConstants.CENTER);
    private final JTextField username_field = new JTextField();
    private final JPasswordField password_field = new JPasswordField();
    private final JButton signin_button = new JButton("Submit");
    private final JPanel box = new JPanel();
    private final JLabel username_label = new JLabel("Username:", SwingConstants.CENTER);
    private final JLabel password_label = new JLabel("Password:", SwingConstants.CENTER);
    private final JLabel logo = new JLabel(new ImageIcon("src/AtlasFeeds-399x165.png"));


    private void signin_button_action() {
        Main.change_card("dashb");
    }


    private void button_x_action() {
        if (Main.popup_confirm("Close Atlas Inventory?")) {
            System.exit(0);
        }
    }
}