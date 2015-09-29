package org.jgrades.admin.api.structures;

import org.jgrades.admin.api.common.CrudPagingService;
import org.jgrades.data.api.entities.Semester;

public interface SemesterMgntService extends CrudPagingService<Semester, Long> {
    Semester createNewByMigration(Semester previousSemester, String newSemesterName);

    Semester getActiveSemester();

    void setActiveSemester(Semester semester);
}
