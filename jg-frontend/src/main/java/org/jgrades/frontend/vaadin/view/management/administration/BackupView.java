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

import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.jgrades.backup.api.entities.Backup;
import org.jgrades.data.api.entities.User;
import org.jgrades.frontend.vaadin.DashboardUI;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.frontend.vaadin.view.DashboardViewType;
import org.jgrades.rest.client.backup.BackupServiceClient;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BackupView extends VerticalLayout implements View {
    private BackupServiceClient backupServiceClient;
    private User user;

    public BackupView() {
        setSizeUndefined();
        setMargin(new MarginInfo(true));
        addStyleName("transactions");
        DashboardEventBus.register(this);

        user = (User) VaadinSession.getCurrent().getAttribute("jgUser");
        backupServiceClient = ((DashboardUI) UI.getCurrent()).getBackupServiceClient();

        initData();
    }

    private void initData() {
        Table table = new Table("Back-ups");
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Status", String.class, null);
        table.addContainerProperty("Scheduled time", String.class, null);

        List<Backup> backups = backupServiceClient.getAll();

        for (Backup backup : backups) {
            table.addItem(new Object[]{backup.getName(),
                    backup.getStatus().toString(),
                    backup.getScheduledDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)}, backup);
        }
        table.setPageLength(table.size());

        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                BackupWindow w = new BackupWindow(backupServiceClient,
                        (Backup) valueChangeEvent.getProperty().getValue());
                UI.getCurrent().addWindow(w);
                w.focus();
            }
        });

        addComponent(table);

        Button addButton = new Button("Make backup", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                backupServiceClient.makeBackup();
                Notification.show("Back-up scheduled!");
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.BACKUP_HOME.getViewName());
            }
        });
        addComponent(addButton);

        Button refreshButton = new Button("Refresh backup directory", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                backupServiceClient.refresh();
                Notification.show("Back-up dir refreshed!");
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.BACKUP_HOME.getViewName());
            }
        });
        addComponent(refreshButton);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
