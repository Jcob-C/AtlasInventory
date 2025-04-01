package jcobc.transact;
import jcobc.main.*;

public class Recorder {

    public static void recordSale(String[][] cus, String[][] inv) {
        if (cus == null && inv == null) return;

        double invPrice = Transact.totalPrice(inv) - Transact.totalPrice(cus);

        if (invPrice < 0) {
            int choice = Interface.popupOptionsChosenIndex(
                new String[]{"Confirm","Cancel"}, 
                "Inventory Price Lost: "+(invPrice*-1)
            );
            if (choice == 1) return;
        }
        
        String customerName = Interface.popupInput("Enter Customer's Name:");
        if (customerName == null) return;
        int salesID  = Database.salesInsert(
            customerName, 
            Transact.totalPrice(inv)
            - Transact.totalPrice(cus)
        );
        if (salesID == -1) return;

        if (inv != null) {
            for (int i  = 0; i < inv.length; i++) {
                Database.salesItemInsert(
                    salesID, 
                    Main.toInteger(inv[i][0]), 
                    Main.toDouble(inv[i][2]),
                    Main.toInteger(inv[i][3])
                );
            }
        }
        if (cus != null) {
            for (int i  = 0; i < cus.length; i++) {
                Database.salesItemInsert(
                    salesID, 
                    Main.toInteger(cus[i][0]), 
                    Main.toDouble(cus[i][2]),
                    Main.toInteger("-"+cus[i][4])
                );
            }
        }

        Interface.popupMessage(
            "Item Sales and Stocks Updated!\n" +
            "Receipt / Transaction ID is...\n\n" + salesID
        );

        Transact.transactionDone();
    }


    private static void calculateChange(double price) {

    }
}