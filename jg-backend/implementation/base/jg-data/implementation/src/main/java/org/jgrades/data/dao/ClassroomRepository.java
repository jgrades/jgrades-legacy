package org.jgrades.data.dao;

import org.jgrades.data.api.entities.Classroom;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends PagingAndSortingRepository<Classroom, Long> {
}
