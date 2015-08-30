package org.jgrades.admin.api.model;

import lombok.Data;
import org.jgrades.data.api.entities.ClassGroup;

@Data
public class StudentMassCreatingSettings {
    private boolean activeAfterCreation;
    private ClassGroup targetClassGroup;
    private LoginGenerationStrategy<StudentCsvEntry> loginGenerationStrategy;
    private PasswordGenerationStrategy passwordGenerationStrategy;
}
