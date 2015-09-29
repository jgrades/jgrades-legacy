package org.jgrades.admin.api.general;

import org.jgrades.admin.api.common.CrudService;
import org.jgrades.admin.api.model.PeriodsGeneratorSettings;
import org.jgrades.data.api.entities.SchoolDayPeriod;

import java.util.List;

public interface PeriodsMgntService extends CrudService<SchoolDayPeriod, Long> {
    void saveMany(List<SchoolDayPeriod> periods);

    List<SchoolDayPeriod> generateManyWithGenerator(PeriodsGeneratorSettings settings);
}
