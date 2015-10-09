/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.app.cli;

import org.jgrades.lic.app.launch.LicenceApplication;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleApplication implements LicenceApplication {
    public static final String APPLICATION_HEADER = "jGrades Licensing Manager Application 0.4";
    public static final String INVALID_OPTION_MESSAGE = "Invalid option. Try again.";
    public static final String UNKNOWN_OPTION_MESSAGE = "Unknown option. Try again.";

    private Scanner scanner = new Scanner(System.in, "UTF-8");

    protected void printPrompt() {
        System.out.print(">: ");  //NOSONAR
    }

    protected String getLine(String description) {
        System.out.println(description + ":"); //NOSONAR
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
        System.out.println("What would you like to do now?"); //NOSONAR
        System.out.println("1 - Create new licence"); //NOSONAR
        System.out.println("2 - Open (read-only) existing licence"); //NOSONAR
        System.out.println("3 - Exit application"); //NOSONAR
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
            System.out.println(INVALID_OPTION_MESSAGE); //NOSONAR
            scanner = new Scanner(System.in);
            return chooseAction();
        } catch (IllegalArgumentException e) {
            System.out.println(UNKNOWN_OPTION_MESSAGE); //NOSONAR
            return chooseAction();
        } finally {
            try {
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                //NOSONAR (not needed...)
            }
        }
    }

    private void printGreetings() {
        System.out.println(APPLICATION_HEADER); //NOSONAR
        System.out.println("============================================"); //NOSONAR
    }
}
