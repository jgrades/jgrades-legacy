/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.app.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.lic.api.model.*;
import org.joda.time.DateTime;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LicenceBuilder {
    private final PropertiesTextAreaParser propertiesParser = new PropertiesTextAreaParser();
    private final LicenceDateTimeAdapter dateTimeAdapter = new LicenceDateTimeAdapter();

    private Licence licence;
    private Customer customer;
    private Product product;
    private List<LicenceProperty> properties;

    public LicenceBuilder() {
        initBuildingNewLicence();
    }

    public LicenceBuilder withLicenceUID(String uid) {
        if (!StringUtils.isEmpty(uid)) {
            licence.setUid(Long.parseLong(uid));
        }
        return this;
    }

    public LicenceBuilder withCustomerID(String id) {
        if (!StringUtils.isEmpty(id)) {
            customer.setId(Long.parseLong(id));
        }
        return this;
    }

    public LicenceBuilder withCustomerName(String name) {
        if (!StringUtils.isEmpty(name)) {
            customer.setName(name);
        }
        return this;
    }

    public LicenceBuilder withCustomerAddress(String address) {
        if (!StringUtils.isEmpty(address)) {
            customer.setAddress(address);
        }
        return this;
    }

    public LicenceBuilder withCustomerPhone(String phone) {
        if (!StringUtils.isEmpty(phone)) {
            customer.setPhone(phone);
        }
        return this;
    }

    public LicenceBuilder withProductName(String name) {
        if (!StringUtils.isEmpty(name)) {
            product.setName(name);
        }
        return this;
    }

    public LicenceBuilder withProductVersion(String version) {
        if (!StringUtils.isEmpty(version)) {
            product.setVersion(version);
        }
        return this;
    }

    public LicenceBuilder withStartOfValid(LocalDate fromDateTime) {
        if (Optional.ofNullable(fromDateTime).isPresent() && !StringUtils.isEmpty(fromDateTime.toString())) {
            product.setValidFrom(new DateTime(fromDateTime.toString()));
        }
        return this;
    }

    public LicenceBuilder withStartOfValid(String validFrom) {
        if (!StringUtils.isEmpty(validFrom)) {
            product.setValidFrom(dateTimeAdapter.unmarshal(validFrom));
        }
        return this;
    }

    public LicenceBuilder withEndOfValid(LocalDate toDateTime) {
        if (Optional.ofNullable(toDateTime).isPresent() && !StringUtils.isEmpty(toDateTime.toString())) {
            product.setValidTo(new DateTime(toDateTime.toString()));
        }
        return this;
    }

    public LicenceBuilder withEndOfValid(String validTo) {
        if (!StringUtils.isEmpty(validTo)) {
            product.setValidTo(dateTimeAdapter.unmarshal(validTo));
        }
        return this;
    }

    public LicenceBuilder withProperties(String propertiesText) {
        if (!StringUtils.isEmpty(propertiesText)) {
            properties.addAll(propertiesParser.getProperties(propertiesText));
        }
        return this;
    }

    private void initBuildingNewLicence() {
        licence = new Licence();
        customer = new Customer();
        product = new Product();
        properties = Lists.newArrayList();

        licence.setCustomer(customer);
        licence.setProduct(product);
        licence.setProperties(properties);
    }

    public Licence build() {
        try {
            return licence;
        } finally {
            initBuildingNewLicence();
        }
    }
}
