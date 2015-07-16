package org.jgrades.lic.app.cli;

import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;
import org.jgrades.lic.app.service.crypto.KeyStoreContentExtractor;
import org.jgrades.lic.app.service.crypto.decrypt.LicenceDecryptionProvider;
import org.jgrades.lic.app.service.crypto.decrypt.SignatureValidator;

import java.io.File;
import java.io.IOException;

public class OpenLicenceAction implements ApplicationAction {
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
                System.out.println("Signature is correct");
            } else {
                System.out.println("Warning! Signature is not correct");
            }

            Licence licence = decryptionProvider.decrypt(new File(licencePath));
            prettyPrint(licence);

            System.out.println("Done.");
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
        System.out.println("Licence valid from: " + licence.getProduct().getValidFrom());
        System.out.println("Licence valid to: " + licence.getProduct().getValidTo());
        System.out.println("Licence properties:");
        for (LicenceProperty property : licence.getProperties()) {
            System.out.println(property.getName() + "=>" + property.getValue());
        }
    }
}
