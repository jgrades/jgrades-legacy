package org.jgrades.data.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.LocalTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JG_DATA_SCHOOL_DAY_PERIOD")
@Data
public class SchoolDayPeriod implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Type(type = CustomType.JODA_LOCAL_TIME)
    private LocalTime startTime;

    @Column
    @Type(type = CustomType.JODA_LOCAL_TIME)
    private LocalTime endTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHOOL_ID", nullable = false)
    private School school;
}
