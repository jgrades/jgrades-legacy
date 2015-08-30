package org.jgrades.admin.api.structures;

import org.jgrades.admin.api.common.Manager;
import org.jgrades.data.api.entities.AcademicYear;
import org.jgrades.data.api.entities.Semester;

public interface SemesterMgntService extends Manager<Semester, Long> {
    Semester createNewByMigration(Semester previousSemester, AcademicYear targetAcademicYear);
}
