/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic;

import com.google.common.collect.Lists;
import org.dozer.Mapper;
import org.jgrades.lic.api.model.Customer;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;
import org.jgrades.lic.api.model.Product;
import org.jgrades.lic.entities.CustomerEntity;
import org.jgrades.lic.entities.LicenceEntity;
import org.jgrades.lic.entities.LicencePropertyEntity;
import org.jgrades.lic.entities.ProductEntity;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MappingTest extends BaseTest {
    @Autowired
    private Mapper mapper;

    @Before
    public void setUp() throws Exception {
        assertThat(mapper).isNotNull();
    }

    @Test
    public void shouldMapLicenceToEntity() throws Exception {
        // given
        Licence licence = getExampleLicence();
        LicenceEntity expectedLicenceEntity = getExampleLicenceEntity();

        // when
        LicenceEntity licenceEntity = mapper.map(licence, LicenceEntity.class);

        // then
        assertThat(licenceEntity).isEqualTo(expectedLicenceEntity);
    }

    @Test
    public void shouldMapEntityToLicence() throws Exception {
        // given
        LicenceEntity licenceEntity = getExampleLicenceEntity();
        Licence expectedLicence = getExampleLicence();

        // when
        Licence licence = mapper.map(licenceEntity, Licence.class);

        // then
        assertThat(licence).isEqualTo(expectedLicence);
    }

    private Licence getExampleLicence() {
        Licence licence = new Licence();
        licence.setUid(5678L);

        Customer customer = new Customer();
        customer.setId(1234L);
        customer.setName("XIV LO");
        customer.setAddress("Wrocław");
        customer.setPhone("+48 71 234 56 78");
        licence.setCustomer(customer);

        Product product = new Product();
        product.setName("JG-BASE");
        product.setValidFrom(new DateTime(2015, 8, 14, 12, 30, 0));
        product.setValidTo(new DateTime(2015, 9, 14, 12, 30, 0));
        product.setVersion("0.4");
        licence.setProduct(product);

        LicenceProperty property1 = new LicenceProperty();
        property1.setName("N1");
        property1.setValue("V1");
        LicenceProperty property2 = new LicenceProperty();
        property2.setName("N2");
        property2.setValue("V2");
        List<LicenceProperty> properties = Lists.newArrayList(property1, property2);
        licence.setProperties(properties);

        return licence;
    }

    private LicenceEntity getExampleLicenceEntity() {
        LicenceEntity licenceEntity = new LicenceEntity();
        licenceEntity.setUid(5678L);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerId(1234L);
        customerEntity.setName("XIV LO");
        customerEntity.setAddress("Wrocław");
        customerEntity.setPhone("+48 71 234 56 78");
        licenceEntity.setCustomer(customerEntity);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("JG-BASE");
        productEntity.setValidFrom(new DateTime(2015, 8, 14, 12, 30, 0));
        productEntity.setValidTo(new DateTime(2015, 9, 14, 12, 30, 0));
        productEntity.setVersion("0.4");
        licenceEntity.setProduct(productEntity);

        LicencePropertyEntity property1 = new LicencePropertyEntity();
        property1.setName("N1");
        property1.setValue("V1");
        LicencePropertyEntity property2 = new LicencePropertyEntity();
        property2.setName("N2");
        property2.setValue("V2");
        List<LicencePropertyEntity> properties = Lists.newArrayList(property1, property2);
        licenceEntity.setProperties(properties);

        return licenceEntity;
    }


}
