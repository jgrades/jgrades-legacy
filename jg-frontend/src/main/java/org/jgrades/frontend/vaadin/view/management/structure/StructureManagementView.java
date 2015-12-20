/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.frontend.vaadin.view.management.structure;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.UI;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.frontend.vaadin.view.DashboardViewType;

public class StructureManagementView extends GridLayout implements View {
    public StructureManagementView() {
        setSizeFull();
        setMargin(new MarginInfo(true));
        addStyleName("transactions");
        DashboardEventBus.register(this);
        setColumns(2);
        setRows(2);

        Button b = new Button("Academic years");
        b.setStyleName("link");
        addComponent(b);

        b.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.ACADAMIC_YEAR_HOME.getViewName());
            }
        });

        Button b1 = new Button("Semesters");
        b1.setStyleName("link");
        addComponent(b1);

        b1.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.SEMESTER_HOME.getViewName());
            }
        });

        Button b2 = new Button("Year levels");
        b2.setStyleName("link");
        addComponent(b2);

        b2.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.YEAR_LEVEL_HOME.getViewName());
            }
        });

        Button b3 = new Button("Classgroups");
        b3.setStyleName("link");
        addComponent(b3);

        b3.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.CLASSGROUP_HOME.getViewName());
            }
        });
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
