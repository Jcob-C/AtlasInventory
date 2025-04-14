package celestino.inventory.ui;

import celestino.Main;
import celestino.EZTable;
import celestino.inventory.InventoryMain;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class InventoryPanel extends JPanel {

    public void update_table_pane(Object[][] data) {
        ((EZTable) inventory_table).update_table(data);
    }


    public String get_search_input() {
        return search_field.getText();
    }


    public Integer get_column_sort_index() {
        return column_sort_select.getSelectedIndex();
    }


    public InventoryPanel(InventoryMain parent) {
        setLayout(null);

        JComboBox column_sort_select = new JComboBox(inventory_table_columns);
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

        dashboard_button.setFont(Main.get_font(16));
        add_button.setFont(Main.get_font(18));
        search_button.setFont(Main.get_font(18));
        search_field.setFont(Main.get_font(16));
        column_sort_select.setFont(Main.get_font(14));
        refresh_button.setFont(Main.get_font(16));

        top_bar.setBounds(0,0,880,30);
        bottom_bar.setBounds(0,630,880,30);
        ribbon_bar.setBounds(57,39,767,59);
        inventory_table_pane.setBounds(27,107,826,523);
        dashboard_button.setBounds(0,0,45,30);
        add_button.setBounds(66,49,92,40);
        search_button.setBounds(452,49,92,40);
        search_field.setBounds(196,52,257,32);
        column_sort_select.setBounds(560,52,124,32);
        refresh_button.setBounds(722,49,92,40);

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

        search_button.addActionListener(e -> parent.call_action("search"));
        column_sort_select.addActionListener(e -> parent.call_action("sort"));
        refresh_button.addActionListener(e -> parent.call_action("refresh"));
    }


    private final String[] inventory_table_columns = 
        {"ID","Barcode","Name","Type","Description","Location","Stock"}
    ;
    private final JTable inventory_table = new EZTable(inventory_table_columns);
    private final JTextField search_field = new JTextField();
    private final JComboBox column_sort_select = new JComboBox(inventory_table_columns);
}
