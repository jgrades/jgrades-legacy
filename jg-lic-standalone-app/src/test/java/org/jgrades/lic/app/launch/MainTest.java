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
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class MainTest {
    @Rule
    public final TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog().muteForSuccessfulTests();
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    private final String INVOKE_EXIT_ACTION_CODE = "3";

    @Test
    public void shouldRunConsoleMode_whenNoguiAgrumentPresent() throws Exception {
        // then
        exit.expectSystemExitWithStatus(0);

        exit.checkAssertionAfterwards(() -> {
            thenOutputContains(ConsoleApplication.APPLICATION_HEADER);
        });

        // when
        systemInMock.provideLines(INVOKE_EXIT_ACTION_CODE);
        Main.main(new String[]{"nogui"});
    }

    private void thenOutputContains(String content) {
        assertThat(systemOutRule.getLog()).contains(content);
    }

}
