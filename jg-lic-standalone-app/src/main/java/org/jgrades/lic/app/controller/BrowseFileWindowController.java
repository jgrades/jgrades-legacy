package org.jgrades.lic.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;

public class BrowseFileWindowController {
    @FXML
    private TextField mockField;

    public String getEnteredPath() {
        return StringUtils.defaultString(mockField.getText(), StringUtils.EMPTY);
    }

    @FXML
    void closeWindow(ActionEvent event) {
        ((Node) event.getTarget()).getScene().getWindow().hide();
    }
}

