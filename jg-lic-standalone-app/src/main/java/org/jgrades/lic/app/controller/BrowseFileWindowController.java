/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

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

