package org.jgrades.data.api.entities;

import com.google.common.collect.Lists;
import org.hibernate.annotations.Type;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "JG_DATA_SEMESTER_JOIN_YEAR_LEVEL")
@AssociationOverrides({
        @AssociationOverride(name = "pk.semester",
                joinColumns = @JoinColumn(name = "SEMESTER_ID")),
        @AssociationOverride(name = "pk.yearLevel",
                joinColumns = @JoinColumn(name = "YEAR_LEVEL_ID"))})
public class SemesterYearLevel {
    private SemesterYearLevelId pk = new SemesterYearLevelId();

    private DateTime startDateTime;
    private DateTime endDateTime;

    private List<ClassGroup> classGroups = Lists.newArrayList();

    @EmbeddedId
    public SemesterYearLevelId getPk() {
        return pk;
    }

    public void setPk(SemesterYearLevelId pk) {
        this.pk = pk;
    }

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

    @Column
    @Type(type = CustomType.JODA_DATE_TIME)
    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Column
    @Type(type = CustomType.JODA_DATE_TIME)
    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "semesterYearLevel")
    public List<ClassGroup> getClassGroups() {
        return classGroups;
    }

    public void setClassGroups(List<ClassGroup> classGroups) {
        this.classGroups = classGroups;
    }
}
