package org.jgrades.lic.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import org.jgrades.lic.api.crypto.decrypt.LicenceDecryptionService;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.app.utils.DialogFactory;
import org.jgrades.lic.app.utils.FileChooserFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class OpeningLicenceWindowController {
    private static final String SIGNATURE_IS_VALID = "Signature is valid";
    private static final String SIGNATURE_IS_NOT_VALID = "Signature is not valid";

    @FXML
    private TextField securityDataField;

    @FXML
    private TextField keystoreFileField;

    @FXML
    private TextField signatureField;

    @FXML
    private TextField licenceField;

    private MainWindowController mainWindowController;
    private LicenceDecryptionService licenceDecryptionService = new LicenceDecryptionService();

    @FXML
    void openLicenceAction(ActionEvent event) {
        try {
            Licence licence = licenceDecryptionService.decrypt(keystoreFileField.getText(), securityDataField.getText(), licenceField.getText());
            mainWindowController.setLicence(licence, keystoreFileField.getText(), securityDataField.getText());
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            DialogFactory.showExceptionDialog(e);
        }
    }

    @FXML
    void browseLicenceFileAction(ActionEvent event) {
        browse("Choose licence file", event, licenceField);
    }

    @FXML
    void browseSignatureFileAction(ActionEvent event) {
        browse("Choose signature file", event, signatureField);
    }

    @FXML
    void browseKeystoreFileAction(ActionEvent event) {
        browse("Choose keystore file", event, keystoreFileField);
    }

    @FXML
    void browseSecurityDataFileAction(ActionEvent event) {
        browse("Choose security data file", event, securityDataField);
    }

    private void browse(String title, ActionEvent event, TextField textField) {
        File chosenFile = FileChooserFactory.showOpenDialog(title, event);
        if (Optional.ofNullable(chosenFile).isPresent()) {
            textField.setText(chosenFile.getAbsolutePath());
        }
    }

    public void init(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void verifySignature() {
        try {
            boolean validationSuccess = licenceDecryptionService.validSignature(keystoreFileField.getText(),
                    securityDataField.getText(), licenceField.getText(), signatureField.getText());
            if (validationSuccess) {
                DialogFactory.showInformationDialog(SIGNATURE_IS_VALID);
            } else {
                DialogFactory.showWarningDialog(SIGNATURE_IS_NOT_VALID);
            }
        } catch (IOException e) {
            DialogFactory.showExceptionDialog(e);
        }
    }
}
