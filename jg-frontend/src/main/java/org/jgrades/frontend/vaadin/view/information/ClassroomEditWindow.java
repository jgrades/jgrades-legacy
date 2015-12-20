/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.frontend.vaadin.view.information;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.jgrades.data.api.entities.Classroom;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.frontend.vaadin.view.DashboardViewType;
import org.jgrades.rest.client.admin.general.ClassroomServiceClient;

import java.util.Arrays;

public class ClassroomEditWindow extends Window {
    public static final String ID = "SubjectEditWindow";

    public ClassroomEditWindow(final ClassroomServiceClient classroomServiceClient, final Classroom classroom, boolean editable) {
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
        if (classroom != null) {
            nameField.setValue(classroom.getName());
        }
        nameField.setEnabled(editable);
        form.addComponent(nameField);

        final TextField buildingField = new TextField("Building");
        if (classroom != null) {
            buildingField.setValue(classroom.getBuilding());
        }
        buildingField.setEnabled(editable);
        form.addComponent(buildingField);

        Button saveButton = new Button("Save", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (classroom != null) {
                    classroom.setName(nameField.getValue());
                    classroom.setBuilding(buildingField.getValue());
                    classroomServiceClient.insertOrUpdate(classroom);
                    Notification.show("Updated!");
                    close();
                    UI.getCurrent().getNavigator()
                            .navigateTo(DashboardViewType.CLASSROOM_HOME.getViewName());
                }
            }
        });

        Button removeButton = new Button("Remove", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                classroomServiceClient.remove(Arrays.asList(classroom.getId()));
                Notification.show("Removed!");
                close();
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.CLASSROOM_HOME.getViewName());
            }
        });
        saveButton.setVisible(editable);
        removeButton.setVisible(editable);
        form.addComponent(saveButton);
        form.addComponent(removeButton);

        setContent(form);
    }
}
