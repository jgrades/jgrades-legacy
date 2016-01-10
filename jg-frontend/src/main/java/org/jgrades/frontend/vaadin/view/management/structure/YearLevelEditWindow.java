/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.frontend.vaadin.view.management.structure;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.jgrades.data.api.entities.YearLevel;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.frontend.vaadin.view.DashboardViewType;
import org.jgrades.rest.client.admin.structures.YearLevelServiceClient;

import java.util.Arrays;

public class YearLevelEditWindow extends Window {
    public static final String ID = "YearLevelEditWindow";

    public YearLevelEditWindow(final YearLevelServiceClient yearLevelServiceClient, final YearLevel yearLevel, boolean editable) {
        addStyleName("profile-window");
        setId(ID);

        setSizeFull();
        addStyleName("transactions");
        DashboardEventBus.register(this);

        Responsive.makeResponsive(this);

        setModal(true);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(true);
//        setHeight(90.0f, Unit.PERCENTAGE);

        FormLayout form = new FormLayout();
        form.setMargin(new MarginInfo(true));
        final TextField nameField = new TextField("Name");
        if (yearLevel != null) {
            nameField.setValue(yearLevel.getName());
        }
        nameField.setEnabled(editable);
        form.addComponent(nameField);

        Button saveButton = new Button("Save", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (yearLevel != null) {
                    yearLevel.setName(nameField.getValue());
                    yearLevelServiceClient.insertOrUpdate(yearLevel);
                    Notification.show("Updated!");
                    close();
                    UI.getCurrent().getNavigator()
                            .navigateTo(DashboardViewType.YEAR_LEVEL_HOME.getViewName());
                }
            }
        });

        Button removeButton = new Button("Remove", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                yearLevelServiceClient.remove(Arrays.asList(yearLevel.getId()));
                Notification.show("Removed!");
                close();
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.YEAR_LEVEL_HOME.getViewName());
            }
        });
        saveButton.setVisible(editable);
        removeButton.setVisible(editable);
        form.addComponent(saveButton);
        form.addComponent(removeButton);

        setContent(form);
    }
}
