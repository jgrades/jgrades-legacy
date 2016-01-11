/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.app.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.lic.api.model.Customer;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;
import org.jgrades.lic.api.model.Product;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class LicenceBuilderTest {
    private LicenceBuilder builder;

    private LocalDate start = LocalDate.now();
    private LocalDate stop = start.plusMonths(1);

    @Before
    public void setUp() throws Exception {
        builder = new LicenceBuilder();

        assertThat(builder).isNotNull();
    }

    @Test
    public void shouldReturnEmptyLicence() throws Exception {
        // given
        Licence expectedLicence = getEmptyLicence();

        // when
        Licence licence = builder.build();

        // then
        assertThat(licence).isEqualTo(expectedLicence);
    }

    @Test
    public void shouldSetUid() throws Exception {
        // given
        Long uid = 1234L;
        Licence expectedLicence = getEmptyLicence();
        expectedLicence.setUid(uid);

        // when
        builder.withLicenceUID(Long.toString(uid));
        Licence licence = builder.build();

        // then
        assertThat(licence).isEqualTo(expectedLicence);
        assertThat(licence.getUid()).isEqualTo(expectedLicence.getUid());
    }

    @Test
    public void shouldSetCustomerInfo() throws Exception {
        // given
        Licence expectedLicence = getEmptyLicence();
        expectedLicence.setCustomer(getExampleCustomer());

        // when
        builder.withCustomerID(Long.toString(getExampleCustomer().getId()));
        builder.withCustomerName(getExampleCustomer().getName());
        builder.withCustomerAddress(getExampleCustomer().getAddress());
        builder.withCustomerPhone(getExampleCustomer().getPhone());
        Licence licence = builder.build();

        // then
        assertThat(licence).isEqualTo(expectedLicence);
        assertThat(licence.getCustomer()).isEqualTo(expectedLicence.getCustomer());
    }

    @Test
    public void shouldSetProductInfo() throws Exception {
        // given
        Licence expectedLicence = getEmptyLicence();
        expectedLicence.setProduct(getExampleProduct());

        // when
        builder.withProductName(getExampleProduct().getName());
        builder.withProductVersion(getExampleProduct().getVersion());
        builder.withStartOfValid(start);
        builder.withEndOfValid(stop);
        Licence licence = builder.build();

        // then
        assertThat(licence).isEqualTo(expectedLicence);
        assertThat(licence.getProduct()).isEqualTo(expectedLicence.getProduct());
    }

    @Test
    public void shouldSetProperties() throws Exception {
        // given
        Licence expectedLicence = getEmptyLicence();
        expectedLicence.setProperties(getExampleProperties());

        // when
        builder.withProperties("name1=value1\nname2=value2\nname3=value3");
        Licence licence = builder.build();

        // then
        assertThat(licence).isEqualTo(expectedLicence);
        assertThat(licence.getProperties()).isEqualTo(expectedLicence.getProperties());
    }

    @Test
    public void shouldSetAllFields_andBuildFullLicence() throws Exception {
        // given
        Licence expectedLicence = new Licence();
        Long uid = 1234L;
        expectedLicence.setUid(uid);
        expectedLicence.setCustomer(getExampleCustomer());
        expectedLicence.setProduct(getExampleProduct());
        expectedLicence.setProperties(getExampleProperties());

        // when
        builder.withLicenceUID(Long.toString(uid));
        builder.withCustomerID(Long.toString(getExampleCustomer().getId()));
        builder.withCustomerName(getExampleCustomer().getName());
        builder.withCustomerAddress(getExampleCustomer().getAddress());
        builder.withCustomerPhone(getExampleCustomer().getPhone());
        builder.withProductName(getExampleProduct().getName());
        builder.withProductVersion(getExampleProduct().getVersion());
        builder.withStartOfValid(start);
        builder.withEndOfValid(stop);
        builder.withProperties("name1=value1\nname2=value2\nname3=value3");
        Licence licence = builder.build();

        // then
        assertThat(licence).isEqualTo(expectedLicence);
        assertThat(licence.getUid()).isEqualTo(expectedLicence.getUid());
        assertThat(licence.getCustomer()).isEqualTo(expectedLicence.getCustomer());
        assertThat(licence.getProduct()).isEqualTo(expectedLicence.getProduct());
        assertThat(licence.getProperties()).isEqualTo(expectedLicence.getProperties());
    }

    @Test
    public void shouldIgnoreEmptyOrNullArguments() throws Exception {
        // given
        Licence expectedLicence = getEmptyLicence();

        // when
        builder.withLicenceUID(StringUtils.EMPTY);
        builder.withCustomerID(StringUtils.EMPTY);
        builder.withCustomerName(StringUtils.EMPTY);
        builder.withCustomerAddress(StringUtils.EMPTY);
        builder.withCustomerPhone(StringUtils.EMPTY);
        builder.withProductName(StringUtils.EMPTY);
        builder.withProductVersion(StringUtils.EMPTY);
        builder.withStartOfValid(StringUtils.EMPTY);
        builder.withEndOfValid(StringUtils.EMPTY);
        builder.withProperties(StringUtils.EMPTY);
        Licence licence = builder.build();

        // then
        assertThat(licence).isEqualTo(expectedLicence);
        assertThat(licence.getUid()).isEqualTo(expectedLicence.getUid());
        assertThat(licence.getCustomer()).isEqualTo(expectedLicence.getCustomer());
        assertThat(licence.getProduct()).isEqualTo(expectedLicence.getProduct());
        assertThat(licence.getProperties()).isEqualTo(expectedLicence.getProperties());
    }

    private Licence getEmptyLicence() {
        Licence licence = new Licence();
        licence.setCustomer(new Customer());
        licence.setProduct(new Product());
        licence.setProperties(Lists.newArrayList());
        return licence;
    }

    private Customer getExampleCustomer() {
        Customer customer = new Customer();
        customer.setId(5678L);
        customer.setName("custName");
        customer.setAddress("custAddr");
        customer.setPhone("custPhone");
        return customer;
    }

    private Product getExampleProduct() {
        Product product = new Product();
        product.setName("prodName");
        product.setVersion("prodVersion");
        product.setValidFrom(LocalDateTime.of(start, LocalTime.of(0, 0)));
        product.setValidTo(LocalDateTime.of(stop, LocalTime.of(0, 0)));
        return product;
    }

    private List<LicenceProperty> getExampleProperties() {
        List<LicenceProperty> properties = Lists.newArrayList();
        properties.add(buildProperty("name1", "value1"));
        properties.add(buildProperty("name2", "value2"));
        properties.add(buildProperty("name3", "value3"));
        return properties;
    }

    private LicenceProperty buildProperty(String name, String value) {
        LicenceProperty property = new LicenceProperty();
        property.setName(name);
        property.setValue(value);
        return property;
    }
}
