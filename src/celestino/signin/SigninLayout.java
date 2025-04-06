package celestino.signin;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import celestino.Main;

public class SigninLayout extends JPanel {

    public final int 
        box_Xsize = 400, box_Ysize = 400,
        title_Xsize = 150, title_Ysize = 30,
        fields_Xsize = 300, fields_Ysize = 30,
        signin_button_Xsize = 120
    ;


    public SigninLayout() {
        setLayout(null);
        setBackground(Color.BLACK);

        box.setBounds(Main.center_x(box_Xsize), Main.center_y(box_Ysize), box_Xsize, box_Ysize);
        title.setBounds(Main.center_x(title_Xsize), 175, title_Xsize, title_Ysize);
        username_field.setBounds(Main.center_x(fields_Xsize), 250, fields_Xsize, fields_Ysize);
        password_field.setBounds(Main.center_x(fields_Xsize), 325, fields_Xsize, fields_Ysize);
        signin_button.setBounds(Main.center_x(signin_button_Xsize), 400, signin_button_Xsize, 25);

        title.setFont(new Font("Segoe UI", Font.BOLD, title_Ysize));
        username_field.setFont(new Font("Segoe UI", Font.PLAIN, fields_Ysize / 2));
        password_field.setFont(new Font("Segoe UI", Font.PLAIN, fields_Ysize / 2));
        signin_button.setFont(new Font("Segoe UI", Font.BOLD, 20));

        box.setBackground(Color.WHITE);
        signin_button.setBackground(Color.WHITE);

        signin_button.addActionListener(_ -> signin_button_action());
    
        add(signin_button);
        add(password_field);
        add(username_field);
        add(title);
        add(box);
    }
    
    
    private final JLabel title = new JLabel("SIGN IN", SwingConstants.CENTER);
    private final JTextField username_field = new JTextField();
    private final JPasswordField password_field = new JPasswordField();
    private final JButton signin_button = new JButton("Submit");
    private final JPanel box = new JPanel();


    private void signin_button_action() {
        
    }
}