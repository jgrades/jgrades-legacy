package org.jgrades.admin.general;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.general.PeriodsMgntService;
import org.jgrades.admin.api.model.PeriodsGeneratorSettings;
import org.jgrades.admin.common.AbstractMgntService;
import org.jgrades.data.api.dao.SchoolDayPeriodRepository;
import org.jgrades.data.api.entities.SchoolDayPeriod;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PeriodsMgntServiceImpl extends AbstractMgntService<SchoolDayPeriod, Long, SchoolDayPeriodRepository> implements PeriodsMgntService {
    @Autowired
    public PeriodsMgntServiceImpl(SchoolDayPeriodRepository repository) {
        super(repository);
    }

    @Override
    @Transactional("mainTransactionManager")
    public List<SchoolDayPeriod> generateManyWithGenerator(PeriodsGeneratorSettings settings) {
        List<SchoolDayPeriod> generatedPeriods = Lists.newArrayList();

        LocalTime firstLessonStartTime = settings.getFirstLessonTime();

        SchoolDayPeriod firstPeriod = new SchoolDayPeriod();
        firstPeriod.setStartTime(firstLessonStartTime);
        firstPeriod.setEndTime(firstLessonStartTime.plusMinutes(settings.getLessonDurationMinutes()));
        generatedPeriods.add(firstPeriod);

        LocalTime endTime = firstPeriod.getEndTime();
        for (Integer breakDuration : settings.getBreakDurations()) {
            SchoolDayPeriod period = new SchoolDayPeriod();
            LocalTime startTime = endTime.plusMinutes(breakDuration);
            endTime = startTime.plusMinutes(settings.getLessonDurationMinutes());
            period.setStartTime(startTime);
            period.setEndTime(endTime);
            generatedPeriods.add(period);
        }
        return generatedPeriods;
    }

    @Override
    @Transactional("mainTransactionManager")
    public void saveMany(List<SchoolDayPeriod> periods) {
        repository.save(periods);
    }
}
