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
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.UI;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.frontend.vaadin.view.DashboardViewType;

public class AdministrationView extends GridLayout implements View {

    public AdministrationView() {
        setSizeFull();
        setMargin(new MarginInfo(true));
        addStyleName("transactions");
        DashboardEventBus.register(this);
        setColumns(2);
        setRows(3);

        Button b = new Button("Licence management");
        b.setStyleName("link");
        addComponent(b);

        b.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.LICENCE_HOME.getViewName());
            }
        });

        Button b1 = new Button("Security policies");
        b1.setStyleName("link");
        addComponent(b1);

        b1.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.SECURITY_HOME.getViewName());
            }
        });

        Button b2 = new Button("Datasource");
        b2.setStyleName("link");
        addComponent(b2);

        b2.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.DATASOURCE_HOME.getViewName());
            }
        });

        Button b3 = new Button("Back-up and restore");
        b3.setStyleName("link");
        addComponent(b3);

        b3.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.BACKUP_HOME.getViewName());
            }
        });

        Button b4 = new Button("Logging and auditing");
        b4.setStyleName("link");
        addComponent(b4);

        b4.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.LOGGING_HOME.getViewName());
            }
        });

    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
