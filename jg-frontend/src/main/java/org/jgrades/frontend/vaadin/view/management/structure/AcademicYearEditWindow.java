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
import org.jgrades.data.api.entities.AcademicYear;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.frontend.vaadin.view.DashboardViewType;
import org.jgrades.rest.client.admin.structures.AcademicYearServiceClient;

import java.util.Arrays;

public class AcademicYearEditWindow extends Window {
    public static final String ID = "AcademicYearEditWindow";

    public AcademicYearEditWindow(final AcademicYearServiceClient academicYearServiceClient, final AcademicYear academicYear, boolean editable) {
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
        if (academicYear != null) {
            nameField.setValue(academicYear.getName());
        }
        nameField.setEnabled(editable);
        form.addComponent(nameField);

        final CheckBox isActive = new CheckBox("Is active");
        if (academicYear != null) {
            isActive.setValue(academicYear.isActive());
        }
        isActive.setEnabled(editable);
        form.addComponent(isActive);

        Button saveButton = new Button("Save", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (academicYear != null) {
                    academicYear.setName(nameField.getValue());
                    academicYear.setActive(isActive.getValue());
                    academicYearServiceClient.insertOrUpdate(academicYear);
                    Notification.show("Updated!");
                    close();
                    UI.getCurrent().getNavigator()
                            .navigateTo(DashboardViewType.ACADAMIC_YEAR_HOME.getViewName());
                }
            }
        });

        Button removeButton = new Button("Remove", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                academicYearServiceClient.remove(Arrays.asList(academicYear.getId()));
                Notification.show("Removed!");
                close();
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.ACADAMIC_YEAR_HOME.getViewName());
            }
        });
        saveButton.setVisible(editable);
        removeButton.setVisible(editable);
        form.addComponent(saveButton);
        form.addComponent(removeButton);

        setContent(form);
    }
}
