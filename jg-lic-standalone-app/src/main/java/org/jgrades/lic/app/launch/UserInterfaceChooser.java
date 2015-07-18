package org.jgrades.lic.app.launch;

import org.jgrades.lic.app.cli.ConsoleApplication;
import org.jgrades.lic.app.controller.JavafxApplication;

public class UserInterfaceChooser {
    private static final String NO_GUI = "nogui";
    private static final String DASH_NO_GUI = "-nogui";
    private static final String DASH_DASH_NO_GUI = "--nogui";

    public LicenceApplication choose(String[] args) {
        if (args != null && args.length >= 1 && argumentMatched(args[0])) {
            return new ConsoleApplication();
        } else {
            return new JavafxApplication();
        }
    }

    private boolean argumentMatched(String arg) {
        return arg.equalsIgnoreCase(NO_GUI) ||
                arg.equalsIgnoreCase(DASH_NO_GUI) ||
                arg.equalsIgnoreCase(DASH_DASH_NO_GUI);
    }
}
