package org.jgrades.data.api.entities;

import org.hibernate.annotations.Type;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.LocalTime;

import javax.persistence.*;

@Entity
@Table(name = "JG_DATA_SCHOOL_DAY_PERIOD")
public class SchoolDayPeriod {
    private Long no;
    private LocalTime startTime;
    private LocalTime endTime;
    private School school;

    @Id
    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    @Column
    @Type(type = CustomType.JODA_LOCAL_TIME)
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Column
    @Type(type = CustomType.JODA_LOCAL_TIME)
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHOOL_ID", nullable = false)
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
