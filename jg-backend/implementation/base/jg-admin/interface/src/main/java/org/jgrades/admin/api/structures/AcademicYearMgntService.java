package org.jgrades.admin.api.structures;

import org.jgrades.admin.api.common.CrudPagingService;
import org.jgrades.data.api.entities.AcademicYear;

public interface AcademicYearMgntService extends CrudPagingService<AcademicYear, Long> {
    AcademicYear getActiveAcademicYear();

    void setActiveAcademicYear(AcademicYear academicYear);
}
