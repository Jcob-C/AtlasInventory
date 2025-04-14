package celestino;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class EZTable extends JTable {

    private DefaultTableModel inventory_table_model;


    public EZTable(String[] columns) {
        inventory_table_model = table_model(columns);
        this.setModel(inventory_table_model);
        this.setCellSelectionEnabled(true);
        this.getTableHeader().setReorderingAllowed(false);
    }

    
    public void update_table(ArrayList<ArrayList<String>> data) {
        inventory_table_model.setRowCount(0);
        for (int x = 0; x < data.size(); x++) {
            String[] new_row = new String[data.get(x).size()];

            for (int y = 0; y < new_row.length; y++) {
                new_row[y] = data.get(x).get(y);
            }

            inventory_table_model.addRow(new_row);
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


    private DefaultTableModel table_model(Object[] columns) {
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return model;
    }
}