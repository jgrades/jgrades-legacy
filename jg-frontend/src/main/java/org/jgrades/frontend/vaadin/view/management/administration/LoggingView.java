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
import org.jgrades.frontend.vaadin.DashboardUI;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.logging.model.JgLogLevel;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.model.LoggingStrategy;
import org.jgrades.rest.client.logging.LoggerConfigServiceClient;

public class LoggingView extends VerticalLayout implements View {
    private LoggerConfigServiceClient loggerConfigServiceClient;
    private User user;

    public LoggingView() {
        setSizeFull();
        setMargin(new MarginInfo(true));
        addStyleName("transactions");
        DashboardEventBus.register(this);

        user = (User) VaadinSession.getCurrent().getAttribute("jgUser");
        loggerConfigServiceClient = ((DashboardUI) UI.getCurrent()).getLoggerConfigServiceClient();

        initData();
    }

    private void initData() {
        FormLayout form = new FormLayout();
        final LoggingConfiguration configuration = loggerConfigServiceClient.getConfiguration();

        final ComboBox loggingStrategy = new ComboBox("Logging strategy");
        loggingStrategy.addItems(LoggingStrategy.values());
        loggingStrategy.setValue(configuration.getLoggingStrategy());
        form.addComponent(loggingStrategy);
        loggingStrategy.setEnabled(true);

        final ComboBox loggingLevel = new ComboBox("Logging level");
        loggingLevel.addItems(JgLogLevel.values());
        loggingLevel.setValue(configuration.getLevel());
        form.addComponent(loggingLevel);
        loggingLevel.setEnabled(true);

        final TextField maxFileSize = new TextField("Max file size");
        form.addComponent(maxFileSize);
        maxFileSize.setValue(configuration.getMaxFileSize());
        maxFileSize.setEnabled(true);

        final TextField maxDays = new TextField("Max days");
        form.addComponent(maxDays);
        maxDays.setValue(configuration.getMaxDays().toString());
        maxDays.setEnabled(true);

        Button saveButton = new Button("Save", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                configuration.setLoggingStrategy(LoggingStrategy.valueOf(loggingStrategy.getValue().toString()));
                configuration.setLevel(JgLogLevel.valueOf(loggingLevel.getValue().toString()));
                configuration.setMaxFileSize(maxFileSize.getValue());
                configuration.setMaxDays(Integer.parseInt(maxDays.getValue()));
                loggerConfigServiceClient.setNewConfiguration(configuration);
                Notification.show("Saved!");
            }
        });
        form.addComponent(saveButton);

        addComponent(form);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
