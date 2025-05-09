package celestino;

import main.Main;

import javax.swing.*;

import celestino.inventory.InventoryMain;

import java.awt.Color;
import java.awt.event.*;
import java.util.function.Consumer;

public class ScannerJPanel extends JPanel implements KeyListener {

    private StringBuilder inputBuffer = new StringBuilder();
    private Consumer<String> receiver;
    private JLabel input_label = new JLabel("Scanned: ");


    public ScannerJPanel(Consumer<String> receiver) {
        this.receiver = receiver;
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        setLayout(null);
        setBackground(Main.getDarkColor());

        JPanel 
            top_bar = new JPanel(),
            bottom_bar = new JPanel();
        JButton 
            back_button = new JButton("<"),
            reset_scan = new JButton(new ImageIcon("src/main/resources/refresh.png"));
        JLabel scanning_label = new JLabel("Waiting for Scan");


        input_label.setFont(Main.getFont(33));
        scanning_label.setFont(Main.getFont(33));

        input_label.setForeground(Color.WHITE);
        scanning_label.setForeground(Color.WHITE);
        top_bar.setBackground(Main.getLightColor());
        bottom_bar.setBackground(Main.getLightColor());
        back_button.setBackground(Main.getMidColor());
        back_button.setForeground(Color.WHITE);
        reset_scan.setBackground(Main.getMidColor());

        reset_scan.setBounds(95,107,40,40);
        input_label.setBounds(150,102,730,44);
        scanning_label.setBounds(310,300,400,44);
        top_bar.setBounds(0,0,880,30);
        bottom_bar.setBounds(0,630,880,30);
        back_button.setBounds(0,0,45,30);

        add(reset_scan);
        add(scanning_label);
        add(input_label);
        add(back_button);
        add(top_bar);
        add(bottom_bar);

        reset_scan.addActionListener(e -> clearBuffer());
        back_button.addActionListener(e -> InventoryMain.gotoInventory());
    }


    public void clearBuffer() {
        inputBuffer.setLength(0);
        input_label.setText("Scanned: ");
        requestFocusInWindow();
    }


    @Override
    public void keyTyped(KeyEvent e) {
        char ch = e.getKeyChar();
        if ((ch == '\n' || ch == '\r') && !inputBuffer.toString().equals("")) {
            onEnterPressed(inputBuffer.toString());
            clearBuffer();
        } else {
            inputBuffer.append(ch);
            input_label.setText("Scanned: " + inputBuffer.toString());
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {

    }

    
    @Override
    public void keyReleased(KeyEvent e) {

    }


    private void onEnterPressed(String input) {
        receiver.accept(input);
    }
}