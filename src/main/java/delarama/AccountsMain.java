package delarama;

import main.Main;

public class AccountsMain {
    public static void addAccountsModule() {
        Main.addCard(Panels.accountPanel, "accounts");
        Main.addCard(Panels.auditTrailPanel, "audit trail");
        Main.addCard(Panels.createPanel, "account create");
        Main.addCard(Panels.upDeletePanel, "updelete");
    }
}
