package org.jgrades.lic.app.cli;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.rules.TemporaryFolder;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleApplicationIT {
    @Rule
    public final TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog().muteForSuccessfulTests();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Rule
    public final TemporaryFolder tempFolder = new TemporaryFolder();

    private final String licenceUID = "1234";
    private final String customerId = "789";
    private final String customerName = "XIV LO Wroclaw";
    private final String customerAddress = "Brucnera 10";
    private final String customerPhone = "+48 71 7986890";
    private final String productName = "JG-BASE";
    private final String productVersion = "0.4";
    private final String validFrom = "2015-07-17 00:00:00";
    private final String validTo = "2015-08-17 00:00:00";
    private final String mac = "00:0A:E6:3E:FD:E1";
    private final String macProperty = "mac=" + mac;
    private final String expiredDaysKey = "expiredDays";
    private final String expiredDaysProperty = expiredDaysKey + "=14";
    private final String INVOKE_GENERATING_ACTION_CODE = "1";
    private final String INVOKE_OPENING_ACTION_CODE = "2";
    private final String INVOKE_EXIT_ACTION_CODE = "3";
    private final String endOfWritingPropertiesFlag = "q";
    private ConsoleApplication consoleApplication;

    private File keystore;
    private File secData;

    @Before
    public void setUp() throws Exception {
        this.consoleApplication = new ConsoleApplication();

        keystore = new ClassPathResource("jg-ks-test.jceks").getFile();
        secData = new ClassPathResource("sec-test.dat").getFile();
    }

    @Test
    public void shouldGenerateLicenceAndSignature_thenVerifyThemCorrectly() throws Exception {
        // given
        File licenceFile = tempFolder.newFile();
        File signatureFile = new File(licenceFile.getAbsolutePath() + ".sign");

        // then
        exit.checkAssertionAfterwards(() -> {
            thenOutputContains(NewLicenceAction.SUCCESS_MESSAGE);
            thenOutputContains(OpenLicenceAction.LICENCE_OPENED_SUCCESS_MESSAGE);
            thenOutputContains(OpenLicenceAction.SIGNATURE_VALID_SUCCESS_MESSAGE);
        });

        // when
        invokeGenerationAndOpeningWithVerification(licenceFile, signatureFile);
    }

    @Test
    public void shouldShowWarning_whenGivenSignatureIsNotVerified() throws Exception {
        // given
        File licenceFile = tempFolder.newFile();
        File notValidSignatureFile = new ClassPathResource("encrypted.lic.sign").getFile();

        // then
        exit.checkAssertionAfterwards(() -> {
            thenOutputContains(NewLicenceAction.SUCCESS_MESSAGE);
            thenOutputContains(OpenLicenceAction.LICENCE_OPENED_SUCCESS_MESSAGE);
            thenOutputContains(OpenLicenceAction.SIGNATURE_NOT_VALID_WARNING_MESSAGE);
        });

        // when
        invokeGenerationAndOpeningWithVerification(licenceFile, notValidSignatureFile);
    }

    private void invokeGenerationAndOpeningWithVerification(File licenceFile, File signatureFile) {
        // then
        exit.expectSystemExitWithStatus(0);

        exit.checkAssertionAfterwards(() -> {
            assertThat(licenceFile.length()).isPositive();
            assertThat(signatureFile).exists();

            thenOutputContains(ConsoleApplication.APPLICATION_HEADER);
            thenOutputContains(licenceUID);
            thenOutputContains(customerId);
            thenOutputContains(customerName);
            thenOutputContains(customerAddress);
            thenOutputContains(customerPhone);
            thenOutputContains(productName);
            thenOutputContains(productVersion);
            thenOutputContains(validFrom);
            thenOutputContains(validTo);
            thenOutputContains(mac);
            thenOutputContains(expiredDaysKey);
        });

        // when
        systemInMock.provideLines(
                INVOKE_GENERATING_ACTION_CODE,
                keystore.getAbsolutePath(),
                secData.getAbsolutePath(),
                licenceUID,
                customerId,
                customerName,
                customerAddress,
                customerPhone,
                productName,
                productVersion,
                validFrom,
                validTo,
                macProperty,
                expiredDaysProperty,
                endOfWritingPropertiesFlag,
                licenceFile.getAbsolutePath(),
                INVOKE_OPENING_ACTION_CODE,
                keystore.getAbsolutePath(),
                secData.getAbsolutePath(),
                licenceFile.getAbsolutePath(),
                signatureFile.getAbsolutePath(),
                INVOKE_EXIT_ACTION_CODE
        );

        consoleApplication.runApp();

    }

    private void thenOutputContains(String content) {
        assertThat(systemOutRule.getLog()).contains(content);
    }
}
