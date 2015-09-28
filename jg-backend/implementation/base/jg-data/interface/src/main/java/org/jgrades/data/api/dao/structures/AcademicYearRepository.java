package org.jgrades.data.api.dao.structures;

import org.jgrades.data.api.entities.AcademicYear;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicYearRepository extends PagingAndSortingRepository<AcademicYear, Long> {
    AcademicYear findOneByActiveTrue();
}
