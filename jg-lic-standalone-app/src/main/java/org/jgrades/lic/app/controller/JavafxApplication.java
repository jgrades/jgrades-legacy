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

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        FXMLLoader loader =
                new FXMLLoader(this.getClass().getClassLoader().getResource(MAIN_WINDOW_FXML_PATH));

        scene = new Scene(loader.load(), MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
        prepareMainWindow(primaryStage, scene);
    }

    private void prepareMainWindow(Stage primaryStage, Scene scene) {
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
