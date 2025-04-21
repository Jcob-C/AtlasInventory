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
    private final int selected_xy[] = {-1,-1};

    
    public int[] get_selected_xy() {
        return selected_xy;
    }


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

        getSelectionModel().addListSelectionListener(e -> select_cell(e));
        getColumnModel().getSelectionModel().addListSelectionListener(e -> select_cell(e));
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


    private void select_cell(ListSelectionEvent e) {
        int 
            new_selected_row = getSelectedRow(),
            new_selected_col = getSelectedColumn();
        if (
            !e.getValueIsAdjusting() && 
            (selected_xy[0] != new_selected_row || selected_xy[1] != new_selected_col)
            ) 
        {
            selected_xy[0] = new_selected_row;
            selected_xy[1] = new_selected_col;
            action_method.accept(selected_xy);
        }
    }
}