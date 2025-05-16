package delarama;
import base.Main;

public class MainCard {
    public static void createModule() {
    Main.addCard(Panels.accountPanel, "Accounts");
    Main.addCard(Panels.createPanel, "Create");
    Main.addCard(Panels.auditTrailPanel, "Audit Trail");
    }
}