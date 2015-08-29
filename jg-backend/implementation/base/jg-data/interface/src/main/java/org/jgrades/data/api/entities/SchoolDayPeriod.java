package org.jgrades.data.api.entities;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.LocalTime;

import javax.persistence.*;

@Entity
@Table(name = "JG_DATA_SCHOOL_DAY_PERIOD")
@Data
public class SchoolDayPeriod {
    @Id
    private Long no;

    @Column
    @Type(type = CustomType.JODA_LOCAL_TIME)
    private LocalTime startTime;

    @Column
    @Type(type = CustomType.JODA_LOCAL_TIME)
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHOOL_ID", nullable = false)
    private School school;
}
