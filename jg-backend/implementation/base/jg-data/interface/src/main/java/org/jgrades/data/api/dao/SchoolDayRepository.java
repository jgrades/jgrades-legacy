package org.jgrades.data.api.dao;

import org.jgrades.data.api.entities.SchoolDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolDayRepository extends CrudRepository<SchoolDay, Integer> {
}
