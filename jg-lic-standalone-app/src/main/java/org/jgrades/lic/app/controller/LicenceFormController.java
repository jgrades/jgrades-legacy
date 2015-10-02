/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceDateTimeAdapter;
import org.jgrades.lic.app.utils.LicenceBuilder;
import org.jgrades.lic.app.utils.PropertiesTextAreaParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LicenceFormController {
    @FXML
    private TextField productNameField;

    @FXML
    private TextField licenceUidField;

    @FXML
    private TextField customerNameField;

    @FXML
    private DatePicker licValidFromDate;

    @FXML
    private TextField productVersionField;

    @FXML
    private DatePicker licValidToDate;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField customerAddrField;

    @FXML
    private TextField customerPhoneField;

    @FXML
    private TextArea propertiesArea;

    private PropertiesTextAreaParser textAreaParser = new PropertiesTextAreaParser();

    Licence getLicenceModel() {
        LicenceBuilder licenceBuilder = new LicenceBuilder();

        licenceBuilder
                .withLicenceUID(licenceUidField.getText())
                .withCustomerID(customerIdField.getText())
                .withCustomerName(customerNameField.getText())
                .withCustomerAddress(customerAddrField.getText())
                .withCustomerPhone(customerPhoneField.getText())
                .withProductName(productNameField.getText())
                .withProductVersion(productVersionField.getText())
                .withStartOfValid(licValidFromDate.getValue())
                .withEndOfValid(licValidToDate.getValue())
                .withProperties(propertiesArea.getText());

        return licenceBuilder.build();
    }

    public void setLicenceModel(Licence licence) {
        String pattern = LicenceDateTimeAdapter.PATTERN;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

        licenceUidField.setText(licence.getUid().toString());
        customerIdField.setText(licence.getCustomer().getId().toString());
        customerNameField.setText(licence.getCustomer().getName());
        customerAddrField.setText(licence.getCustomer().getAddress());
        customerPhoneField.setText(licence.getCustomer().getPhone());
        productNameField.setText(licence.getProduct().getName());
        productVersionField.setText(licence.getProduct().getVersion());
        String validFromString = licence.getProduct().getValidFrom().toString(pattern);
        licValidFromDate.setValue(LocalDate.parse(validFromString, dateTimeFormatter));
        String validToString = licence.getProduct().getValidTo().toString(pattern);
        licValidToDate.setValue(LocalDate.parse(validToString, dateTimeFormatter));
        propertiesArea.setText(textAreaParser.getPropertiesText(licence.getProperties()));
        setEditable(false);
    }

    void clearData() {
        licenceUidField.clear();
        customerIdField.clear();
        customerNameField.clear();
        customerAddrField.clear();
        customerPhoneField.clear();
        productNameField.clear();
        productVersionField.clear();
        licValidFromDate.setValue(null);
        licValidToDate.setValue(null);
        propertiesArea.clear();
        setEditable(true);
    }

    private void setEditable(boolean value) {
        licenceUidField.setEditable(value);
        customerIdField.setEditable(value);
        customerNameField.setEditable(value);
        customerAddrField.setEditable(value);
        customerPhoneField.setEditable(value);
        productNameField.setEditable(value);
        productVersionField.setEditable(value);
        licValidFromDate.setEditable(value);
        licValidToDate.setEditable(value);
        propertiesArea.setEditable(value);
    }
}
