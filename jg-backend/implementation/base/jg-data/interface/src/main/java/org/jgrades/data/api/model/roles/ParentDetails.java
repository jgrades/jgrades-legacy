package org.jgrades.data.api.model.roles;

import org.jgrades.data.api.entities.Student;

public interface ParentDetails extends RoleDetails {
    String getContactPhone();

    String getAddress();

    Student getStudent();
}
