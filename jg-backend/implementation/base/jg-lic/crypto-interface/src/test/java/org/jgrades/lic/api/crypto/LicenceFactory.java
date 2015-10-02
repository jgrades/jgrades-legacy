/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.api.crypto;

import com.google.common.collect.Lists;
import org.jgrades.lic.api.model.Customer;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;
import org.jgrades.lic.api.model.Product;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;

public class LicenceFactory {
    public static Licence getCorrectLicence() {
        Licence licence = new Licence();
        licence.setUid(1234L);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("school1");
        customer.setAddress("address1");
        customer.setPhone("+48 601 234 567");

        Product product = new Product();
        product.setName("JG-BASE");
        product.setVersion("0.4");
        product.setValidFrom(new DateTime(0, DateTimeZone.UTC));
        product.setValidTo(new DateTime(0, DateTimeZone.UTC).plusMonths(1));

        LicenceProperty licProperty1 = new LicenceProperty();
        licProperty1.setName("mac");
        licProperty1.setValue("00:0A:E6:3E:FD:E1");

        LicenceProperty licProperty2 = new LicenceProperty();
        licProperty2.setName("expiredDays");
        licProperty2.setValue("14");

        List<LicenceProperty> properties = Lists.newArrayList(licProperty1, licProperty2);

        licence.setCustomer(customer);
        licence.setProduct(product);
        licence.setProperties(properties);

        return licence;
    }
}
