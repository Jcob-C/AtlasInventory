package celestino.dashb;
import celestino.Main;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;

public class DashbLayout extends JPanel{
    

    public DashbLayout() {
        setLayout(null);
        setBackground(new Color(2, 69, 24));

        top_bar.setBounds(0,0,880,30);
        bottom_bar.setBounds(0,630,880,30);
        button_x.setBounds(835,0,45,30);

        button_x.setFont(new Font("Segoe UI", Font.BOLD, 15));

        bottom_bar.setBackground(new Color(252, 153, 51));
        top_bar.setBackground(new Color(252, 153, 51));
        button_x.setBackground(Color.RED);
        button_x.setForeground(Color.WHITE); 

        button_x.addActionListener(_ -> button_x_action());

        add(button_x);
        add(bottom_bar);
        add(top_bar);
    }


    private final JPanel top_bar = new JPanel();
    private final JPanel bottom_bar = new JPanel();
    private final JButton button_x = new JButton("X");


    private void button_x_action() {
        if (Main.popup_confirm("Close Atlas Inventory?")) {
            System.exit(0);
        }
    }
}