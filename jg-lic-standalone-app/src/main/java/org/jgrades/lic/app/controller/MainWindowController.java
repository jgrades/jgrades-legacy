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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jgrades.lic.api.crypto.encrypt.LicenceEncryptionService;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.app.utils.DialogFactory;
import org.jgrades.lic.app.utils.FileChooserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.jgrades.lic.app.utils.AppConstants.LICENCE_FORM_FXML_PATH;
import static org.jgrades.lic.app.utils.AppConstants.OPENING_LIC_WINDOW_FXML_PATH;

public class MainWindowController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindowController.class);

    private ApplicationMode applicationMode;

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TextField secDatField;
    @FXML
    private TextField keystoreField;
    @FXML
    private Button mainActionButton;
    @FXML
    private Button browseKeystoreButton;
    @FXML
    private Button browseSecDatButton;

    private LicenceFormController licenceFormController;
    private OpeningLicenceWindowController openingLicenceWindowController;

    private LicenceEncryptionService licenceEncryptionService = new LicenceEncryptionService();

    @FXML
    void initialize() throws IOException {
        FXMLLoader loader =
                new FXMLLoader(this.getClass().getClassLoader().getResource(LICENCE_FORM_FXML_PATH));
        Pane licenceFormPane = loader.load();
        mainBorderPane.setCenter(licenceFormPane);
        licenceFormController = loader.<LicenceFormController>getController();
        setApplicationMode(ApplicationMode.NEW_MODE);
    }

    @FXML
    void makeMainAction(ActionEvent event) {
        if (applicationMode == ApplicationMode.NEW_MODE) {
            String keystorePath = keystoreField.getText();
            String secDatPath = secDatField.getText();

            File licenceFile = FileChooserFactory.showSaveDialog("Choose location for licence file", event);
            if (Optional.ofNullable(licenceFile).isPresent()) {
                try {
                    Licence licence = licenceFormController.getLicenceModel();
                    licenceEncryptionService.encryptAndSign(licence, keystorePath, secDatPath, licenceFile.getAbsolutePath());
                    DialogFactory.showInformationDialog("Licence and its signature saved correctly.");
                } catch (Exception e) {
                    DialogFactory.showExceptionDialog(e);
                }
            }
        } else {
            openingLicenceWindowController.verifySignature();
        }
    }

    @FXML
    void browseKeystoreAction(ActionEvent event) {
        File chosenFile = FileChooserFactory.showOpenDialog("Choose keystore file", event);
        if (Optional.ofNullable(chosenFile).isPresent()) {
            keystoreField.setText(chosenFile.getAbsolutePath());
        }
    }

    @FXML
    void browseSecDatAction(ActionEvent event) {
        File chosenFile = FileChooserFactory.showOpenDialog("Choose security data file", event);
        if (Optional.ofNullable(chosenFile).isPresent()) {
            secDatField.setText(chosenFile.getAbsolutePath());
        }
    }

    @FXML
    void newLicenceAction(ActionEvent event) { //NOSONAR
        setApplicationMode(ApplicationMode.NEW_MODE);
        cleanData();
        licenceFormController.clearData();
        keystoreField.setEditable(true);
        secDatField.setEditable(true);
        browseKeystoreButton.setDisable(false);
        browseSecDatButton.setDisable(false);
    }

    @FXML
    void openLicenceAction(ActionEvent event) { //NOSONAR
        setApplicationMode(ApplicationMode.OPEN_MODE);
    }

    public void setApplicationMode(ApplicationMode applicationMode) {
        this.applicationMode = applicationMode;
        if (this.applicationMode == ApplicationMode.NEW_MODE) {
            mainActionButton.setText("Save");
        } else {
            FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource(OPENING_LIC_WINDOW_FXML_PATH));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                LOGGER.error("Loading window for opening licence failed", e);
                return;
            }
            Stage stage = new Stage();
            stage.setTitle("Open licence");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            openingLicenceWindowController = loader.<OpeningLicenceWindowController>getController();
            openingLicenceWindowController.init(this);
        }
    }

    protected void setLicence(Licence licence, String keystorePath, String securityDataPath) {
        licenceFormController.setLicenceModel(licence);
        keystoreField.setEditable(false);
        keystoreField.setText(keystorePath);
        secDatField.setEditable(false);
        secDatField.setText(securityDataPath);
        browseKeystoreButton.setDisable(true);
        browseSecDatButton.setDisable(true);
        mainActionButton.setText("Valid signature");
    }

    private void cleanData() {
        keystoreField.clear();
        secDatField.clear();
    }

    private enum ApplicationMode {
        NEW_MODE, OPEN_MODE
    }
}
