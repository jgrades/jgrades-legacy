package org.jgrades.lic.app.launch;

//TODO use standard java logging in entire lic standalone app
public class Main {
    public static void main(String[] args) {
        UserInterfaceChooser chooser = new UserInterfaceChooser();
        LicenceApplication application = chooser.choose(args);
        application.runApp();
    }
}
