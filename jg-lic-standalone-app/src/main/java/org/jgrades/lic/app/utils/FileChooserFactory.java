/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.app.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jgrades.lic.app.controller.BrowseFileWindowController;
import org.jgrades.lic.app.controller.JavafxApplication;

import java.io.File;
import java.io.IOException;

import static org.jgrades.lic.app.utils.AppConstants.BROWSE_FILE_MOCK_WINDOW_FXML_PATH;

public class FileChooserFactory {
    public static File showOpenDialog(String title, ActionEvent event) {
        if (JavafxApplication.isTestingModeEnabled()) {
            return new File(getPathFromMockedFileChooser());
        } else {
            FileChooser fileChooser = getFileChooserWithTitle(title);
            return fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
        }
    }

    public static File showSaveDialog(String title, ActionEvent event) {
        if (JavafxApplication.isTestingModeEnabled()) {
            return new File(getPathFromMockedFileChooser());
        } else {
            FileChooser fileChooser = getFileChooserWithTitle(title);
            return fileChooser.showSaveDialog(((Node) event.getTarget()).getScene().getWindow());
        }
    }

    private static FileChooser getFileChooserWithTitle(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        return fileChooser;
    }

    private static String getPathFromMockedFileChooser() {
        FXMLLoader loader = new FXMLLoader(FileChooserFactory.class.getClassLoader().getResource(BROWSE_FILE_MOCK_WINDOW_FXML_PATH));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Browse file mock window");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        BrowseFileWindowController controller = loader.<BrowseFileWindowController>getController();
        return controller.getEnteredPath();
    }
}
