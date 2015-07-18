package org.jgrades.lic.app.cli;

public class ExitAction implements ApplicationAction {
    @Override
    public void printDescription() {
        System.out.println("Bye!");
    }

    @Override
    public void start() {
        System.exit(0);
    }
}
