package org.jgrades.data.api.dao;

import org.jgrades.data.api.entities.SchoolDayPeriod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolDayPeriodRepository extends CrudRepository<SchoolDayPeriod, Long> {

}
