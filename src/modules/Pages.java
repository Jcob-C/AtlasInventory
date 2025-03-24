package modules;

public class Pages {

    static final pages.Home HomePage = new pages.Home();
    static final pages.Login LoginPage = new pages.Login();
    static final pages.Inventory InventoryPage = new pages.Inventory();
    static final pages.NewItem NewItemPage = new pages.NewItem();
    static final pages.Transaction TransactionPage = new pages.Transaction();
    static final pages.ItemSelect itemSelectPage = new pages.ItemSelect();


    public static void callEvent(String action) {
        switch (action) {
            case "login": Accounts.login(); 
            break;
            case "logout": Accounts.logout(); 
            break;
            case "inventory": Inventory.gotoInventory(); 
            break;
            case "newItem": Inventory.createNewItem(); 
            break;
            case "sortInventory": Inventory.sortInventory(); 
            break;
            case "cellSelected": Inventory.cellSelected(); 
            break;
            case "itemSelect": Inventory.gotoItemSelection();
            break;
            default: System.out.println("Unmapped Action: "+action);
        }
    }


    static void createPages() {
        Interface.addPage(HomePage, "home");
        Interface.addPage(LoginPage, "login");
        Interface.addPage(InventoryPage, "inventory");
        Interface.addPage(NewItemPage, "newitem");
        Interface.addPage(TransactionPage, "transaction");
        Interface.addPage(itemSelectPage, "itemSelect");
    }
}
