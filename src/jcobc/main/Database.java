package jcobc.main;

import java.sql.*;
import java.math.BigDecimal;

public class Database {

    private static final String 
        urlDB = "jdbc:mysql://localhost:3306/db01",
        userDB = "root",
        passDB = "celestino04!",
        inventoryColumnNamesDB[] = {"id","barcode","itemName","itemLocation","itemType","descr","price","stock"};

    private static String inventoryCache[][] = null;


    public static String[][] inventoryCache() {
        return inventoryCache;
    }


    public static String[][] updateInventoryCache() {
        inventoryCache = table("inventory", 8);
        return inventoryCache;
    }


    public static void setInventoryCache(String[][] newTable) {
        inventoryCache = newTable;
    }


    public static void checkConnection() {
        try (Connection conn = connectionToDB()) {
            if (conn == null) {
                Interface.popupMessage("Database Connection Failed"); 
                System.exit(0);
            } 
        }
        catch (Exception e) {
            Interface.popupMessage("Database Connection Error: "+e.getMessage());
            System.exit(0);
        }
    }
    

    public static boolean validateAccount(String[] inputAccount) {
        String query = "SELECT username FROM staff WHERE username = ? AND pass = ?";
        try (
            Connection conn = connectionToDB();
            PreparedStatement queryBuild = conn.prepareStatement(query)
            ) 
        { 
            queryBuild.setString(1, inputAccount[0]);
            queryBuild.setString(2, inputAccount[1]);

            try (ResultSet result = queryBuild.executeQuery()) {
                if (result.next()) return true;
            }
        }
        catch (Exception e) { 
            Interface.popupMessage("Account Validation Error: "+e.getMessage());
        }
        return false;
    }


    public static boolean inventoryInsert(String[] newItem) {
        String query = "INSERT INTO inventory (barcode, itemName, itemLocation, itemType, descr, price, stock) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (
            Connection conn = connectionToDB();
            PreparedStatement queryBuild = conn.prepareStatement(query)
            ) 
        {
            queryBuild.setString(1, newItem[0]);  // barcode
            queryBuild.setString(2, newItem[1]);  // itemName
            queryBuild.setString(3, newItem[2]);  // itemLocation
            queryBuild.setString(4, newItem[3]);  // itemType
            queryBuild.setString(5, newItem[4]);  // descr
            queryBuild.setBigDecimal(6, new BigDecimal(newItem[5]));  // price as BigDecimal
            queryBuild.setInt(7, Integer.parseInt(newItem[6]));       // stock as int
            queryBuild.executeUpdate();
            return true;
        } 
        catch (Exception e) {
            Interface.popupMessage("Inventory Insert Error: "+e.getMessage());
        }
        return false;
    }


    public static boolean editInventoryAttribute(int id, int column, String newValue) {
        String query = "UPDATE inventory SET " + inventoryColumnNamesDB[column] + " = ? WHERE id = ?";
        try (
            Connection conn = connectionToDB();
            PreparedStatement stmt = conn.prepareStatement(query)
            ) 
        {
            switch (column) {
                case 6: stmt.setBigDecimal(1, new BigDecimal(newValue)); 
                break;
                case 7: stmt.setInt(1, Integer.parseInt(newValue)); 
                break; 
                default: stmt.setString(1, newValue); 
            }
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } 
        catch (Exception e) {
            Interface.popupMessage("Attribute Edit Error: "+e.getMessage());
        }
        return false;
    }


    public static boolean deleteRow(String table, int id) {
        String query = "DELETE FROM " + table + " WHERE id = ?";
        try (
            Connection conn = connectionToDB();
            PreparedStatement stmt = conn.prepareStatement(query)
            ) 
        {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
        catch (Exception e) {
            Interface.popupMessage("Row Deletion Error: "+e.getMessage());
        }
        return false;
    }


    public static boolean addStock(int newStock, int id) {
        String query = "UPDATE inventory SET stock = stock + ? WHERE id = ?";
        try (
            Connection conn = connectionToDB();
            PreparedStatement stmt = conn.prepareStatement(query)
            ) 
        {
            stmt.setInt(1, newStock);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        }
        catch (Exception e) {
            Interface.popupMessage("Stock Addition Error: "+e.getMessage());
        }
        return false;
    }


    private static Connection connectionToDB() throws Exception {
        return DriverManager.getConnection(urlDB, userDB, passDB);
    }


    private static int tableSize(String table) {
        int tableSize = 0;
        String query = "SELECT COUNT(*) FROM "+table;
        try (
            Connection conn = connectionToDB();
            Statement queryBuild = conn.createStatement();
            ResultSet result = queryBuild.executeQuery(query);
            ) 
        {
            if (result.next()) tableSize = result.getInt(1);
        }
        catch (Exception e) { 
            Interface.popupMessage("Get Table Size Error: "+e.getMessage());
        }
        return tableSize;
    }


    private static String[][] table(String table, int columns) {
        String query = "SELECT * FROM "+table,
        outputTable[][] = new String[tableSize(table)][columns];
        try (
            Connection conn = connectionToDB();
            Statement queryBuild = conn.createStatement();
            ResultSet result = queryBuild.executeQuery(query);
            ) 
        {
            int o = 0;
            while (result.next()) {
                for (int i = 0; i < columns; i++) {
                    outputTable[o][i] = result.getString(i+1);
                }
                o++;
            }
        }
        catch (Exception e) { 
            Interface.popupMessage("Get Whole Table Error: "+e.getMessage());
        }
        return outputTable;
    }
}