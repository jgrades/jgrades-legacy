/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.frontend.vaadin.view.management.user;

import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.RoleDetails;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.frontend.vaadin.DashboardUI;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.rest.client.admin.accounts.UserServiceClient;
import org.jgrades.rest.client.config.UserProfileServiceClient;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;

public class UserManagementView extends VerticalLayout implements View {

    private UserServiceClient userServiceClient;
    private UserProfileServiceClient userProfileServiceClient;
    private User user;

    public UserManagementView() {
        setSizeFull();
        setMargin(new MarginInfo(true));
        addStyleName("transactions");
        DashboardEventBus.register(this);

        user = (User) VaadinSession.getCurrent().getAttribute("jgUser");
        userServiceClient = ((DashboardUI) UI.getCurrent()).getUserServiceClient();
        userProfileServiceClient = ((DashboardUI) UI.getCurrent()).getUserProfileServiceClient();

        initData();
    }

    private void initData() {
        boolean editable = false;

        if (user.getRoles().containsKey(JgRole.ADMINISTRATOR) ||
                user.getRoles().containsKey(JgRole.MANAGER)) {
            editable = true;
        }
        Table table = new Table("Users");
        table.addContainerProperty("Login", String.class, null);
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Surname", String.class, null);
        table.addContainerProperty("E-mail", String.class, null);
        table.addContainerProperty("Roles", String.class, null);
        table.addContainerProperty("Is active?", Boolean.class, null);
        table.addContainerProperty("Last visit", LocalDateTime.class, null);

        List<User> users = userServiceClient.getAll();

        for (User user : users) {
            table.addItem(new Object[]{user.getLogin(), user.getName(), user.getSurname(), user.getEmail(), user.getRoles().keySet().toString(), user.isActive(), user.getLastVisit()}, user);
        }
        table.setPageLength(table.size());

        final boolean finalEditable = editable;
        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if (finalEditable) {
                    UserEditWindow w = new UserEditWindow(userServiceClient, userProfileServiceClient,
                            (User) valueChangeEvent.getProperty().getValue(), finalEditable);
                    UI.getCurrent().addWindow(w);
                    w.focus();
                }
            }
        });

        addComponent(table);

        if (editable) {
            Button addButton = new Button("Add new user", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    User user = new User();
                    user.setRoles(new EnumMap<JgRole, RoleDetails>(JgRole.class));
                    UserEditWindow w = new UserEditWindow(userServiceClient, userProfileServiceClient,
                            user, finalEditable);
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
