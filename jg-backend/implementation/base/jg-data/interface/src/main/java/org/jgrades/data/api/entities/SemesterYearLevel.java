package org.jgrades.data.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "JG_DATA_SEMESTER_JOIN_YEAR_LEVEL")
@AssociationOverrides({
        @AssociationOverride(name = "pk.semester",
                joinColumns = @JoinColumn(name = "SEMESTER_ID")),
        @AssociationOverride(name = "pk.yearLevel",
                joinColumns = @JoinColumn(name = "YEAR_LEVEL_ID"))})
@Data
public class SemesterYearLevel implements Serializable {
    @EmbeddedId
    private SemesterYearLevelId pk = new SemesterYearLevelId();

    @Column
    @Type(type = CustomType.JODA_DATE_TIME)
    private DateTime startDateTime;

    @Column
    @Type(type = CustomType.JODA_DATE_TIME)
    private DateTime endDateTime;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "semesterYearLevel")
    private List<ClassGroup> classGroups = Lists.newArrayList();


    @Transient
    public Semester getSemester() {
        return getPk().getSemester();
    }

    public void setSemester(Semester semester) {
        getPk().setSemester(semester);
    }

    @Transient
    public YearLevel getYearLevel() {
        return getPk().getYearLevel();
    }

    public void setYearLevel(YearLevel yearLevel) {
        getPk().setYearLevel(yearLevel);
    }
}
