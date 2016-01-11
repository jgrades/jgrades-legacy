/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging;

import com.project.StrangerClass;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class JgLoggerFactoryTest {
    @Test
    public void shouldSetJGradesModuleName_whenConstructWithJGradesClass() throws Exception {
        // given
        Class clazz = JgLoggerTest.class;

        // when
        JgLogger jgLogger = JgLoggerFactory.getLogger(clazz);

        // then
        String moduleName = (String) ReflectionTestUtils.getField(jgLogger, "moduleName");
        assertThat(moduleName).isEqualTo("logging");
    }

    @Test
    public void shouldSetExternalLibModuleName_whenConstructNotWithJGradesClass() throws Exception {
        // given
        Class clazz = StrangerClass.class;

        // when
        JgLogger jgLogger = JgLoggerFactory.getLogger(clazz);

        // then
        String moduleName = (String) ReflectionTestUtils.getField(jgLogger, "moduleName");
        assertThat(moduleName).isEqualTo("external-lib");
    }

}
