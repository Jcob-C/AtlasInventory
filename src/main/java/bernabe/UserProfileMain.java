package bernabe;

import base.Main;

public class UserProfileMain {
    
    public static void createModule() {
        Main.addCard(UserProfilePage.createPanel(), "user settings");
    }

    public static void gotoUserSettings() {
        Main.changeCard("user settings");
    }
}
