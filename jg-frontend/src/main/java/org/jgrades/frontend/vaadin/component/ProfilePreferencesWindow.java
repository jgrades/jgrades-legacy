/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.frontend.vaadin.component;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.ParentDetails;
import org.jgrades.data.api.entities.roles.StudentDetails;
import org.jgrades.data.api.exception.MissingDataException;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.frontend.vaadin.event.DashboardEvent.CloseOpenWindowsEvent;
import org.jgrades.frontend.vaadin.event.DashboardEvent.ProfileUpdatedEvent;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;

public class ProfilePreferencesWindow extends Window {
    public static final String ID = "profilepreferenceswindow";


    private User user;

    private TextField loginField;
    private TextField nameField;
    private TextField surnameField;
    private TextField emailField;
    private TextField addressField;
    private TextField phoneField;
    private TextField nationalIdentificationNumberField;
    private TextField dateOfBirthField;

    private PasswordField oldPasswordField;
    private PasswordField newPasswordField;
    private PasswordField newPasswordAgainField;

    private ProfilePreferencesWindow(final User user,
                                     final boolean editPasswordOpen) {
        this.user = user;
        addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);

        setModal(true);
        setCloseShortcut(KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(false);
        setHeight(90.0f, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));
        setContent(content);

        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        detailsWrapper.addComponent(buildProfileTab());
        detailsWrapper.addComponent(buildChangePasswordTab());

        if (editPasswordOpen) {
            detailsWrapper.setSelectedTab(1);
        }

