package bernabe;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import delarama.AuditTrail;
import base.DB;
import base.Main;

public class UserProfileMain {

    private static final String[] column_names = {"ID","Username","Acc_Password","User_Role","Full_Name","Email","Contact_no","Address","Theme"};
    
    public static void createModule() {
        Main.addCard(UserProfilePage.createPanel(), "user settings");
    }


    public static void gotoUserSettings() {
        UserProfilePage.setFields(getAccount(Main.loggedInID));
        Main.changeCard("user settings");
    }


    public static void saveAccountChanges() {
        String[] fields = UserProfilePage.getFields();

        edit(Main.loggedInID, 1, fields[0]);
        edit(Main.loggedInID, 2, fields[1]);
        edit(Main.loggedInID, 4, fields[2]);
        edit(Main.loggedInID, 5, fields[3]);
        edit(Main.loggedInID, 6, fields[4]);
        edit(Main.loggedInID, 7, fields[5]);

        AuditTrail.Audit_Trail("Accounts of " + fields[2] + " has been updated");
        Main.popupMessage("Account Updated!");
    }


    public static void changeAppTheme(Object theme) {
        saveTheme(String.valueOf(theme));
        if (Main.popupConfirm("Theme change requires a restart\n\nClose app now?")) {
            System.exit(0);
        }
        else {
            Main.popupMessage("Theme will be applied on next startup");
        }
    }


    public static void saveTheme(String new_theme) {
        String value = new_theme;
        try {
            FileWriter writer = new FileWriter("theme.txt");
            writer.write(value);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<String> getAccount(String id) {
        ArrayList<String> account = new ArrayList<>();
        try (
            Connection conn = DB.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM accounts WHERE ID = "+id+";");
            ) 
        {
            if (rs.next()) {
                account.add(rs.getString(2));
                account.add(rs.getString(3));
                account.add(rs.getString(5));
                account.add(rs.getString(6));
                account.add(rs.getString(7));
                account.add(rs.getString(8));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return account;
    }


    static boolean edit(String id, int column, String new_value) {
        String edit_query = "UPDATE Accounts SET " + column_names[column] + " = ? WHERE ID = " + id + ";";
        try (
            Connection conn = DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(edit_query) 
            ) 
        {
            stmt.setString(1, new_value);
            if (stmt.executeUpdate() > 0) return true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }
}
