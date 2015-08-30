package org.jgrades.admin.api.general;

import org.jgrades.data.api.entities.School;

public interface GeneralDataService {
    School getSchoolGeneralDetails();

    void setSchoolGeneralDetails(School school);
}
