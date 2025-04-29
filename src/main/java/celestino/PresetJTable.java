package celestino;

import java.util.ArrayList;
import java.util.function.Consumer;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

public class PresetJTable extends JTable {

    private final DefaultTableModel table_model;
    private final Consumer<int[]> action_method;
    private int
        selected_x = -1,
        selected_y = -1
    ;


    public PresetJTable(String[] columns, Consumer<int[]> action_method) {
        this.action_method = action_method;
        table_model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.setModel(table_model);
        this.setCellSelectionEnabled(true);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.getTableHeader().setReorderingAllowed(false);
        this.getColumnModel().getColumn(0).setPreferredWidth(10);

        getSelectionModel().addListSelectionListener(e -> selected_a_cell(e));
        getColumnModel().getSelectionModel().addListSelectionListener(e -> selected_a_cell(e));
    }

    
    public void update_table(ArrayList<ArrayList<String>> data) {
        table_model.setRowCount(0);
        for (int x = 0; x < data.size(); x++) {
            String[] new_row = new String[data.get(x).size()];

            for (int y = 0; y < new_row.length; y++) {
                new_row[y] = data.get(x).get(y);
            }

            table_model.addRow(new_row);
        }
    }


    public void add_row(String[] row) { table_model.addRow(row); }
    public void add_row(Object[] row) { table_model.addRow(row); }
    public void delete_row(int index) { table_model.removeRow(index); }


    private void selected_a_cell(ListSelectionEvent e) {
        int 
            new_selected_row = getSelectedRow(),
            new_selected_col = getSelectedColumn();
        if (
            !e.getValueIsAdjusting() && 
            (selected_x != new_selected_row || selected_y != new_selected_col)
            ) 
        {
            selected_x = new_selected_row;
            selected_y = new_selected_col;
            action_method.accept(new int[]{selected_x, selected_y});
        }
    }
}