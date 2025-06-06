package celestino;

import java.util.ArrayList;
import java.util.function.Consumer;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import base.Main;

public class PresetJTable extends JTable {
    
    private final DefaultTableModel table_model;
    private final Consumer<int[]> action_method;
    private int
        selected_x = -1,
        selected_y = -1;


    public PresetJTable(String[] columns, Consumer<int[]> action_method) {
        this.action_method = action_method;
        table_model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(table_model);
        setCellSelectionEnabled(true);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getTableHeader().setReorderingAllowed(false);
        getColumnModel().getColumn(0).setPreferredWidth(30);
        setFont(Main.getFont(12));
        setRowHeight(24);

        getSelectionModel().addListSelectionListener(e -> cellSelected(e));
        getColumnModel().getSelectionModel().addListSelectionListener(e ->cellSelected(e));
    }


    public ArrayList<ArrayList<String>> getTable() {
        ArrayList<ArrayList<String>> tableData = new ArrayList<>();
        for (int row = 0; row < getRowCount(); row++) {
            ArrayList<String> rowData = new ArrayList<>();
            for (int col = 0; col < getColumnCount(); col++) {
                rowData.add(String.valueOf(getValueAt(row, col)));
            }
            tableData.add(rowData);
        }
        return tableData;
    }

    
    public void updateTable(ArrayList<ArrayList<String>> data) {
        table_model.setRowCount(0);
        if (data == null) return;
        for (int x = 0; x < data.size(); x++) {
            String[] new_row = new String[data.get(x).size()];

            for (int y = 0; y < new_row.length; y++) {
                new_row[y] = data.get(x).get(y);
            }

            table_model.addRow(new_row);
        }
    }


    public void addRow(String[] row) { table_model.addRow(row); }
    public void addRow(Object[] row) { table_model.addRow(row); }
    public void deleteRow(int index) { table_model.removeRow(index); }


    private void cellSelected(ListSelectionEvent e) {
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
            if (selected_x == -1 || selected_y == -1) return;
            action_method.accept(new int[]{selected_x, selected_y});
        }
    }
}