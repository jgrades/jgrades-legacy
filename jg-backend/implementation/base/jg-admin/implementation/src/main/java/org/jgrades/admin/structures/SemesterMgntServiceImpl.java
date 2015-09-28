package org.jgrades.admin.structures;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.structures.AcademicYearMgntService;
import org.jgrades.admin.api.structures.SemesterMgntService;
import org.jgrades.data.api.dao.structures.SemesterRepository;
import org.jgrades.data.api.entities.Semester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SemesterMgntServiceImpl implements SemesterMgntService {
    @Autowired
    private SemesterRepository repository;

    @Autowired
    private AcademicYearMgntService academicYearMgntService;

    @Override//TODO
    @Transactional("mainTransactionManager")
    public Semester createNewByMigration(Semester previousSemester, String newSemesterName) {
        return null;
    }

    @Override
    public Semester getActiveSemester() {
        return repository.findOneByActiveTrue();
    }

    @Override
    @Transactional("mainTransactionManager")
    public void setActiveSemester(Semester semester) {
        semester.setAcademicYear(academicYearMgntService.getActiveAcademicYear());
        Semester actualActiveSemester = getActiveSemester();
        if (actualActiveSemester != null) {
            actualActiveSemester.setActive(false);
            repository.save(actualActiveSemester);
        }

        semester.setActive(true);
        repository.save(semester);
    }

    @Override
    public void saveOrUpdate(Semester semester) {
        semester.setAcademicYear(academicYearMgntService.getActiveAcademicYear());
        if (semester.isActive()) {
            setActiveSemester(semester);
        } else {
            repository.save(semester);
        }
    }

    @Override
    public void remove(Semester semester) {
        repository.delete(semester);
    }

    @Override
    public void remove(List<Semester> semesters) {
        repository.delete(semesters);
    }

    @Override
    public Page<Semester> getPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Semester> getAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Semester getWithId(Long id) {
        return repository.findOne(id);
    }
}
