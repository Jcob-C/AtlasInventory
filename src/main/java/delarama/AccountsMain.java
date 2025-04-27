package delarama;

import main.Main;

public class AccountsMain {
    public AccountsMain() {
        Main.add_card(Panels.accountPanel, "accounts");
        Main.add_card(Panels.auditTrailPanel, "audit trail");
        Main.add_card(Panels.createPanel, "account create");
        Main.add_card(Panels.upDeletePanel, "updelete");
    }
}
