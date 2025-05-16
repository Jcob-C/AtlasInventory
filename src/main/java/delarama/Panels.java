package delarama;

import base.Main;

public class Panels {
    public static Account_Manager accountPanel = new Account_Manager();
    public static Create createPanel = new Create();
    public static AuditTrail auditTrailPanel = new AuditTrail(); 
    
    public static void allFalseVisibility() {
        accountPanel.setVisible(false);
        createPanel.setVisible(false);
        auditTrailPanel.setVisible(false);
    }

     public static void createModule() {
        Main.addCard(Panels.accountPanel, "Accounts");
        Main.addCard(Panels.createPanel, "Create");
        Main.addCard(Panels.auditTrailPanel, "Audit Trail");
    }
}