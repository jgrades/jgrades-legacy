package org.jgrades.data.api.dao.structures;

import org.jgrades.data.api.entities.Semester;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends PagingAndSortingRepository<Semester, Long> {
    Semester findOneByActiveTrue();
}
