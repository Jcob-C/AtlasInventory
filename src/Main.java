import modules.Database;
import modules.Interface;

class Main {

    public static void main(String[] args) throws Exception {
        Interface.createWindow();
        Database.checkConnection();

        System.out.println(Database.getInventoryQuantity(7));
    }
}