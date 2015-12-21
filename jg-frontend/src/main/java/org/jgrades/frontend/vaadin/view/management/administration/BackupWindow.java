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

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.backup.api.entities.Backup;
import org.jgrades.backup.api.entities.BackupEvent;
import org.jgrades.backup.api.model.BackupStatus;
import org.jgrades.backup.api.model.RestoreSettings;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.frontend.vaadin.view.DashboardViewType;
import org.jgrades.rest.client.backup.BackupServiceClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class BackupWindow extends Window {
    public static final String ID = "BackupWindow";

    public BackupWindow(final BackupServiceClient backupServiceClient, final Backup backup) {
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

        VerticalLayout form = new VerticalLayout();
        form.setMargin(new MarginInfo(true));

        Table table = new Table("Events for back-up " + backup.getName());
        table.addContainerProperty("Type", String.class, null);
        table.addContainerProperty("Severity", String.class, null);
        table.addContainerProperty("Operation", String.class, null);
        table.addContainerProperty("Start time", String.class, null);
        table.addContainerProperty("End time", String.class, null);

        List<BackupEvent> events = backupServiceClient.getEvents(backup.getId());
        for (BackupEvent event : events) {
            table.addItem(new Object[]{
                    event.getEventType().toString(),
                    event.getSeverity().toString(),
                    event.getOperation().toString(),
                    event.getStartTime().format(DateTimeFormatter.BASIC_ISO_DATE),
                    nullableStop(event.getEndTime())
            }, event);
        }

        table.setPageLength(table.size());
        form.addComponent(table);

        if (backup.getStatus().equals(BackupStatus.ONGOING)) {
            Button InterruptButton = new Button("Interrupt", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    backupServiceClient.interrupt(backup.getId());
                    Notification.show("Back-up " + backup.getName() + " interrupted!");
                    UI.getCurrent().getNavigator()
                            .navigateTo(DashboardViewType.BACKUP_HOME.getViewName());
                }
            });
            form.addComponent(InterruptButton);
        }

        Button restoreButton = new Button("Restore from backup", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                backupServiceClient.restore(backup.getId(), new RestoreSettings());
                Notification.show("Back-up " + backup.getName() + " restored! Backend will reboot now");
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.BACKUP_HOME.getViewName());
            }
        });
        form.addComponent(restoreButton);

        Button removeButton = new Button("Remove backup", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                backupServiceClient.remove(Arrays.asList(backup.getId()));
                Notification.show("Back-up " + backup.getName() + " removed!");
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.BACKUP_HOME.getViewName());
            }
        });
        form.addComponent(removeButton);
        setContent(form);
    }

    private Object nullableStop(LocalDateTime endTime) {
        if (endTime == null) {
            return StringUtils.EMPTY;
        } else {
            return endTime.format(DateTimeFormatter.ISO_DATE_TIME);
        }
    }
}
