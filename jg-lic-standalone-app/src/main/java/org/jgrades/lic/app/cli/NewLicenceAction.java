package org.jgrades.lic.app.cli;

import org.apache.commons.lang3.StringUtils;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.app.service.LicenceBuilder;
import org.jgrades.lic.app.service.crypto.KeyStoreContentExtractor;
import org.jgrades.lic.app.service.crypto.encrypt.LicenceEncryptionProvider;
import org.jgrades.lic.app.service.crypto.encrypt.LicenceSaver;
import org.jgrades.lic.app.service.crypto.encrypt.LicenceSigningProvider;

import java.io.File;
import java.io.IOException;

public class NewLicenceAction implements ApplicationAction {
    public static final String SUCCESS_MESSAGE = "SUCCESS! Licence and signature saved.";

    private LicenceBuilder licenceBuilder = new LicenceBuilder();

    private ConsoleApplication console;

    public NewLicenceAction(ConsoleApplication consoleApplication) {
        this.console = consoleApplication;
    }

    @Override
    public void printDescription() {
        System.out.println("GENERATION OF NEW LICENCE");
        System.out.println("=========================");
    }

    @Override
    public void action() {
        String keystorePath = console.getLine("Enter keystore path");
        String secDatPath = console.getLine("Enter secure data path");

        try {
            KeyStoreContentExtractor extractor =
                    new KeyStoreContentExtractor(new File(keystorePath), new File(secDatPath));
            Licence licence = licenceCreator();
            LicenceEncryptionProvider encryptionProvider = new LicenceEncryptionProvider(extractor);
            byte[] encryptedBytes = encryptionProvider.encrypt(licence);

            LicenceSigningProvider signingProvider = new LicenceSigningProvider(extractor);
            byte[] signature = signingProvider.sign(encryptedBytes);

            String licencePath = console.getLine("Enter path to save licence");
            LicenceSaver saver = new LicenceSaver(licencePath);

            saver.saveLicence(encryptedBytes);
            saver.saveSignature(signature);
            System.out.println(SUCCESS_MESSAGE);
        } catch (IOException e) {
            System.err.println("Path to keystore or/and secDat file incorrect: " + e);
        }
    }

    private Licence licenceCreator() {
        String licenceUid = console.getLine("Enter licence UID");
        String customerId = console.getLine("Enter customer ID");
        String customerName = console.getLine("Enter customer name");
        String customerAddress = console.getLine("Enter customer address");
        String customerPhone = console.getLine("Enter customer phone");
        String productName = console.getLine("Enter product name");
        String productVersion = console.getLine("Enter product version");
        String licenceValidFrom = console.getLine("Licence valid from (YYYY-MM-DD hh:mm:ss)");
        String licenceValidTo = console.getLine("Licence valid to (YYYY-MM-DD hh:mm:ss)");
        String properties = propertiesCreator();

        licenceBuilder
                .withLicenceUID(licenceUid)
                .withCustomerID(customerId)
                .withCustomerName(customerName)
                .withCustomerAddress(customerAddress)
                .withCustomerPhone(customerPhone)
                .withProductName(productName)
                .withProductVersion(productVersion)
                .withStartOfValid(licenceValidFrom)
                .withEndOfValid(licenceValidTo)
                .withProperties(properties);

        return licenceBuilder.build();
    }

    private String propertiesCreator() {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("Enter in line one property with given scheme: KEY=VALUE.");
        System.out.println("When finish enter 'q'\n");

        String propertyLine = StringUtils.EMPTY;
        while (!"q".equals(propertyLine)) {
            stringBuilder.append(propertyLine).append("\n");
            propertyLine = console.getLine("Enter KEY=VALUE or 'q'");
        }
        return stringBuilder.toString();
    }
}
