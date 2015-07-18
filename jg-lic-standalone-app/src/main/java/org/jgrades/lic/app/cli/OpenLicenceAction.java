package org.jgrades.lic.app.cli;

import org.jgrades.lic.api.crypto.decrypt.LicenceDecryptionProvider;
import org.jgrades.lic.api.crypto.decrypt.SignatureValidator;
import org.jgrades.lic.api.crypto.utils.KeyStoreContentExtractor;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceDateTimeAdapter;
import org.jgrades.lic.api.model.LicenceProperty;

import java.io.File;
import java.io.IOException;

public class OpenLicenceAction implements ApplicationAction {
    public static final String LICENCE_OPENED_SUCCESS_MESSAGE = "SUCCESS! Licence opened correctly.";
    public static final String SIGNATURE_VALID_SUCCESS_MESSAGE = "SUCCESS! Signature is valid";
    public static final String SIGNATURE_NOT_VALID_WARNING_MESSAGE = "WARNING! Signature is not valid.";

    private ConsoleApplication console;

    public OpenLicenceAction(ConsoleApplication consoleApplication) {
        this.console = consoleApplication;
    }

    @Override
    public void printDescription() {
        System.out.println("OPENING OF EXISTING LICENCE");
        System.out.println("==========================");
    }

    @Override
    public void action() {
        String keystorePath = console.getLine("Enter keystore path");
        String secDatPath = console.getLine("Enter secure data path");
        String licencePath = console.getLine("Enter licence path");
        String signaturePath = console.getLine("Enter signature path");

        try {
            KeyStoreContentExtractor extractor =
                    new KeyStoreContentExtractor(new File(keystorePath), new File(secDatPath));
            LicenceDecryptionProvider decryptionProvider = new LicenceDecryptionProvider(extractor);

            SignatureValidator signatureValidator = new SignatureValidator(extractor);
            boolean validationSuccess = signatureValidator.signatureValidated(
                    new File(licencePath), new File(signaturePath));

            if (validationSuccess) {
                System.out.println(SIGNATURE_VALID_SUCCESS_MESSAGE);
            } else {
                System.out.println(SIGNATURE_NOT_VALID_WARNING_MESSAGE);
            }

            Licence licence = decryptionProvider.decrypt(new File(licencePath));
            prettyPrint(licence);

            System.out.println(LICENCE_OPENED_SUCCESS_MESSAGE);
        } catch (IOException e) {
            System.err.println("Path(s) to one or more of input files is/are incorrect: " + e);
        }
    }

    private void prettyPrint(Licence licence) {
        System.out.println("Licence UID: " + licence.getUid());
        System.out.println("Customer ID: " + licence.getCustomer().getId());
        System.out.println("Customer name: " + licence.getCustomer().getName());
        System.out.println("Customer address: " + licence.getCustomer().getAddress());
        System.out.println("Customer phone: " + licence.getCustomer().getPhone());
        System.out.println("Product name: " + licence.getProduct().getName());
        System.out.println("Product version: " + licence.getProduct().getVersion());
        System.out.println("Licence valid from: " + LicenceDateTimeAdapter.getLicDateTimeFormatter().print(licence.getProduct().getValidFrom()));
        System.out.println("Licence valid to: " + LicenceDateTimeAdapter.getLicDateTimeFormatter().print(licence.getProduct().getValidTo()));
        System.out.println("Licence properties:");
        for (LicenceProperty property : licence.getProperties()) {
            System.out.println(property.getName() + "=>" + property.getValue());
        }
    }
}
