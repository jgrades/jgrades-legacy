/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.frontend.vaadin.view.management.user;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.*;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.frontend.vaadin.view.DashboardViewType;
import org.jgrades.rest.client.admin.accounts.UserServiceClient;
import org.jgrades.rest.client.config.UserProfileServiceClient;

import java.util.Arrays;

public class UserEditWindow extends Window {
    public static final String ID = "AcademicYearEditWindow";

    public UserEditWindow(final UserServiceClient userServiceClient, UserProfileServiceClient userProfileServiceClient, final User user, boolean editable) {
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

        final TextField loginField = new TextField("Login");
        if (user != null) {
            loginField.setValue(user.getLogin());
        }
        loginField.setEnabled(editable);
        form.addComponent(loginField);

        final TextField nameField = new TextField("Name");
        if (user != null) {
            nameField.setValue(user.getName());
        }
        nameField.setEnabled(editable);
        form.addComponent(nameField);

        final TextField surnameField = new TextField("Surname");
        if (user != null) {
            surnameField.setValue(user.getSurname());
        }
        surnameField.setEnabled(editable);
        form.addComponent(surnameField);

        final TextField emailField = new TextField("E-mail");
        if (user != null) {
            emailField.setValue(user.getEmail());
        }
        emailField.setEnabled(editable);
        form.addComponent(emailField);

        final CheckBox isActive = new CheckBox("Is active");
        if (user != null) {
            isActive.setValue(user.isActive());
        } else {
            isActive.setValue(true);
        }
        isActive.setEnabled(editable);
        form.addComponent(isActive);

        final CheckBox isAdmin = new CheckBox("Administrator role");
        if (user != null) {
            isAdmin.setValue(user.getRoles().containsKey(JgRole.ADMINISTRATOR));
        }
        isAdmin.setEnabled(editable);
        form.addComponent(isAdmin);

        final CheckBox isManager = new CheckBox("Manager role");
        if (user != null) {
            isManager.setValue(user.getRoles().containsKey(JgRole.MANAGER));
        }
        isManager.setEnabled(editable);
        form.addComponent(isManager);

        final CheckBox isTeacher = new CheckBox("Teacher role");
        if (user != null) {
            isTeacher.setValue(user.getRoles().containsKey(JgRole.TEACHER));
        }
        isTeacher.setEnabled(editable);
        form.addComponent(isTeacher);

        final CheckBox isStudent = new CheckBox("Student role");
        if (user != null) {
            isStudent.setValue(user.getRoles().containsKey(JgRole.STUDENT));
        }
        isStudent.setEnabled(editable);
        form.addComponent(isStudent);

        final CheckBox isParent = new CheckBox("Parent role");
        if (user != null) {
            isParent.setValue(user.getRoles().containsKey(JgRole.PARENT));
        }
        isParent.setEnabled(editable);
        form.addComponent(isParent);

        Button saveButton = new Button("Save", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (user != null) {
                    user.setLogin(loginField.getValue());
                    user.setName(nameField.getValue());
                    user.setSurname(surnameField.getValue());
                    user.setEmail(emailField.getValue());
                    user.setActive(isActive.getValue());

                    if (isAdmin.getValue() && !user.getRoles().containsKey(JgRole.ADMINISTRATOR)) {
                        AdministratorDetails administratorDetails = new AdministratorDetails();
                        // administratorDetails.setUser(user);
                        user.getRoles().put(JgRole.ADMINISTRATOR, administratorDetails);
                    } else if (!isAdmin.getValue() && user.getRoles().containsKey(JgRole.ADMINISTRATOR)) {
                        user.getRoles().remove(JgRole.ADMINISTRATOR);
                    }

                    if (isManager.getValue() && !user.getRoles().containsKey(JgRole.MANAGER)) {
                        ManagerDetails managerDetails = new ManagerDetails();
                        //managerDetails.setUser(user);
                        user.getRoles().put(JgRole.MANAGER, managerDetails);
                    } else if (!isManager.getValue() && user.getRoles().containsKey(JgRole.MANAGER)) {
                        user.getRoles().remove(JgRole.MANAGER);
                    }

                    if (isTeacher.getValue() && !user.getRoles().containsKey(JgRole.TEACHER)) {
                        TeacherDetails teacherDetails = new TeacherDetails();
                        //teacherDetails.setUser(user);
                        user.getRoles().put(JgRole.TEACHER, teacherDetails);
                    } else if (!isTeacher.getValue() && user.getRoles().containsKey(JgRole.TEACHER)) {
                        user.getRoles().remove(JgRole.TEACHER);
                    }

                    if (isStudent.getValue() && !user.getRoles().containsKey(JgRole.STUDENT)) {
                        StudentDetails studentDetails = new StudentDetails();
                        // studentDetails.setUser(user);
                        user.getRoles().put(JgRole.STUDENT, studentDetails);
                    } else if (!isStudent.getValue() && user.getRoles().containsKey(JgRole.STUDENT)) {
                        user.getRoles().remove(JgRole.STUDENT);
                    }

                    if (isParent.getValue() && !user.getRoles().containsKey(JgRole.PARENT)) {
                        ParentDetails parentDetails = new ParentDetails();
                        // parentDetails.setUser(user);
                        user.getRoles().put(JgRole.PARENT, parentDetails);
                    } else if (!isParent.getValue() && user.getRoles().containsKey(JgRole.PARENT)) {
                        user.getRoles().remove(JgRole.PARENT);
                    }

                    userServiceClient.insertOrUpdate(user);
                    Notification.show("Updated!");
                    close();
                    UI.getCurrent().getNavigator()
                            .navigateTo(DashboardViewType.USER_MANAGEMENT.getViewName());
                }
            }
        });

        Button removeButton = new Button("Remove", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                userServiceClient.remove(Arrays.asList(user.getId()));
                Notification.show("Removed!");
                close();
                UI.getCurrent().getNavigator()
                        .navigateTo(DashboardViewType.USER_MANAGEMENT.getViewName());
            }
        });
        saveButton.setVisible(editable);
        removeButton.setVisible(editable);
        form.addComponent(saveButton);
        form.addComponent(removeButton);

        setContent(form);
    }
}
