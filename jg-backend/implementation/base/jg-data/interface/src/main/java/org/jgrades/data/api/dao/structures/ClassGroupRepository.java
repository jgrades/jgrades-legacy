package org.jgrades.data.api.dao.structures;

import org.jgrades.data.api.entities.ClassGroup;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassGroupRepository extends PagingAndSortingRepository<ClassGroup, Long> {
}
