package org.jgrades.admin.api.general;

import org.jgrades.admin.api.common.Manager;
import org.jgrades.admin.api.common.Selector;
import org.jgrades.admin.api.model.PeriodsGeneratorSettings;
import org.jgrades.data.api.entities.SchoolDayPeriod;

import java.util.List;

public interface PeriodsMgntService extends Manager<SchoolDayPeriod>, Selector<SchoolDayPeriod, Long> {
    void saveMany(List<SchoolDayPeriod> periods);

    List<SchoolDayPeriod> generateManyWithGenerator(PeriodsGeneratorSettings settings);
}
