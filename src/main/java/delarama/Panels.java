package delarama;

public class Panels {
    public static Account_Manager accountPanel = new Account_Manager();
    public static Create createPanel = new Create();
    public static AuditTrail auditTrailPanel = new AuditTrail(); 
    
    public static void allFalseVisibility() {
        accountPanel.setVisible(false);
        createPanel.setVisible(false);
        auditTrailPanel.setVisible(false);
    }
}