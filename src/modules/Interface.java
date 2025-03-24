package modules;

import javax.swing.*;
import java.awt.*;

public class Interface {

    private static final String initialPage = "login";

    private static final JFrame frame = new JFrame();
    private static final CardLayout cardLayout = new CardLayout();
    private static final JPanel mainPanel = new JPanel(cardLayout);

    private static String prevPage = null; 
    private static String currentPage = null;

    public static void createWindow() {
        frame.add(mainPanel);
        frame.setTitle("Inventory System");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);
        Pages.createPages();
        changePage(initialPage);
        frame.setVisible(true);
    }


    public static void goPrevPage() {
        String pageHolder = currentPage;
        currentPage = prevPage;
        prevPage = pageHolder;
        cardLayout.show(mainPanel, currentPage);
    }


    public static void changePage(String name) {
        prevPage = currentPage;
        currentPage = name;
        cardLayout.show(mainPanel, name);
    }


    public static int getCenterX(int xSize) {
        return ((frame.getWidth() - xSize) / 2)-8;
    }

    
    public static ImageIcon scaleImageIcon(String file, double multiplier) {
        ImageIcon image = new ImageIcon(file);
        int
        newWidth = (int) (image.getIconWidth() * multiplier),
        newHeight = (int) (image.getIconHeight() * multiplier);
        Image newImage = image.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }


    static int popupOptions(String[] options, String message) {
        return JOptionPane.showOptionDialog(
            null,                       
            message,        
            frame.getTitle(),              
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,  
            null,                          
            options,                       
            options[0]                     
        );
    }


    static void popupMessage(String message) {
        JOptionPane.showMessageDialog(null, message, frame.getTitle(), JOptionPane.INFORMATION_MESSAGE);
    }


    static String popupInput(String message) {
        return JOptionPane.showInputDialog(message);
    }


    static void addPage(JPanel newPage, String name) {
        mainPanel.add(newPage, name);
    }
}