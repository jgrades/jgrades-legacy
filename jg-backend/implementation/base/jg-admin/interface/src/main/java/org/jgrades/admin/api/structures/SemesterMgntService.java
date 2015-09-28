package org.jgrades.admin.api.structures;

import org.jgrades.admin.api.common.Manager;
import org.jgrades.admin.api.common.PagingSelector;
import org.jgrades.data.api.entities.Semester;

public interface SemesterMgntService extends Manager<Semester>, PagingSelector<Semester, Long> {
    Semester createNewByMigration(Semester previousSemester, String newSemesterName);

    Semester getActiveSemester();

    void setActiveSemester(Semester semester);
}
