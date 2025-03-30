package jcobc.main;

public class Main {

    
    public static boolean checkIfNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception _) {
            return false;
        }
    }


    public static Integer toInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception _) {
            return null;
        }
    }


    public static Double toDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception _) {
            return null;
        }
    }


    public static String[][] withNewRow(String[][] table, String[] newRow) {
        if (table == null || table.length == 0) {
            return new String[][]{newRow};
        }
        else if (newRow.length > table[0].length) {
            Interface.popupMessage("withNewRow() failed: newRow.length > table[0].length");
            return table;
        }
        String newTable[][] = new String[table.length+1][table[0].length];
        for (int i = 0; i < table.length; i++) {
            for (int o = 0; o < table[i].length; o++) {
                newTable[i][o] = table[i][o];
            }
        }
        for (int i = 0; i < newRow.length; i++) {
            newTable[table.length][i] = newRow[i];
        }
        return newTable;
    }


    public static String[][] withoutRow(String[][] table, int rowIndex) {
        if (rowIndex < 0 || table.length <= rowIndex) {
            Interface.popupMessage("withoutRow() failed: rowIndex < 0 || table.length <= rowIndex");
            return null;
        }
        else if (table.length <= 1) {
            return null;
        }
        String newTable[][] = new String[table.length-1][table[0].length];
        int offset = 0;
        for (int i = 0; i < newTable.length; i++) {
            if (i == rowIndex) {
                offset = 1;
            }
            for (int o = 0; o < table[0].length; o++) {
                newTable[i][o] = table[i + offset][o];
            }
        }
        return newTable;
    }


    public static String[][] numSorted(String[][] table, int columnIndex, boolean ascending) {
        if (table == null || table.length == 0 || columnIndex < 0 || columnIndex >= table[0].length) {
            Interface.popupMessage("numSorted() failed: table == null || table.length == 0 || columnIndex < 0 || columnIndex >= table[0].length");
            return null;
        }
        String 
            holder[] = null,
            newTable[][] = copyTable(table);
        boolean unsorted = true;
        while (unsorted) {
            unsorted = false;
            for (int i = 1; i < table.length; i++) {
                Double 
                    prev = toDouble(newTable[i - 1][columnIndex]),
                    curr = toDouble(newTable[i][columnIndex]);
                if ((prev > curr && ascending) || (prev < curr && !ascending)) {
                    holder = newTable[i - 1];
                    newTable[i - 1] = newTable[i];
                    newTable[i] = holder;
                    unsorted = true;
                }
            }
         }
        return newTable;
    }


    public static String[][] strSorted(String[][] table, int columnIndex, boolean ascending) {
        if (table == null || table.length == 0 || columnIndex < 0 || columnIndex >= table[0].length) {
            Interface.popupMessage("strSorted() failed: table == null || table.length == 0 || columnIndex < 0 || columnIndex >= table[0].length");
            return null;
        }
        String 
            holder[] = null,
            newTable[][] = copyTable(table);
        boolean unsorted = true;
        while (unsorted) {
            unsorted = false;
            for (int i = 1; i < table.length; i++) {
                int comparison = newTable[i-1][columnIndex].compareToIgnoreCase(newTable[i][columnIndex]);
                if ((comparison > 0 && ascending) || (comparison < 0 && !ascending)) {
                    holder = newTable[i - 1];
                    newTable[i - 1] = newTable[i];
                    newTable[i] = holder;
                    unsorted = true;
                }
            }
         }
        return newTable;
    }


    public static String[][] copyTable(String[][] table) {
        String newTable[][] = new String[table.length][table[0].length];
        for (int i = 0; i < table.length; i++) {
            for (int o = 0; o < table[i].length; o++) {
                newTable[i][o] = table[i][o];
            }
        }
        return newTable;
    }
}