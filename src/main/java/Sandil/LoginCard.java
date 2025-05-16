package sandil;

import base.Main;
public class LoginCard {
    public static Login loginPanel = new Login();

    public static void createModule() {
        Main.addCard(loginPanel, "login");
    }
}
