/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.app.launch;

import org.jgrades.lic.app.cli.ConsoleApplication;
import org.jgrades.lic.app.controller.JavafxApplication;

public class UserInterfaceChooser {
    private static final String NO_GUI = "nogui";
    private static final String DASH_NO_GUI = "-nogui";
    private static final String DASH_DASH_NO_GUI = "--nogui";

    private static boolean argumentMatched(String arg) {
        return arg.equalsIgnoreCase(NO_GUI) ||
                arg.equalsIgnoreCase(DASH_NO_GUI) ||
                arg.equalsIgnoreCase(DASH_DASH_NO_GUI);
    }

    public LicenceApplication choose(String[] args) {
        if (args != null && args.length >= 1 && argumentMatched(args[0])) {
            return new ConsoleApplication();
        } else {
            return new JavafxApplication();
        }
    }
}
