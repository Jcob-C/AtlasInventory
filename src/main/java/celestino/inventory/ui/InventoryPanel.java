package celestino.inventory.ui;

import celestino.Main;
import celestino.PresetJTable;
import celestino.inventory.InventoryMain;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class InventoryPanel extends JPanel {

    public void update_table_pane(ArrayList<ArrayList<String>> data) {
        ((PresetJTable) inventory_table).update_table(data);
    }
    

    public void flip_sort_order() {
        switch(sort_order_button.getText()) {
            case ">": sort_order_button.setText("<");
            break;
            case "<": sort_order_button.setText(">");
            break;
        }
    }
    
    
    public Integer get_sort_column_index() {
        return column_sort_select.getSelectedIndex();
    }


    public String get_id_at_row(int x) {
        return String.valueOf(inventory_table.getValueAt(x,0));
    }


    public Integer[] get_selected_xy() {
        return new Integer[] {selected_row, selected_col};
    }


    public String get_value_at_xy(int x, int y) {
        return String.valueOf(inventory_table.getValueAt(x,y));
    }


    public String get_search_input() {
        return search_field.getText();
    }
    
    
    public String get_sort_order() {
        return sort_order_button.getText();
    }
    
    
    public void reset_search_field() {
        search_field.setText("");
    }


    public void reset_sort_order() {
        sort_order_button.setText(">");
    }
    
    
    public void reset_sort_column() {
        column_sort_select.setSelectedIndex(0);
    }


    public InventoryPanel(InventoryMain parent) {
        setLayout(null);

        JScrollPane inventory_table_pane = new JScrollPane(inventory_table);
        JPanel 
            top_bar = new JPanel(),
            bottom_bar = new JPanel(),
            ribbon_bar = new JPanel()
        ;
        JButton 
            dashboard_button = new JButton("<"),
            add_button = new JButton("New"),
            search_button = new JButton("Search"),
            refresh_button = new JButton("Refresh")
        ;

        setBackground(Main.theme[2]);
        sort_order_button.setBackground(Main.theme[2]);
        sort_order_button.setForeground(Main.theme[0]);
        top_bar.setBackground(Main.theme[1]);
        bottom_bar.setBackground(Main.theme[1]);
        ribbon_bar.setBackground(Main.theme[1]);
        dashboard_button.setBackground(Main.theme[2]);
        dashboard_button.setForeground(Main.theme[0]);
        add_button.setBackground(Main.theme[2]);
        add_button.setForeground(Main.theme[0]);
        search_button.setBackground(Main.theme[2]);
        search_button.setForeground(Main.theme[0]);
        column_sort_select.setBackground(Main.theme[2]);
        column_sort_select.setForeground(Main.theme[0]);
        refresh_button.setBackground(Main.theme[2]);
        refresh_button.setForeground(Main.theme[0]);

        add_button.setFont(Main.get_font(18));
        search_button.setFont(Main.get_font(18));
        search_field.setFont(Main.get_font(16));
        column_sort_select.setFont(Main.get_font(14));
        refresh_button.setFont(Main.get_font(16));

        sort_order_button.setBounds(556,53,45,30);
        top_bar.setBounds(0,0,880,30);
        bottom_bar.setBounds(0,630,880,30);
        ribbon_bar.setBounds(57,39,767,59);
        inventory_table_pane.setBounds(27,107,826,523);
        dashboard_button.setBounds(0,0,45,30);
        add_button.setBounds(66,49,92,40);
        search_button.setBounds(434,49,92,40);
        search_field.setBounds(178,52,257,32);
        column_sort_select.setBounds(601,52,101,32);
        refresh_button.setBounds(722,49,92,40);

        add(sort_order_button);
        add(dashboard_button);
        add(add_button);
        add(search_button);
        add(search_field);
        add(column_sort_select);
        add(refresh_button);
        add(top_bar);
        add(bottom_bar);
        add(ribbon_bar);
        add(inventory_table_pane);

        sort_order_button.addActionListener(e -> parent.call_action("order sort"));
        search_button.addActionListener(e -> parent.call_action("search"));
        search_field.addActionListener(e -> parent.call_action("search"));
        column_sort_select.addActionListener(e -> parent.call_action("sort"));
        refresh_button.addActionListener(e -> parent.call_action("refresh"));
        add_button.addActionListener(e -> parent.call_action("new item"));
        inventory_table.getSelectionModel().addListSelectionListener
        (e -> {
                if (
                    !e.getValueIsAdjusting() && 
                    selected_row != inventory_table.getSelectedRow()
                    ) 
                {
                    update_selected();
                    parent.call_action("select cell");
                }
            }
        );
        inventory_table.getColumnModel().getSelectionModel().addListSelectionListener
        (e -> {
                if (!e.getValueIsAdjusting() && 
                    selected_col != inventory_table.getSelectedColumn()
                    ) 
                {
                    update_selected();
                    parent.call_action("select cell");
                }
            }
        );
    }


    private final String[] inventory_table_columns = 
        {"ID","Barcode","Name","Type","Description","Location","Price","Stock"}
    ;
    private final JTable inventory_table = new PresetJTable(inventory_table_columns);
    private final JTextField search_field = new JTextField();
    private final JComboBox<String> column_sort_select = new JComboBox<>(inventory_table_columns);
    private final JButton sort_order_button = new JButton(">");
    private Integer 
        selected_row = -1,
        selected_col = -1;


    private void update_selected() {
        selected_row = inventory_table.getSelectedRow();
        selected_col = inventory_table.getSelectedColumn();
    }
}
