/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.frontend.vaadin.view;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import org.jgrades.frontend.vaadin.view.dashboard.DashboardView;
import org.jgrades.frontend.vaadin.view.reports.ReportsView;
import org.jgrades.frontend.vaadin.view.schedule.ScheduleView;
import org.jgrades.frontend.vaadin.view.transactions.TransactionsView;

//import com.vaadin.demo.dashboard.view.sales.SalesView;

public enum DashboardViewType {
    DASHBOARD("dashboard", DashboardView.class, FontAwesome.HOME, true), TRANSACTIONS(
            "transactions", TransactionsView.class, FontAwesome.TABLE, false), REPORTS(
            "reports", ReportsView.class, FontAwesome.FILE_TEXT_O, true), SCHEDULE(
            "schedule", ScheduleView.class, FontAwesome.CALENDAR_O, false);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    DashboardViewType(final String viewName,
                      final Class<? extends View> viewClass, final Resource icon,
                      final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

}
