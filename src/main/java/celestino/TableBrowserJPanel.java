package celestino;

import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class TableBrowserJPanel extends JPanel {
    
    private final PresetJTable preset_table;
    private final JComboBox<String> sort_column_dropdown;
    private final JTextField search_field;
    private final JButton sort_order_button;


    public String get_value_at_xy(int x, int y) {
        return String.valueOf(preset_table.getValueAt(x,y));
    }


    public Integer get_sort_column_index() {
        return sort_column_dropdown.getSelectedIndex();
    }


    public String get_search_input() {
        return search_field.getText();
    }


    public String get_sort_order() {
        String order = sort_order_button.getText();
        if (order.equals("<")) order = "DESC";
        else order = "ASC";
        return order;
    }
    
    
    public void reset_sort_n_filter() {
        search_field.setText("");
        sort_order_button.setText(">");
        sort_column_dropdown.setSelectedIndex(0);
    }
    
    
    public void update_table_pane(ArrayList<ArrayList<String>> data) {
        preset_table.update_table(data);
    }


    public void flip_sort_order() {
        switch(sort_order_button.getText()) {
            case ">": sort_order_button.setText("<"); break;
            case "<": sort_order_button.setText(">"); break;
        }
    }

    
    public TableBrowserJPanel(String[] column_names, Consumer<int[]> select_cell, Runnable goto_prev, Runnable update_jtable) {
        setLayout(null);
        
        sort_order_button = new JButton(">");
        search_field = new JTextField();
        preset_table = new PresetJTable(column_names, select_cell);
        sort_column_dropdown = new JComboBox<>(column_names);
        
        JPanel 
            top_bar = new JPanel(),
            bottom_bar = new JPanel(),
            ribbon_bar = new JPanel();
        JButton 
            back_button = new JButton("<"),
            search_button = new JButton("Search"),
            refresh_button = new JButton("Refresh");
        JScrollPane table_pane = new JScrollPane(preset_table);

        setBackground(Main.get_dark_color());
        sort_order_button.setBackground(Main.get_dark_color());
        sort_order_button.setForeground(Main.get_light_color());
        top_bar.setBackground(Main.get_mid_color());
        bottom_bar.setBackground(Main.get_mid_color());
        ribbon_bar.setBackground(Main.get_mid_color());
        back_button.setBackground(Main.get_dark_color());
        back_button.setForeground(Main.get_light_color());
        search_button.setBackground(Main.get_dark_color());
        search_button.setForeground(Main.get_light_color());
        sort_column_dropdown.setBackground(Main.get_dark_color());
        sort_column_dropdown.setForeground(Main.get_light_color());
        refresh_button.setBackground(Main.get_dark_color());
        refresh_button.setForeground(Main.get_light_color());

        search_button.setFont(Main.get_font(18));
        search_field.setFont(Main.get_font(16));
        sort_column_dropdown.setFont(Main.get_font(14));
        refresh_button.setFont(Main.get_font(16));

        sort_order_button.setBounds(556,53,45,30);
        top_bar.setBounds(0,0,880,30);
        bottom_bar.setBounds(0,630,880,30);
        ribbon_bar.setBounds(57,39,767,59);
        table_pane.setBounds(27,107,826,523);
        back_button.setBounds(0,0,45,30);
        search_button.setBounds(434,49,92,40);
        search_field.setBounds(178,52,257,32);
        sort_column_dropdown.setBounds(601,52,101,32);
        refresh_button.setBounds(722,49,92,40);

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
        refresh_button.addActionListener(e -> {reset_sort_n_filter(); update_jtable.run();});
        sort_order_button.addActionListener(e -> {flip_sort_order(); update_jtable.run();});
    }
}