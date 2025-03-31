import jcobc.main.Database;
import jcobc.main.Interface;

public class App {
    public static void main(String[] args) throws Exception {
        Interface.createWindow();
        Database.checkConnection();
    }
}