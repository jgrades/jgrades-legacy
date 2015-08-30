package org.jgrades.admin.api.accounts;

import org.jgrades.admin.api.model.StudentCsvEntry;
import org.jgrades.admin.api.model.StudentMassCreatingSettings;

import java.util.Set;

public interface MassAccountCreatorService {
    void createStudents(Set<StudentCsvEntry> students, StudentMassCreatingSettings settings);
}