        content.addComponent(buildFooter());
    }

    public static void open(final User user, final boolean preferencesTabActive) {
        DashboardEventBus.post(new CloseOpenWindowsEvent());
        Window w = new ProfilePreferencesWindow(user, preferencesTabActive);
        UI.getCurrent().addWindow(w);
        w.focus();
    }

    private Component buildChangePasswordTab() {
        VerticalLayout root = new VerticalLayout();
        root.setCaption("Change password");
        root.setIcon(FontAwesome.COGS);
        root.setSpacing(true);
        root.setMargin(true);
        root.setSizeFull();

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);

        oldPasswordField = new PasswordField("Old password");
        oldPasswordField.setRequired(true);
        details.addComponent(oldPasswordField);
        newPasswordField = new PasswordField("New password");
        newPasswordField.setRequired(true);
        details.addComponent(newPasswordField);
        newPasswordAgainField = new PasswordField("New password again");
        newPasswordAgainField.setRequired(true);
        details.addComponent(newPasswordAgainField);

        root.addComponent(details);

        return root;
    }

    private Component buildProfileTab() {
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Profile");
        root.setIcon(FontAwesome.USER);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("profile-form");

        VerticalLayout pic = new VerticalLayout();
        pic.setSizeUndefined();
        pic.setSpacing(true);
        Image profilePic = new Image(null, new ThemeResource(
                "img/profile-pic-300px.jpg"));
        profilePic.setWidth(100.0f, Unit.PIXELS);
        pic.addComponent(profilePic);

        Button upload = new Button("Change…", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Not implemented yet");
            }
        });
        upload.addStyleName(ValoTheme.BUTTON_TINY);
        pic.addComponent(upload);

        root.addComponent(pic);

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        loginField = new TextField("Login");
        loginField.setEnabled(false);
        loginField.setValue(user.getLogin());
        details.addComponent(loginField);
        nameField = new TextField("Name");
        nameField.setValue(user.getName());
        nameField.setEnabled(false);
        details.addComponent(nameField);
        surnameField = new TextField("Surname");
        surnameField.setValue(user.getSurname());
        surnameField.setEnabled(false);
        details.addComponent(surnameField);

        Label section = new Label("Contact Info");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);

        emailField = new TextField("Email");
        emailField.setWidth("100%");
        emailField.setValue(user.getEmail());
        emailField.setNullRepresentation("");
        details.addComponent(emailField);

        if (user.getRoles().containsKey(JgRole.STUDENT)) {
            StudentDetails studentDetails = (StudentDetails) user.getRoles().get(JgRole.STUDENT);
            addressField = new TextField("Address");
            addressField.setWidth("100%");
            addressField.setNullRepresentation("");
            addressField.setValue(studentDetails.getAddress());
            details.addComponent(addressField);

            phoneField = new TextField("Contact phone");
        phoneField.setWidth("100%");
        phoneField.setNullRepresentation("");
            phoneField.setValue(studentDetails.getContactPhone());
        details.addComponent(phoneField);
        } else if (user.getRoles().containsKey(JgRole.PARENT)) {
            ParentDetails parentDetails = (ParentDetails) user.getRoles().get(JgRole.PARENT);


            addressField = new TextField("Address");
            addressField.setWidth("100%");
            addressField.setNullRepresentation("");
            addressField.setValue(parentDetails.getAddress());
            details.addComponent(addressField);

            phoneField = new TextField("Contact phone");
            phoneField.setWidth("100%");
            phoneField.setNullRepresentation("");
            phoneField.setValue(parentDetails.getContactPhone());
            details.addComponent(phoneField);
        }

        section = new Label("Additional Info");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);

        if (user.getRoles().containsKey(JgRole.STUDENT)) {
            StudentDetails studentDetails = (StudentDetails) user.getRoles().get(JgRole.STUDENT);

            dateOfBirthField = new TextField("Date of birth");
            dateOfBirthField.setWidth("100%");
            dateOfBirthField.setNullRepresentation("");
            if (studentDetails.getDateOfBirth() != null) {
                dateOfBirthField.setValue(studentDetails.getDateOfBirth().toString());
            }
            dateOfBirthField.setEnabled(false);
            details.addComponent(dateOfBirthField);

            nationalIdentificationNumberField = new TextField("National identification number");
            nationalIdentificationNumberField.setWidth("100%");
            nationalIdentificationNumberField.setNullRepresentation("");
            nationalIdentificationNumberField.setValue(studentDetails.getNationalIdentificationNumber());
            nationalIdentificationNumberField.setEnabled(false);
            details.addComponent(nationalIdentificationNumberField);
        }

        return root;
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button save = new Button("Save");
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    user.setEmail(emailField.getValue());
                    if (user.getRoles().containsKey(JgRole.STUDENT)) {
                        StudentDetails studentDetails = (StudentDetails) user.getRoles().get(JgRole.STUDENT);
                        studentDetails.setAddress(addressField.getValue());
                        studentDetails.setContactPhone(phoneField.getValue());
                    } else if (user.getRoles().containsKey(JgRole.PARENT)) {
                        ParentDetails parentDetails = (ParentDetails) user.getRoles().get(JgRole.PARENT);
                        parentDetails.setAddress(addressField.getValue());
                        parentDetails.setContactPhone(phoneField.getValue());
                    }

                    String newPassword = getNewPassword();

                    DashboardEventBus.post(new ProfileUpdatedEvent(user, newPassword));

                    Notification success = new Notification("Profile updated successfully");
                    success.setDelayMsec(2000);
                    success.setStyleName("bar success small");
                    success.setPosition(Position.BOTTOM_CENTER);
                    success.show(Page.getCurrent());


                    close();
                } catch (Exception ex) {
                    Notification.show(ex.getMessage(), Type.ERROR_MESSAGE);
                }
            }

            private String getNewPassword() throws MissingDataException {
                if (!oldPasswordField.isEmpty() || !newPasswordField.isEmpty()
                        || !newPasswordAgainField.isEmpty()) {
                    if (oldPasswordField.isEmpty() || newPasswordField.isEmpty() || newPasswordAgainField.isEmpty()) {
                        throw new MissingDataException("You must fill all fields");
                    }
                    if (!newPasswordField.getValue().equals(newPasswordAgainField.getValue())) {
                        throw new MissingDataException("New password not match");
                    }
                    return newPasswordField.getValue();
                }
                return null;
            }
        });
        save.focus();
        footer.addComponent(save);
        footer.setComponentAlignment(save, Alignment.TOP_RIGHT);
        return footer;
    }
}
