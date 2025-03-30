import jcobc.main.Database;
import jcobc.main.Interface;

public class Start {
    public static void main(String[] args) throws Exception {
        Interface.createWindow();
        Database.checkConnection();
    }
}