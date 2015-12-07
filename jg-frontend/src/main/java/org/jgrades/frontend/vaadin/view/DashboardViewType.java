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
import org.jgrades.frontend.vaadin.view.information.InformationView;
import org.jgrades.frontend.vaadin.view.lessons.LessonsView;
import org.jgrades.frontend.vaadin.view.management.administration.AdministrationView;
import org.jgrades.frontend.vaadin.view.management.structure.StructureManagementView;
import org.jgrades.frontend.vaadin.view.management.user.UserManagementView;

public enum DashboardViewType {
    //    DASHBOARD("dashboard", DashboardView.class, FontAwesome.HOME, true),
//    TRANSACTIONS("transactions", TransactionsView.class, FontAwesome.TABLE, false),
//    REPORTS("reports", ReportsView.class, FontAwesome.FILE_TEXT_O, true),
//    SCHEDULE("schedule", ScheduleView.class, FontAwesome.CALENDAR_O, false),
    //------------------------------------------------------------------------
    INFORMATION("information", InformationView.class, FontAwesome.HOME, false),
    LESSONS("lessons", LessonsView.class, FontAwesome.CALENDAR_O, false),
    USER_MANAGEMENT("user Managment", UserManagementView.class, FontAwesome.DEDENT, false),
    STRUCTURE_MANAGEMENT("structure Managment", StructureManagementView.class, FontAwesome.FOLDER_OPEN_O, false),
    ADMINISTRATION("administration", AdministrationView.class, FontAwesome.LAPTOP, false);


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
