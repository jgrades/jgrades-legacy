package org.jgrades.lic.app.cli;

import org.jgrades.lic.app.launch.LicenceApplication;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleApplication implements LicenceApplication {
    public static final String APPLICATION_HEADER = "jGrades Licensing Manager Application 0.4";

    private Scanner scanner = new Scanner(System.in, "UTF-8");

    protected void printPrompt() {
        System.out.print(">: ");
    }

    protected String getLine(String description) {
        System.out.println(description + ":");
        printPrompt();
        return scanner.nextLine();
    }

    @Override
    public void runApp() {
        printGreetings();
        while (true) {
            ApplicationAction action = chooseAction();
            action.printDescription();
            action.start();
        }
    }

    private ApplicationAction chooseAction() {
        System.out.println("What would you like to do now?");
        System.out.println("1 - Create new licence");
        System.out.println("2 - Open (read-only) existing licence");
        System.out.println("3 - Exit application");
        printPrompt();
        try {
            int action = scanner.nextInt();
            if (action == 1) {
                return new NewLicenceAction(this);
            } else if (action == 2) {
                return new OpenLicenceAction(this);
            } else if (action == 3) {
                return new ExitAction();
            } else {
                throw new IllegalArgumentException();
            }
        } catch (NoSuchElementException e) {
            System.out.println("Invalid option. Try again.");
            scanner.reset();
            scanner = new Scanner(System.in);
            return chooseAction();
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown option. Try again.");
            return chooseAction();
        } finally {
            scanner.nextLine();
        }
    }

    private void printGreetings() {
        System.out.println(APPLICATION_HEADER);
        System.out.println("============================================");
    }
}
