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

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.jgrades.data.api.entities.School;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.data.api.model.SchoolType;
import org.jgrades.frontend.vaadin.DashboardUI;
import org.jgrades.frontend.vaadin.event.DashboardEventBus;
import org.jgrades.rest.client.admin.general.SchoolServiceClient;

public class GeneralView extends VerticalLayout implements View {

    private SchoolServiceClient schoolServiceClient;
    private User user;

    public GeneralView() {
        setSizeFull();
        setMargin(new MarginInfo(true));
        addStyleName("transactions");
        DashboardEventBus.register(this);

        user = (User) VaadinSession.getCurrent().getAttribute("jgUser");
        schoolServiceClient = ((DashboardUI) UI.getCurrent()).getSchoolServiceClient();
        boolean editable = false;

        if (user.getRoles().containsKey(JgRole.ADMINISTRATOR) ||
                user.getRoles().containsKey(JgRole.MANAGER)) {
            editable = true;
        }

        FormLayout form = new FormLayout();
        final School generalData = schoolServiceClient.getGeneralData();

        final TextField nameField = new TextField("Name");
        nameField.setValue(generalData.getName());
        nameField.setEnabled(editable);
        form.addComponent(nameField);

        final TextField shortNameField = new TextField("Short name");
        form.addComponent(shortNameField);
        shortNameField.setValue(generalData.getShortName());
        shortNameField.setEnabled(editable);

        final TextField nameOnDiplomaField = new TextField("Name on diploma");
        form.addComponent(nameOnDiplomaField);
        nameOnDiplomaField.setValue(generalData.getNameOnDiploma());
        nameOnDiplomaField.setEnabled(editable);

        final TextField nameOnGradeuateDiplomaField = new TextField("Name on graduate diploma");
        form.addComponent(nameOnGradeuateDiplomaField);
        nameOnGradeuateDiplomaField.setValue(generalData.getNameOnGraduateDiploma());
        nameOnGradeuateDiplomaField.setEnabled(editable);

        final ComboBox typeOfSchool = new ComboBox("Type of school");
        typeOfSchool.addItems(SchoolType.values());
        typeOfSchool.setValue(generalData.getType());
        form.addComponent(typeOfSchool);
        typeOfSchool.setEnabled(editable);

        final TextField addressField = new TextField("Address");
        form.addComponent(addressField);
        addressField.setValue(generalData.getAddress());
        addressField.setEnabled(editable);

        final TextField vatField = new TextField("Vat Identification Number");
        form.addComponent(vatField);
        vatField.setValue(generalData.getVatIdentificationNumber());
        vatField.setEnabled(editable);

        final TextField webpageField = new TextField("Webpage");
        form.addComponent(webpageField);
        webpageField.setValue(generalData.getWebpage());
        webpageField.setEnabled(editable);

        final TextField emailField = new TextField("E-mail");
        form.addComponent(emailField);
        emailField.setValue(generalData.getEmail());
        emailField.setEnabled(editable);

        final TextField contactPhoneField = new TextField("Contact phone");
        form.addComponent(contactPhoneField);
        contactPhoneField.setValue(generalData.getContactPhone());
        contactPhoneField.setEnabled(editable);

        Button saveButton = new Button("Save", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                generalData.setName(nameField.getValue());
                generalData.setShortName(shortNameField.getValue());
                generalData.setNameOnDiploma(nameOnDiplomaField.getValue());
                generalData.setNameOnGraduateDiploma(nameOnGradeuateDiplomaField.getValue());
                generalData.setType(SchoolType.valueOf(typeOfSchool.getValue().toString()));
                generalData.setAddress(addressField.getValue());
                generalData.setVatIdentificationNumber(vatField.getValue());
                generalData.setWebpage(webpageField.getValue());
                generalData.setEmail(emailField.getValue());
                generalData.setContactPhone(contactPhoneField.getValue());
                schoolServiceClient.insertOrUpdate(generalData);
                Notification.show("Saved!");
            }
        });
        saveButton.setVisible(editable);
        form.addComponent(saveButton);

        addComponent(form);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
