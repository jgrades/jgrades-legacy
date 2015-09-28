package org.jgrades.data.api.dao.structures;

import org.jgrades.data.api.entities.YearLevel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearLevelRepository extends PagingAndSortingRepository<YearLevel, Long> {
}
