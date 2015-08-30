package org.jgrades.data.dao;

import org.jgrades.data.api.entities.Subject;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends PagingAndSortingRepository<Subject, Long> {
}
