package jcobc.main;
import jcobc.main.layouts.*;
import jcobc.inventory.Inventory;
import jcobc.transact.Transact;

public class Home {

    private static String loggedinUsername = "null";

    private static final LoginPage loginPage = new LoginPage();
    private static final HomePage homePage = new HomePage();


    public static void loadPages() {
        Interface.addPage(loginPage, "login");
        Interface.addPage(homePage, "home");
    }


    public static void callAction(String action) {
        switch (action) {
            case "login": login(); 
            break;
            case "logout": logout(); 
            break;
            case "gotoHome": Interface.changePage("home");
            break;
            case "gotoInventory": Inventory.callAction("gotoInventory");
            break;
            case "gotoTransact": Transact.callAction("gotoTransact");
            break;
            default: System.out.println("Unmapped Action: "+action);
        }
    }


    public static String loggedInUsername() {
        return loggedinUsername;
    }

    
    private static void login() {
        String inputAccount[] = loginPage.getInputAccount();
        if (Database.validateAccount(inputAccount)) {
            loggedinUsername = inputAccount[0];
            loginPage.clearInputAccount();
            Interface.changePage("home");
        }
        else {
            Interface.popupMessage("Account is not Valid");
        }
    }


    private static void logout() {
        loggedinUsername = null;
        Interface.changePage("login");
    } 
}