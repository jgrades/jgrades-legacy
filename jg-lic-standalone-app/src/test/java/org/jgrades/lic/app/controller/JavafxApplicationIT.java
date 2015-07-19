package org.jgrades.lic.app.controller;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.loadui.testfx.GuiTest.find;

public class JavafxApplicationIT {
    private static GuiTest controller;
    private final String licenceUID = "1234";
    private final String customerId = "789";
    private final String customerName = "XIV LO Wrocław";
    private final String customerAddress = "Brucnera 10";
    private final String customerPhone = "+48 71 7986890";
    private final String productName = "JG-BASE";
    private final String productVersion = "0.4";
    private final String validFrom = "17.07.2015";
    private final String validTo = "17.08.2015";
    private final String mac = "00:0A:E6:3E:FD:E1";
    private final String macProperty = "mac=" + mac;
    private final String expiredDaysKey = "expiredDays";
    private final String expiredDaysProperty = expiredDaysKey + "=14";
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    private File keystore;
    private File secData;

    @BeforeClass
    public static void setAfter() throws Exception {
        FXTestUtils.launchApp(JavafxApplication.class);
        Thread.sleep(5000);
        controller = new GuiTest() {
            @Override
            protected Parent getRootNode() {
                return JavafxApplication.getScene().getRoot();
            }
        };
    }

    @AfterClass
    public static void shutdownAll() throws InterruptedException {
        Platform.runLater(() -> JavafxApplication.getStage().close());
    }

    @Before
    public void setUp() throws Exception {
        keystore = new ClassPathResource("jg-ks-test.jceks").getFile();
        secData = new ClassPathResource("sec-test.dat").getFile();
    }

    @Test
    public void shouldFillForm_saveLicence_thenOpen_andVerifySignature() throws Throwable {
        ReflectionTestUtils.setField(new JavafxApplication(), "testingModeEnabled", true);
        controller.click("#newLicenceButton");
        checkIsFormEditable(true);

        assertThat(((Button) find("#mainActionButton")).getText()).isEqualTo("Save");

        File licenceFile = tempFolder.newFile();
        ((TextField) find("#keystoreField")).setText(keystore.getAbsolutePath());
        ((TextField) find("#secDatField")).setText(secData.getAbsolutePath());
        ((TextField) find("#licenceUidField")).setText(licenceUID);
        ((TextField) find("#customerIdField")).setText(customerId);
        ((TextField) find("#customerNameField")).setText(customerName);
        ((TextField) find("#customerAddrField")).setText(customerAddress);
        ((TextField) find("#customerPhoneField")).setText(customerPhone);
        ((TextField) find("#productNameField")).setText(productName);
        ((TextField) find("#productVersionField")).setText(productVersion);
        controller.click("#licValidFromDate").type(validFrom);
        controller.click("#licValidToDate").type(validTo);
        ((TextArea) find("#propertiesArea")).setText(macProperty + "\n" + expiredDaysProperty);

        controller.click("#mainActionButton");
        Thread.sleep(5000);
        ((TextField) find("#mockField")).setText(licenceFile.getAbsolutePath());
        controller.click("#mockButton");

        File signatureFile = new File(licenceFile.getAbsolutePath() + ".sign");

        assertThat(licenceFile.length()).isPositive();
        assertThat(signatureFile).exists();
        assertThat(signatureFile.length()).isPositive();

        Thread.sleep(1000);
        controller.closeCurrentWindow();
        Thread.sleep(1000);
        controller.click("#newLicenceButton");
        controller.click("#openLicenceButton");
        ((TextField) find("#licenceField")).setText(licenceFile.getAbsolutePath());
        ((TextField) find("#signatureField")).setText(signatureFile.getAbsolutePath());
        ((TextField) find("#keystoreFileField")).setText(keystore.getAbsolutePath());
        ((TextField) find("#securityDataField")).setText(secData.getAbsolutePath());
        controller.click("#openButton");
        Thread.sleep(2000);
        assertThat(((Button) find("#mainActionButton")).getText()).isEqualTo("Valid signature");
        checkIsFormEditable(false);

        controller.click("#mainActionButton");
        controller.closeCurrentWindow();

        FileUtils.copyInputStreamToFile(new ByteArrayInputStream(new byte[]{1, 2, 3}), signatureFile);

        controller.click("#mainActionButton");
        controller.closeCurrentWindow();

        signatureFile.delete();
        controller.click("#mainActionButton");
        controller.closeCurrentWindow();
    }

    @Test
    public void allNativeFileChoosersShouldBeVisible_whenButtonBrowsePressed() throws Throwable {
        ReflectionTestUtils.setField(new JavafxApplication(), "testingModeEnabled", false);
        controller.click("#newLicenceButton");
        controller.click("#browseKeystoreButton");
        controller.closeCurrentWindow();
        controller.click("#browseSecDatButton");
        controller.closeCurrentWindow();
        controller.click("#mainActionButton");
        controller.closeCurrentWindow();
        controller.click("#openLicenceButton");
        Thread.sleep(1000);
        controller.click("#browseLicenceFileButton");
        controller.closeCurrentWindow();
        controller.click("#browseSignatureFileButton");
        controller.closeCurrentWindow();
        controller.click("#browseKeystoreFileButton");
        controller.closeCurrentWindow();
        controller.click("#browseSecurityDataFileButton");
        controller.closeCurrentWindow();
        controller.closeCurrentWindow();
    }

    private void checkIsFormEditable(boolean value) {
        assertThat(((TextField) find("#keystoreField")).isEditable()).isEqualTo(value);
        assertThat(((TextField) find("#secDatField")).isEditable()).isEqualTo(value);
        assertThat(((TextField) find("#licenceUidField")).isEditable()).isEqualTo(value);
        assertThat(((TextField) find("#customerIdField")).isEditable()).isEqualTo(value);
        assertThat(((TextField) find("#customerNameField")).isEditable()).isEqualTo(value);
        assertThat(((TextField) find("#customerAddrField")).isEditable()).isEqualTo(value);
        assertThat(((TextField) find("#customerPhoneField")).isEditable()).isEqualTo(value);
        assertThat(((TextField) find("#productNameField")).isEditable()).isEqualTo(value);
        assertThat(((TextField) find("#productVersionField")).isEditable()).isEqualTo(value);
        assertThat(((DatePicker) find("#licValidFromDate")).isEditable()).isEqualTo(value);
        assertThat(((DatePicker) find("#licValidToDate")).isEditable()).isEqualTo(value);
        assertThat(((TextArea) find("#propertiesArea")).isEditable()).isEqualTo(value);

        assertThat(((Button) find("#browseKeystoreButton")).isDisable()).isEqualTo(!value);
        assertThat(((Button) find("#browseSecDatButton")).isDisable()).isEqualTo(!value);
    }
}
