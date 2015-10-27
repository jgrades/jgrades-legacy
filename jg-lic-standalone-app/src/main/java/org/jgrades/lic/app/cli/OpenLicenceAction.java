/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.app.cli;

import org.jgrades.lic.api.crypto.decrypt.LicenceDecryptionService;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;

import static org.jgrades.lic.api.model.LicenceDateTimeAdapter.getLicDateTimeFormatter;

public class OpenLicenceAction implements ApplicationAction {
    public static final String LICENCE_OPENED_SUCCESS_MESSAGE = "SUCCESS! Licence opened correctly.";
    public static final String SIGNATURE_VALID_SUCCESS_MESSAGE = "SUCCESS! Signature is valid";
    public static final String SIGNATURE_NOT_VALID_WARNING_MESSAGE = "WARNING! Signature is not valid.";
    public static final String GENERAL_ERROR_MESSAGE = "Operation interrupted by error: ";

    private ConsoleApplication console;
    private LicenceDecryptionService licenceDecryptionService = new LicenceDecryptionService();

    public OpenLicenceAction(ConsoleApplication consoleApplication) {
        this.console = consoleApplication;
    }

    private static void prettyPrint(Licence licence) {
        System.out.println("Licence UID: " + licence.getUid()); //NOSONAR
        System.out.println("Customer ID: " + licence.getCustomer().getId()); //NOSONAR
        System.out.println("Customer name: " + licence.getCustomer().getName()); //NOSONAR
        System.out.println("Customer address: " + licence.getCustomer().getAddress()); //NOSONAR
        System.out.println("Customer phone: " + licence.getCustomer().getPhone()); //NOSONAR
        System.out.println("Product name: " + licence.getProduct().getName()); //NOSONAR
        System.out.println("Product version: " + licence.getProduct().getVersion()); //NOSONAR
        System.out.println("Licence valid from: " + getLicDateTimeFormatter().format(licence.getProduct().getValidFrom())); //NOSONAR
        System.out.println("Licence valid to: " + getLicDateTimeFormatter().format(licence.getProduct().getValidTo())); //NOSONAR
        System.out.println("Licence properties:"); //NOSONAR
        for (LicenceProperty property : licence.getProperties()) {
            System.out.println(property.getName() + " => " + property.getValue()); //NOSONAR
        }
    }

    @Override
    public void printDescription() {
        System.out.println("OPENING OF EXISTING LICENCE"); //NOSONAR
        System.out.println("=========================="); //NOSONAR
    }

    @Override
    public void start() {
        try {
            String keystorePath = console.getLine("Enter keystore path");
            String secDatPath = console.getLine("Enter secure data path");
            String licencePath = console.getLine("Enter licence path");
            String signaturePath = console.getLine("Enter signature path");

            Licence licence = licenceDecryptionService.decrypt(keystorePath, secDatPath, licencePath);
            prettyPrint(licence);
            System.out.println(LICENCE_OPENED_SUCCESS_MESSAGE); //NOSONAR

            if (licenceDecryptionService.validSignature(keystorePath, secDatPath, licencePath, signaturePath)) {
                System.out.println(SIGNATURE_VALID_SUCCESS_MESSAGE); //NOSONAR
            } else {
                System.out.println(SIGNATURE_NOT_VALID_WARNING_MESSAGE); //NOSONAR
            }
        } catch (Exception e) {
            System.err.println(GENERAL_ERROR_MESSAGE + e); //NOSONAR
        }
    }
}
