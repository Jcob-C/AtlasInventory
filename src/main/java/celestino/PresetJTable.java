package celestino;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class PresetJTable extends JTable {

    private DefaultTableModel table_model;


    public PresetJTable(String[] columns) {
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


    public Integer[] get_selected_xy() {
        int row = this.getSelectedRow();
        int col = this.getSelectedColumn();
        if (row != -1 && col != -1) {
            return new Integer[] {row,col};
        }
        return null;
    }
}