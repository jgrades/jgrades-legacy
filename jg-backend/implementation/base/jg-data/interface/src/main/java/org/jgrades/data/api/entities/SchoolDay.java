package org.jgrades.data.api.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Table(name = "JG_DATA_SCHOOL_DAY")
@Data
public class SchoolDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHOOL_ID", nullable = false)
    private School school;
}
