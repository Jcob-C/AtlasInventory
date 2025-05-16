package delarama;

import java.sql.*;

public class UserActivity {
    private static int id = 1;
    private static String fullName = "Admin";

    public static void setId(int userId) {
        id = userId;
    }

    public static void setFullName(String name) {
        fullName = name;
    }

    public static int getId() {
        return id;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void Audit_Trail(String action) {
        try (Connection conn = DBC.getConnection()) {
            
            PreparedStatement insert = conn.prepareStatement("INSERT INTO Activity (ID, Full_Name, Activity) VALUES (?, ?, ?)");

            insert.setInt(1, getId());
            insert.setString(2, getFullName());
            insert.setString(3, action);

            insert.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}