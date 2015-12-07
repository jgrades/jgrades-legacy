/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.frontend.vaadin;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import org.jgrades.config.api.model.UserData;
import org.jgrades.frontend.vaadin.data.DataProvider;
import org.jgrades.frontend.vaadin.data.dummy.DummyDataProvider;
import org.jgrades.frontend.vaadin.event.DashboardEvent.*;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.frontend.vaadin.view.LoginView;
import org.jgrades.frontend.vaadin.view.MainView;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.jgrades.rest.client.admin.accounts.UserServiceClient;
import org.jgrades.rest.client.config.UserProfileServiceClient;
import org.jgrades.rest.client.security.LoginServiceClient;
import org.jgrades.security.api.model.LoginResult;

import java.util.Locale;

@Theme("dashboard")
@Widgetset("org.jgrades.frontend.vaadin.DashboardWidgetSet")
@Title("jGrades")
public final class DashboardUI extends UI {
    public static final String URL = "http://localhost:8080/jg-rest/";

    private final DataProvider dataProvider = new DummyDataProvider();
    private final DashboardEventBus dashboardEventbus = new DashboardEventBus();
    private StatefullRestTemplate restTemplate = new StatefullRestTemplate();
    private LoginServiceClient loginServiceClient = new LoginServiceClient(URL, restTemplate);
    private UserProfileServiceClient userProfileServiceClient = new UserProfileServiceClient(URL, restTemplate);
    private UserServiceClient userServiceClient = new UserServiceClient(URL, restTemplate);

    /**
     * @return An instance for accessing the (dummy) services layer.
     */
    public static DataProvider getDataProvider() {
        return ((DashboardUI) getCurrent()).dataProvider;
    }

    public static DashboardEventBus getDashboardEventbus() {
        return ((DashboardUI) getCurrent()).dashboardEventbus;
    }

    @Override
    protected void init(final VaadinRequest request) {
        setLocale(Locale.US);

        DashboardEventBus.register(this);
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        updateContent();

        Page.getCurrent().addBrowserWindowResizeListener(
                new BrowserWindowResizeListener() {
                    @Override
                    public void browserWindowResized(
                            final BrowserWindowResizeEvent event) {
                        DashboardEventBus.post(new BrowserResizeEvent());
                    }
                });
    }

    private void updateContent() {
        LoginResult loginResult = (LoginResult) VaadinSession.getCurrent().getAttribute("loginResult");
        if (loginResult != null) {
            if (loginResult.isSuccess()) {
                setContent(new MainView());
                removeStyleName("loginview");
                getNavigator().navigateTo(getNavigator().getState());
            } else {
                setContent(new LoginView(loginResult));
                addStyleName("loginview");
            }
        } else {
            setContent(new LoginView());
            addStyleName("loginview");
        }
    }

    @Subscribe
    public void userLoginRequested(final UserLoginRequestedEvent event) {
        LoginResult loginResult = loginServiceClient.logIn(event.getUserName(), event.getPassword());
        if (loginResult.isSuccess()) {
            VaadinSession.getCurrent().setAttribute("jgUser", loginServiceClient.getLoggedUser());
        }
        VaadinSession.getCurrent().setAttribute("loginResult", loginResult);
        updateContent();
    }

    @Subscribe
    public void userLoggedOut(final UserLoggedOutEvent event) {
        loginServiceClient.logOut();
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }

    @Subscribe
    public void closeOpenWindows(final CloseOpenWindowsEvent event) {
        for (Window window : getWindows()) {
            window.close();
        }
    }

    @Subscribe
    public void updateProfile(final ProfileUpdatedEvent profileUpdatedEvent) {
        UserData userData = new UserData();
        userData.setUser(profileUpdatedEvent.getUser());
        userData.setPassword(profileUpdatedEvent.getPassword());
        userProfileServiceClient.setProfileData(userData);
        VaadinSession.getCurrent().setAttribute("jgUser", loginServiceClient.getLoggedUser());
        updateContent();
    }
}
