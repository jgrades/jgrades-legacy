/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.api.service;

import com.google.common.collect.Lists;
import org.jgrades.lic.api.model.Customer;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;
import org.jgrades.lic.api.model.Product;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenceMarshallingFactoryTest {
    private static final String CORRECT_LICENCE_FILENAME = "correct_licence.xml";
    private static final String CORRECT_LICENCE_UTF8_FILENAME = "correct_licence_utf8.xml";
    private static final String INCORRECT_DATETIME_LICENCE_FILENAME = "incorrect_datetime_licence.xml";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private Marshaller jaxbMarshaller;
    private Unmarshaller jaxbUnmarshaller;

    @Before
    public void setUp() throws Exception {
        jaxbMarshaller = LicenceMarshallingFactory.getMarshaller();
        jaxbUnmarshaller = LicenceMarshallingFactory.getUnmarshaller();

        assertThat(jaxbMarshaller).isNotNull();
        assertThat(jaxbUnmarshaller).isNotNull();
    }

    @Test
    public void shouldMarshall_whenCorrectLicence() throws Exception {
        // given
        Licence licence = getCorrectLicence("school1");
        File expectedXmlFile = new File(getResourcePath(CORRECT_LICENCE_FILENAME));
        File workingFile = folder.newFile();

        // when
        jaxbMarshaller.marshal(licence, workingFile);
        Licence unmarshalLicence = (Licence) jaxbUnmarshaller.unmarshal(workingFile);
        Licence unmarshalExpectedLicence = (Licence) jaxbUnmarshaller.unmarshal(expectedXmlFile);

        // then
        assertThat(licence).isEqualTo(unmarshalLicence);
        assertThat(unmarshalLicence).isEqualTo(unmarshalExpectedLicence);
    }

    @Test
    public void shouldMarshall_whenCorrectLicenceWithUtf8Characters() throws Exception {
        // given
        Licence licence = getCorrectLicence("ąęłżźóĎ÷€€€€€");
        File expectedXmlFile = new File(getResourcePath(CORRECT_LICENCE_UTF8_FILENAME));
        File workingFile = folder.newFile();

        // when
        jaxbMarshaller.marshal(licence, workingFile);
        Licence unmarshalLicence = (Licence) jaxbUnmarshaller.unmarshal(workingFile);
        Licence unmarshalExpectedLicence = (Licence) jaxbUnmarshaller.unmarshal(expectedXmlFile);

        // then
        assertThat(licence).isEqualTo(unmarshalLicence);
        assertThat(unmarshalLicence).isEqualTo(unmarshalExpectedLicence);
    }

    @Test
    public void shouldUnmarshall_whenCorrectXML() throws Exception {
        // given
        String licenceFilePath = getResourcePath(CORRECT_LICENCE_FILENAME);

        // when
        Licence licence = (Licence) jaxbUnmarshaller.unmarshal(new File(licenceFilePath));

        // then
        assertThat(licence).isEqualTo(getCorrectLicence("school1"));
    }

    @Test
    public void shouldUnmarshall_whenCorrectXMLWithUtf8Characters() throws Exception {
        // given
        String licenceFilePath = getResourcePath(CORRECT_LICENCE_UTF8_FILENAME);

        // when
        Licence licence = (Licence) jaxbUnmarshaller.unmarshal(new File(licenceFilePath));

        // then
        assertThat(licence).isEqualTo(getCorrectLicence("ąęłżźóĎ÷€€€€€"));
    }

    @Test(expected = UnmarshalException.class)
    public void shouldFailed_whenIncorrectDateTimeFormat() throws Exception {
        // given
        String licenceFilePath = getResourcePath(INCORRECT_DATETIME_LICENCE_FILENAME);

        // when
        jaxbUnmarshaller.unmarshal(new File(licenceFilePath));

        // then
        // should throw UnmarshalException
    }

    private Licence getCorrectLicence(String customerName) {
        Licence licence = new Licence();
        licence.setUid(1234L);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName(customerName);
        customer.setAddress("address1");
        customer.setPhone("+48 601 234 567");

        Product product = new Product();
        product.setName("JG-BASE");
        product.setVersion("0.4");
        product.setValidFrom(LocalDateTime.of(1970, 1, 1, 0, 0));
        product.setValidTo(LocalDateTime.of(1970, 2, 1, 0, 0));

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

    private String getResourcePath(String fileName) {
        return this.getClass().getClassLoader().getResource(fileName).getFile();
    }
}
