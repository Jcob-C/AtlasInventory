package celestino;

import main.Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class TableBrowserJPanel extends JPanel {
    
    private final PresetJTable preset_table;
    private final JComboBox<String> sort_column_dropdown;
    private final JTextField search_field;
    private final JButton sort_order_button;


    public String getValueAt(int x, int y) {
        return String.valueOf(preset_table.getValueAt(x,y));
    }


    public int getSortColumnIndex() {
        return sort_column_dropdown.getSelectedIndex();
    }


    public String getSearchInput() {
        return search_field.getText();
    }


    public String getSortOrder() {
        return sort_order_button.getText();
    }
    
    
    public void resetSortFilter() {
        search_field.setText("");
        sort_order_button.setText("ASC");
        sort_column_dropdown.setSelectedIndex(0);
    }
    
    
    public void updateTable(ArrayList<ArrayList<String>> data) {
        preset_table.updateTable(data);
    }


    public void flipSortOrder() {
        switch(sort_order_button.getText()) {
            case "ASC": sort_order_button.setText("DESC"); break;
            case "DESC": sort_order_button.setText("ASC"); break;
        }
    }


    public void setTitle(String title) {
        JLabel title_label = new JLabel(title);
        title_label.setFont(Main.getFont(33));
        title_label.setForeground(Color.WHITE);
        title_label.setBounds(100,102,780,44);
        add(title_label);
        this.setComponentZOrder(title_label, 1);
    }

    
    public TableBrowserJPanel
        (
        String[] column_names, 
        Consumer<int[]> select_cell, 
        Runnable goto_prev, 
        Runnable update_jtable, 
        Runnable refresh 
        ) 
    {
        setLayout(null);
        
        sort_order_button = new JButton("ASC");
        search_field = new JTextField();
        preset_table = new PresetJTable(column_names, select_cell);
        sort_column_dropdown = new JComboBox<>(column_names);
        
        JPanel 
            top_bar = new JPanel(),
            bottom_bar = new JPanel(),
            ribbon_bar = new JPanel();
        JButton 
            back_button = new JButton("<"),
            search_button = new JButton(new ImageIcon("src/main/resources/search.png")),
            refresh_button = new JButton(new ImageIcon("src/main/resources/refresh.png"));
        JScrollPane table_pane = new JScrollPane(preset_table);

        setBackground(Main.getDarkColor());
        sort_order_button.setBackground(Main.getMidColor());
        sort_order_button.setForeground(Color.WHITE);
        top_bar.setBackground(Main.getLightColor());
        bottom_bar.setBackground(Main.getLightColor());
        ribbon_bar.setBackground(Main.getLightColor());
        back_button.setBackground(Main.getMidColor());
        back_button.setForeground(Color.WHITE);
        search_button.setBackground(Main.getMidColor());
        sort_column_dropdown.setBackground(Main.getMidColor());
        sort_column_dropdown.setForeground(Color.WHITE);
        refresh_button.setBackground(Main.getMidColor());

        sort_order_button.setFont(Main.getFont(10));
        search_field.setFont(Main.getFont(16));
        sort_column_dropdown.setFont(Main.getFont(14));

        sort_order_button.setBounds(593,32,60,40);
        top_bar.setBounds(0,0,880,30);
        bottom_bar.setBounds(0,630,880,30);
        ribbon_bar.setBounds(228,0,596,88);
        table_pane.setBounds(29,156,826,474);
        back_button.setBounds(0,0,45,30);
        search_button.setBounds(533,32,40,40);
        search_field.setBounds(240,36,294,32);
        sort_column_dropdown.setBounds(652,36,101,32);
        refresh_button.setBounds(774,32,40,40);

        add(sort_order_button);
        add(back_button);
        add(search_button);
        add(search_field);
        add(sort_column_dropdown);
        add(refresh_button);
        add(top_bar);
        add(bottom_bar);
        add(ribbon_bar);
        add(table_pane);

        back_button.addActionListener(e -> goto_prev.run());
        search_field.addActionListener(e -> update_jtable.run());
        search_button.addActionListener(e -> update_jtable.run());
        sort_column_dropdown.addActionListener(e -> update_jtable.run());
        refresh_button.addActionListener(e -> refresh.run());
        sort_order_button.addActionListener(e -> {flipSortOrder(); update_jtable.run();});
    }
}