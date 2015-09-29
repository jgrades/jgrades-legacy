package org.jgrades.data.api.dao.structures;

import org.jgrades.data.api.entities.SubGroup;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubGroupRepository extends PagingAndSortingRepository<SubGroup, Long> {
}
