package org.jgrades.admin.general;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.general.PeriodsMgntService;
import org.jgrades.admin.api.model.PeriodsGeneratorSettings;
import org.jgrades.data.api.dao.SchoolDayPeriodRepository;
import org.jgrades.data.api.entities.SchoolDayPeriod;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PeriodsMgntServiceImpl implements PeriodsMgntService {
    @Autowired
    private SchoolDayPeriodRepository repository;

    @Override
    @Transactional
    public void saveManyWithGenerator(PeriodsGeneratorSettings settings) {
        LocalTime firstLessonStartTime = settings.getFirstLessonTime();

        SchoolDayPeriod firstPeriod = new SchoolDayPeriod();
        firstPeriod.setStartTime(firstLessonStartTime);
        firstPeriod.setEndTime(firstLessonStartTime.plusMinutes(settings.getLessonDurationMinutes()));
        repository.save(firstPeriod);

        LocalTime endTime = firstPeriod.getEndTime();
        for (Integer breakDuration : settings.getBreakDurations()) {
            SchoolDayPeriod period = new SchoolDayPeriod();
            LocalTime startTime = endTime.plusMinutes(breakDuration);
            endTime = startTime.plusMinutes(settings.getLessonDurationMinutes());
            period.setStartTime(startTime);
            period.setEndTime(endTime);
            repository.save(period);
        }
    }

    @Override
    public void saveOrUpdate(SchoolDayPeriod schoolDayPeriod) {
        repository.save(schoolDayPeriod);
    }

    @Override
    public void remove(SchoolDayPeriod schoolDayPeriod) {
        repository.delete(schoolDayPeriod);
    }

    @Override
    public void remove(List<SchoolDayPeriod> schoolDayPeriods) {
        repository.delete(schoolDayPeriods);
    }

    @Override
    public List<SchoolDayPeriod> getAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public SchoolDayPeriod getWithId(Long id) {
        return repository.findOne(id);
    }
}
