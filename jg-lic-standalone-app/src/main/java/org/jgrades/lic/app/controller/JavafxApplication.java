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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jgrades.lic.app.launch.LicenceApplication;

import static org.jgrades.lic.app.utils.AppConstants.*;

public class JavafxApplication extends Application implements LicenceApplication {
    private static boolean testingModeEnabled = false;

    private static Scene scene;
    private static Stage stage;

    public static boolean isTestingModeEnabled() {
        return testingModeEnabled;
    }

    public static Scene getScene() {
        return scene;
    }

    public static Stage getStage() {
        return stage;
    }

    private void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setStage(primaryStage);
        FXMLLoader loader =
                new FXMLLoader(this.getClass().getClassLoader().getResource(MAIN_WINDOW_FXML_PATH));

        scene = new Scene(loader.load(), MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
        prepareMainWindow(primaryStage);
    }

    private void prepareMainWindow(Stage primaryStage) {
        primaryStage.setScene(scene);
        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void runApp() {
        launch();
    }
}
