package org.jgrades.admin.general;

import org.jgrades.admin.api.general.GeneralDataService;
import org.jgrades.data.api.dao.SchoolRepository;
import org.jgrades.data.api.entities.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class GeneralDataServiceImpl implements GeneralDataService {
    @Autowired
    private SchoolRepository repository;

    @Override
    public School getSchoolGeneralDetails() {
        Iterator<School> schoolIterator = repository.findAll().iterator();
        return schoolIterator.hasNext() ? schoolIterator.next() : null;
    }

    @Override
    public void setSchoolGeneralDetails(School school) {
        if (repository.count() == 1) {
            School actualSchool = getSchoolGeneralDetails();
            school.setId(actualSchool.getId());
        }
        repository.save(school);
    }
}
