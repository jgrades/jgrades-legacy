package org.jgrades.lic.app.cli;

import org.apache.commons.lang3.StringUtils;
import org.jgrades.lic.api.crypto.encrypt.LicenceEncryptionService;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.app.utils.LicenceBuilder;

public class NewLicenceAction implements ApplicationAction {
    public static final String SUCCESS_MESSAGE = "SUCCESS! Licence and signature saved.";
    public static final String GENERAL_ERROR_MESSAGE = "Operation interrupted by error: ";

    private LicenceBuilder licenceBuilder = new LicenceBuilder();

    private ConsoleApplication console;
    private LicenceEncryptionService licenceEncryptionService = new LicenceEncryptionService();

    public NewLicenceAction(ConsoleApplication consoleApplication) {
        this.console = consoleApplication;
    }

    @Override
    public void printDescription() {
        System.out.println("GENERATION OF NEW LICENCE");
        System.out.println("=========================");
    }

    @Override
    public void start() {
        try {
            String keystorePath = console.getLine("Enter keystore path");
            String secDatPath = console.getLine("Enter secure data path");

            Licence licence = licenceCreator();

            String licencePath = console.getLine("Enter path to save licence");
            licenceEncryptionService.encryptAndSign(licence, keystorePath, secDatPath, licencePath);
            System.out.println(SUCCESS_MESSAGE);
        } catch (Exception e) {
            System.err.println(GENERAL_ERROR_MESSAGE + e);
        }
    }

    private Licence licenceCreator() {
        licenceBuilder
                .withLicenceUID(console.getLine("Enter licence UID"))
                .withCustomerID(console.getLine("Enter customer ID"))
                .withCustomerName(console.getLine("Enter customer name"))
                .withCustomerAddress(console.getLine("Enter customer address"))
                .withCustomerPhone(console.getLine("Enter customer phone"))
                .withProductName(console.getLine("Enter product name"))
                .withProductVersion(console.getLine("Enter product version"))
                .withStartOfValid(console.getLine("Licence valid from (YYYY-MM-DD hh:mm:ss)"))
                .withEndOfValid(console.getLine("Licence valid to (YYYY-MM-DD hh:mm:ss)"))
                .withProperties(propertiesCreator());

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
