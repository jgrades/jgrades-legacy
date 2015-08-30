package org.jgrades.data.dao;

import org.jgrades.data.api.entities.SchoolDayPeriod;
import org.springframework.data.repository.CrudRepository;

@org.springframework.stereotype.Repository
public interface SchoolDayPeriodRepository extends CrudRepository<SchoolDayPeriod, Long> {

}
