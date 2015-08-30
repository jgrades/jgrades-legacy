package org.jgrades.admin.api.general;

import org.jgrades.admin.api.model.WorkingDays;

public interface DaysMgntService {
    WorkingDays getWorkingDays();

    void setWorkingDays(WorkingDays workingDays);
}
