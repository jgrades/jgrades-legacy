package org.jgrades.admin.api.general;

import org.jgrades.admin.api.common.Manager;
import org.jgrades.admin.api.model.PeriodsGeneratorSettings;
import org.jgrades.data.api.entities.SchoolDayPeriod;

public interface PeriodsMgntService extends Manager<SchoolDayPeriod, Long> {
    void saveManyWithGenerator(PeriodsGeneratorSettings settings);
}
