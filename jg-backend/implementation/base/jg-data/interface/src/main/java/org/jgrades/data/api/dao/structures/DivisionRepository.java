package org.jgrades.data.api.dao.structures;

import org.jgrades.data.api.entities.Division;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DivisionRepository extends PagingAndSortingRepository<Division, Long> {
}
