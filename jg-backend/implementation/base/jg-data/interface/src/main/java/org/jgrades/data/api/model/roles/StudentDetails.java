package org.jgrades.data.api.model.roles;

import org.jgrades.data.api.entities.Parent;
import org.joda.time.LocalDate;

public interface StudentDetails extends RoleDetails {
    String getContactPhone();

    String getAddress();

    LocalDate getDateOfBirth();

    String getNationalIdentificationNumber();

    Parent getParent();
}
