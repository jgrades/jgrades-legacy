package org.jgrades.admin.structures;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.structures.AcademicYearMgntService;
import org.jgrades.data.api.dao.structures.AcademicYearRepository;
import org.jgrades.data.api.entities.AcademicYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AcademicYearMgntServiceImpl implements AcademicYearMgntService {
    @Autowired
    private AcademicYearRepository repository;

    @Override
    public AcademicYear getActiveAcademicYear() {
        return repository.findOneByActiveTrue();
    }

    @Override
    @Transactional("mainTransactionManager")
    public void setActiveAcademicYear(AcademicYear academicYear) {
        AcademicYear actualActiveYear = repository.findOneByActiveTrue();
        if (actualActiveYear != null) {
            actualActiveYear.setActive(false);
            repository.save(actualActiveYear);
        }

        academicYear.setActive(true);
        repository.save(academicYear);
    }

    @Override
    public void saveOrUpdate(AcademicYear academicYear) {
        if (academicYear.isActive()) {
            setActiveAcademicYear(academicYear);
        } else {
            repository.save(academicYear);
        }
    }

    @Override
    public void remove(AcademicYear academicYear) {
        repository.delete(academicYear);
    }

    @Override
    public void remove(List<AcademicYear> academicYears) {
        repository.delete(academicYears);
    }

    @Override
    public Page<AcademicYear> getPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<AcademicYear> getAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public AcademicYear getWithId(Long id) {
        return repository.findOne(id);
    }
}
