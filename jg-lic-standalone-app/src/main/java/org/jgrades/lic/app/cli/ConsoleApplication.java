/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.app.cli;

import org.jgrades.lic.app.launch.LicenceApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleApplication implements LicenceApplication {
    public static final String APPLICATION_HEADER = "jGrades Licensing Manager Application 0.4";
    public static final String INVALID_OPTION_MESSAGE = "Invalid option. Try again.";
    public static final String UNKNOWN_OPTION_MESSAGE = "Unknown option. Try again.";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleApplication.class);
    private Scanner scanner = new Scanner(System.in, "UTF-8");

    private static void printGreetings() {
        System.out.println(APPLICATION_HEADER); //NOSONAR
        System.out.println("============================================"); //NOSONAR
    }

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

    private ApplicationAction chooseAction() { //NOSONAR
        System.out.println("What would you like to do now?"); //NOSONAR
        System.out.println("1 - Create new licence"); //NOSONAR
        System.out.println("2 - Open (read-only) existing licence"); //NOSONAR
        System.out.println("3 - Exit application"); //NOSONAR
        printPrompt();
        try {
            int action = scanner.nextInt();
            switch(action){
                case 1:
                    return new NewLicenceAction(this);
                case 2:
                    return new OpenLicenceAction(this);
                case 3:
                    return new ExitAction();
                default:
                    throw new IllegalArgumentException();
            }
        } catch (NoSuchElementException e) {
            LOGGER.trace(INVALID_OPTION_MESSAGE, e);
            System.out.println(INVALID_OPTION_MESSAGE); //NOSONAR
            scanner = new Scanner(System.in, "UTF-8");
            return chooseAction();
        } catch (IllegalArgumentException e) {
            LOGGER.trace(UNKNOWN_OPTION_MESSAGE, e);
            System.out.println(UNKNOWN_OPTION_MESSAGE); //NOSONAR
            return chooseAction();
        } finally {
            try {
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                LOGGER.trace("Getting nextline failed", e);
            }
        }
    }
}
