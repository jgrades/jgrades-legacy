package org.jgrades.admin.api.structures;

import org.jgrades.admin.api.common.Manager;
import org.jgrades.admin.api.common.PagingSelector;
import org.jgrades.data.api.entities.AcademicYear;

public interface AcademicYearMgntService extends Manager<AcademicYear, Long>, PagingSelector<AcademicYear, Long> {
    AcademicYear getActiveAcademicYear();

    void setActiveAcademicYear(AcademicYear activeAcademicYear);
}
