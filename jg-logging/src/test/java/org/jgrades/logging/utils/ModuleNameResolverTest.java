/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.utils;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ModuleNameResolverTest {
    @Test
    public void shouldReturnExternalLib_whenNull() throws Exception {
        // given
        String canonicalClassName = null;

        // when
        String moduleName = ModuleNameResolver.resolve(canonicalClassName);

        // then
        assertThat(moduleName).isEqualTo("external-lib");
    }

    @Test
    public void shouldReturnExternalLib_whenEmpty() throws Exception {
        // given
        String canonicalClassName = StringUtils.EMPTY;

        // when
        String moduleName = ModuleNameResolver.resolve(canonicalClassName);

        // then
        assertThat(moduleName).isEqualTo("external-lib");
    }

    @Test
    public void shouldReturnExternalLib_whenClassOutsideJGradesPackage1() throws Exception {
        // given
        String canonicalClassName = "org.springframework.data.auditing.AuditingHandler";

        // when
        String moduleName = ModuleNameResolver.resolve(canonicalClassName);

        // then
        assertThat(moduleName).isEqualTo("external-lib");
    }

    @Test
    public void shouldReturnExternalLib_whenClassOutsideJGradesPackage2() throws Exception {
        // given
        String canonicalClassName = "com.jgrades.foo.Clazz";

        // when
        String moduleName = ModuleNameResolver.resolve(canonicalClassName);

        // then
        assertThat(moduleName).isEqualTo("external-lib");
    }

    @Test
    public void shouldReturnExternalLib_whenClassOutsideJGradesPackage3() throws Exception {
        // given
        String canonicalClassName = "com.org.jgrades.foo.Clazz";

        // when
        String moduleName = ModuleNameResolver.resolve(canonicalClassName);

        // then
        assertThat(moduleName).isEqualTo("external-lib");
    }

    @Test
    public void shouldReturnLic_when_OrgJgradesLic() throws Exception {
        // given
        String canonicalClassName = "org.jgrades.lic.Clazz";

        // when
        String moduleName = ModuleNameResolver.resolve(canonicalClassName);

        // then
        assertThat(moduleName).isEqualTo("lic");
    }

    @Test
    public void shouldReturnRest_when_OrgJgradesRest() throws Exception {
        // given
        String canonicalClassName = "org.jgrades.rest.org.jgrades.Clazz";

        // when
        String moduleName = ModuleNameResolver.resolve(canonicalClassName);

        // then
        assertThat(moduleName).isEqualTo("rest");
    }

    @Test
    public void shouldReturnClazz_when_OrgJgradesClazz() throws Exception {
        // given
        String canonicalClassName = "org.jgrades.Clazz";

        // when
        String moduleName = ModuleNameResolver.resolve(canonicalClassName);

        // then
        assertThat(moduleName).isEqualTo("Clazz");
    }
}
