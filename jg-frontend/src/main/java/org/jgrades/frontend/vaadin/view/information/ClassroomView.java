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

import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.jgrades.data.api.entities.Classroom;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.frontend.vaadin.DashboardUI;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.rest.client.admin.general.ClassroomServiceClient;

import java.util.List;

public class ClassroomView extends VerticalLayout implements View {
    private ClassroomServiceClient classroomServiceClient;
    private User user;

    public ClassroomView() {
        setSizeFull();
        setMargin(new MarginInfo(true));
        addStyleName("transactions");
        DashboardEventBus.register(this);

        user = (User) VaadinSession.getCurrent().getAttribute("jgUser");
        classroomServiceClient = ((DashboardUI) UI.getCurrent()).getClassroomServiceClient();

        initData();
    }

    private void initData() {
        boolean editable = false;

        if (user.getRoles().containsKey(JgRole.ADMINISTRATOR) ||
                user.getRoles().containsKey(JgRole.MANAGER)) {
            editable = true;
        }
        Table table = new Table("Classrooms");
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Building", String.class, null);

        List<Classroom> classrooms = classroomServiceClient.getAll();

        for (Classroom classroom : classrooms) {
            table.addItem(new Object[]{classroom.getName(), classroom.getBuilding()}, classroom);
        }
        table.setPageLength(table.size());

        final boolean finalEditable = editable;
        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if (finalEditable) {
                    ClassroomEditWindow w = new ClassroomEditWindow(classroomServiceClient,
                            (Classroom) valueChangeEvent.getProperty().getValue(), finalEditable);
                    UI.getCurrent().addWindow(w);
                    w.focus();
                }
            }
        });

        addComponent(table);

        if (editable) {
            Button addButton = new Button("Add new classroom", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    ClassroomEditWindow w = new ClassroomEditWindow(classroomServiceClient,
                            new Classroom(), finalEditable);
                    UI.getCurrent().addWindow(w);
                    w.focus();
                }
            });
            addComponent(addButton);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
