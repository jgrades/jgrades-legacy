package org.jgrades.admin.api.model;

import lombok.Data;
import org.joda.time.LocalDate;

@Data
public class StudentCsvEntry {
    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    private String nationalIdentificationNumber;

    private String address;
}
