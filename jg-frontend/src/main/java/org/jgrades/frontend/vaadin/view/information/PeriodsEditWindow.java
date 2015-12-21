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
import org.jgrades.data.api.entities.SchoolDayPeriod;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.frontend.vaadin.view.DashboardViewType;
import org.jgrades.rest.client.admin.general.PeriodsServiceClient;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class PeriodsEditWindow extends Window {
    public static final String ID = "PeriodsEditWindow";


    public PeriodsEditWindow(final PeriodsServiceClient periodsServiceClient, final SchoolDayPeriod period, boolean editable) {
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
        final TextField fromField = new TextField("From");
        if (period != null && period.getStartTime() != null) {
            fromField.setValue(period.getStartTime().format(DateTimeFormatter.ISO_LOCAL_TIME));
        }
        fromField.setEnabled(editable);
        form.addComponent(fromField);

        final TextField toField = new TextField("To");
        if (period != null && period.getEndTime() != null) {
            toField.setValue(period.getEndTime().format(DateTimeFormatter.ISO_LOCAL_TIME));
        }
        toField.setEnabled(editable);
        form.addComponent(toField);

        Button saveButton = new Button("Save", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (period != null) {
                    period.setStartTime(LocalTime.parse(fromField.getValue(), DateTimeFormatter.ISO_LOCAL_TIME));
                    period.setEndTime(LocalTime.parse(toField.getValue(), DateTimeFormatter.ISO_LOCAL_TIME));
                    periodsServiceClient.insertOrUpdate(period);
                    Notification.show("Updated!");
                    close();
                    UI.getCurrent().getNavigator()
                            .navigateTo(DashboardViewType.SCHOOLDAYS_AND_PERIODS_HOME.getViewName());
                }
            }
        });

        Button removeButton = new Button("Remove", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                periodsServiceClient.remove(Arrays.asList(period.getId()));
                Notification.show("Removed!");
                close();
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.SCHOOLDAYS_AND_PERIODS_HOME.getViewName());
            }
        });
        saveButton.setVisible(editable);
        removeButton.setVisible(editable);
        form.addComponent(saveButton);
        form.addComponent(removeButton);
        form.setSizeUndefined();
        setContent(form);

        setSizeUndefined();

    }
}
