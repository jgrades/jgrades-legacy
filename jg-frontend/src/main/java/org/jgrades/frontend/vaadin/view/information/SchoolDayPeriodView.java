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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.jgrades.data.api.entities.SchoolDayPeriod;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.frontend.vaadin.DashboardUI;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.frontend.vaadin.view.DashboardViewType;
import org.jgrades.rest.client.admin.general.PeriodsServiceClient;
import org.jgrades.rest.client.admin.general.WorkingDaysServiceClient;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class SchoolDayPeriodView extends VerticalLayout implements View {
    private PeriodsServiceClient periodsServiceClient;
    private WorkingDaysServiceClient workingDaysServiceClient;
    private User user;

    public SchoolDayPeriodView() {
        this.setSizeUndefined();
        //setSizeFull();
        setMargin(new MarginInfo(true));
        addStyleName("transactions");
        DashboardEventBus.register(this);

        user = (User) VaadinSession.getCurrent().getAttribute("jgUser");
        periodsServiceClient = ((DashboardUI) UI.getCurrent()).getPeriodsServiceClient();
        workingDaysServiceClient = ((DashboardUI) UI.getCurrent()).getWorkingDaysServiceClient();

        initData();
    }

    private void initData() {
        boolean editable = false;

        if (user.getRoles().containsKey(JgRole.ADMINISTRATOR) ||
                user.getRoles().containsKey(JgRole.MANAGER)) {
            editable = true;
        }


///////////////////////////////////////////////////////////

        Table table = new Table("Periods");
        table.addContainerProperty("From", LocalTime.class, null);
        table.addContainerProperty("To", LocalTime.class, null);

        List<SchoolDayPeriod> periods = periodsServiceClient.getAll();

        for (SchoolDayPeriod period : periods) {
            table.addItem(new Object[]{period.getStartTime(), period.getEndTime()}, period);
        }
        table.setPageLength(table.size());

        final boolean finalEditable = editable;
        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if (finalEditable) {
                    PeriodsEditWindow w = new PeriodsEditWindow(periodsServiceClient,
                            (SchoolDayPeriod) valueChangeEvent.getProperty().getValue(), finalEditable);
                    UI.getCurrent().addWindow(w);
                    w.focus();
                }
            }
        });

        addComponent(table);

        if (editable) {
            Button addButton = new Button("Add new period", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    PeriodsEditWindow w = new PeriodsEditWindow(periodsServiceClient,
                            new SchoolDayPeriod(), finalEditable);
                    UI.getCurrent().addWindow(w);
                    w.focus();
                }
            });
            addComponent(addButton);
        }

        ////////////////////////////////////////////////
        Set<DayOfWeek> workingDays = workingDaysServiceClient.getWorkingDays();
        FormLayout daysForm = new FormLayout();
        final List<CheckBox> checkBoxes = Lists.newArrayList();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            CheckBox checkBox = new CheckBox(dayOfWeek.toString());
            checkBoxes.add(checkBox);
            if (workingDays.contains(dayOfWeek)) {
                checkBox.setValue(true);
            }
            checkBox.setEnabled(editable);
            daysForm.addComponent(checkBox);
        }
        Button saveDaysButton = new Button("Save days", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Set<DayOfWeek> newDays = Sets.newHashSet();
                for (CheckBox checkBox : checkBoxes) {
                    if (checkBox.getValue() == true) {
                        newDays.add(DayOfWeek.valueOf(checkBox.getCaption()));
                    }
                }
                workingDaysServiceClient.setWorkingDays(newDays);
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.SCHOOLDAYS_AND_PERIODS_HOME.getViewName());
            }
        });
        if (editable) {
            daysForm.addComponent(saveDaysButton);
        }
        addComponent(daysForm);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
