package org.jgrades.admin.general;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.general.PeriodsMgntService;
import org.jgrades.admin.api.model.PeriodsGeneratorSettings;
import org.jgrades.data.api.entities.SchoolDayPeriod;
import org.jgrades.data.dao.SchoolDayPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PeriodsMgntServiceImpl implements PeriodsMgntService {
    @Autowired
    private SchoolDayPeriodRepository schoolDayPeriodRepository;

    @Override
    @Transactional
    public void saveManyWithGenerator(PeriodsGeneratorSettings settings) {
        //TODO
    }

    @Override
    public void saveOrUpdate(SchoolDayPeriod schoolDayPeriod) {
        schoolDayPeriodRepository.save(schoolDayPeriod);
    }

    @Override
    public void remove(SchoolDayPeriod schoolDayPeriod) {
        schoolDayPeriodRepository.delete(schoolDayPeriod);
    }

    @Override
    public void remove(List<SchoolDayPeriod> schoolDayPeriods) {
        schoolDayPeriodRepository.delete(schoolDayPeriods);
    }

    @Override
    public List<SchoolDayPeriod> getAll() {
        return Lists.newArrayList(schoolDayPeriodRepository.findAll());
    }

    @Override
    public SchoolDayPeriod getWithId(Long id) {
        return schoolDayPeriodRepository.findOne(id);
    }
}
