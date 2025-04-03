package jcobc.transact;
import jcobc.main.*;

public class Recorder {

    public static void recordSale(String[][] cus, String[][] inv) {
        if (cus == null && inv == null) return;
        if (!enoughStock(inv)) return;

        double invPrice = Transact.totalPrice(inv) - Transact.totalPrice(cus);
        if (invPrice < 0) {
          int choice = Interface.popupOptionsChosenIndex(
                new String[]{"Confirm","Cancel"}, 
                "Inventory Price Lost: "+(invPrice*-1)
            );
            if (choice == 1) return;
        }
        else if (invPrice > 0) {
            calculateChange(invPrice);
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
                Database.addStock(-(Main.toInteger(inv[i][3])), Main.toInteger(inv[i][0]));
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
                if (cus[i][3].equals("OK")) {
                    Database.addStock(Main.toInteger(cus[i][4]), Main.toInteger(cus[i][0]));
                }
            }
        }

        Interface.popupMessage(
            "Item Sales and Stocks Updated!\n" +
            "Receipt / Transaction ID is:\n\n" + salesID
        );

        Transact.transactionDone();
    }


    private static boolean enoughStock(String[][] inv) {
        if (inv == null) return true;
        boolean output = true;
        for (String[] x : inv) {
            int currStock = Database.getStock(Main.toInteger(x[0]));
            if (currStock < Main.toInteger(x[3])) {
                Interface.popupMessage(
                    "\nAvailable Stock: "+currStock+
                    "\nInsufficient Stock for item:\n\nID: "+x[0]+"\nName: "+x[1]
                );
                output = false;
            }
        }
        return output;
    }


    private static void calculateChange(double price) {
        int choice = Interface.popupOptionsChosenIndex(
            new String[]{"Yes","Skip"}, "Calculate Change?"
        );
        if (choice != 0) return;

        Double cusPayment = Interface.popupDoubleInput(
            "Total Price: "+price+"\n\n"+
            "Enter the Customer's payment amount:"
        );
        if (cusPayment == null) return;

        Double change = price-cusPayment;
        if (cusPayment < price) {
            Interface.popupMessage(
                "Payment is NOT ENOUGH by:\n\n"+change
            );
        } 
        else if (cusPayment > price) {
            Interface.popupMessage(
                "The change is:\n\n"+(change*-1)
            );
        }
        else {
            Interface.popupMessage(
                "The change is:\n\n0"
            );
        }
    }
}