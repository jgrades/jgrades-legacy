/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.frontend.vaadin.view.management.administration;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.DataSourceDetails;
import org.jgrades.frontend.vaadin.DashboardUI;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.rest.client.data.DataSourceDetailsServiceClient;

public class DatasourceView extends VerticalLayout implements View {
    private DataSourceDetailsServiceClient dataSourceDetailsServiceClient;
    private User user;


    public DatasourceView() {
        setSizeFull();
        setMargin(new MarginInfo(true));
        addStyleName("transactions");
        DashboardEventBus.register(this);

        user = (User) VaadinSession.getCurrent().getAttribute("jgUser");
        dataSourceDetailsServiceClient = ((DashboardUI) UI.getCurrent()).getDataSourceDetailsServiceClient();

        initData();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    private void initData() {
        FormLayout form = new FormLayout();
        final DataSourceDetails dataSourceDetails = dataSourceDetailsServiceClient.getDataSourceDetails();

        final TextField urlField = new TextField("URL");
        form.addComponent(urlField);
        urlField.setValue(dataSourceDetails.getUrl());
        urlField.setEnabled(true);

        final TextField usernameField = new TextField("Username");
        form.addComponent(usernameField);
        usernameField.setValue(dataSourceDetails.getUsername());
        usernameField.setEnabled(true);

        final PasswordField passwordField = new PasswordField("Password");
        form.addComponent(passwordField);
        passwordField.setEnabled(true);

        Button saveButton = new Button("Save", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                dataSourceDetails.setUrl(urlField.getValue());
                dataSourceDetails.setUrl(usernameField.getValue());
                dataSourceDetails.setPassword(passwordField.getValue());
                dataSourceDetailsServiceClient.setDataSourceDetails(dataSourceDetails);
                Notification.show("Saved! Now backend will reboot");
            }
        });

        Button testButton = new Button("Test connection with current DB", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean result = dataSourceDetailsServiceClient.testConnection();
                if (result) {
                    Notification.show("Test connection PASSED");
                } else {
                    Notification.show("Test connection FAILED. Please check logs on backend for more details");
                }
            }
        });
        form.addComponent(saveButton);
        form.addComponent(testButton);

        addComponent(form);
    }
}
