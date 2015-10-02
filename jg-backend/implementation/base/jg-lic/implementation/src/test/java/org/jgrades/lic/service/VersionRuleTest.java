/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.service;

import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.Product;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VersionRuleTest {
    private VersionRule versionRule;
    private Licence licence;

    @Before
    public void setUp() throws Exception {
        licence = new Licence();
    }

    @Test
    public void shouldValid_whenVersionMatched() throws Exception {
        // given
        String expectedVersion = "0.4";
        versionRule = new VersionRule(expectedVersion);
        licence.setProduct(getProductWithVersion(expectedVersion));

        // when
        boolean isValid = versionRule.isValid(licence);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldNotValid_whenVersionNotMatched() throws Exception {
        // given
        String expectedVersion = "0.4";
        versionRule = new VersionRule(expectedVersion);
        licence.setProduct(getProductWithVersion("0.6"));

        // when
        boolean isValid = versionRule.isValid(licence);

        // then
        assertThat(isValid).isFalse();
    }

    private Product getProductWithVersion(String expectedVersion) {
        Product product = new Product();
        product.setVersion(expectedVersion);
        return product;
    }
}
