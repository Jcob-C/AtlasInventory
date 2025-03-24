package modules;

public class Accounts {

    @SuppressWarnings("unused")
    private static String loggedinUsername = "null";

    
    static void login() {
        String inputAccount[] = Pages.LoginPage.getInputAccount();
        if (Database.validateAccount(inputAccount)) {
            loggedinUsername = inputAccount[0];
            Pages.LoginPage.clearInputAccount();
            Interface.changePage("home");
        }
        else {
            Interface.popupMessage("Account is not Valid");
        }
    }


    static void logout() {
        loggedinUsername = null;
        Interface.changePage("login");
    } 
    

    static String getLoggedInUser() {
        return loggedinUsername;
    }
}