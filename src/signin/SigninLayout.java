package signin;
import main.App;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SigninLayout extends JPanel {

    private static final JLabel title = new JLabel("SIGN IN");
    private static final JTextField username_field = new JTextField();
    private static final JPasswordField password_field = new JPasswordField();
    private static final JButton signin_button = new JButton("Submit");


    public SigninLayout() {
        setLayout(null);
        title.setBounds(App.center_x(100),App.center_y(20),100,20);
        add(title);
    }
}