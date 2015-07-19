package org.jgrades.lic.app.utils;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static junit.framework.Assert.fail;

public class DialogFactoryIT extends GuiTest {
    @Test
    public void shouldDisplayInformationDialog() throws Exception {
        Platform.runLater(() -> {
            DialogFactory.showInformationDialog("Information message");
        });
    }

    @Test
    public void shouldDisplayWarningDialog() throws Exception {
        Platform.runLater(() -> {
            DialogFactory.showWarningDialog("Warning message");
        });
    }

    @Test
    public void shouldDisplayExceptionDialog() throws Exception {
        Platform.runLater(() -> {
            DialogFactory.showExceptionDialog(new NullPointerException("Exception message"));
        });
    }

    @Override
    protected Parent getRootNode() {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(this.getClass().getClassLoader().getResource("sample.fxml"));
            return parent;
        } catch (IOException ex) {
            fail();
        }
        return parent;
    }
}
