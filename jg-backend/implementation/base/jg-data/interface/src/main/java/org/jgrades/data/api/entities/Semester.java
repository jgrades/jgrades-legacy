package org.jgrades.data.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "JG_DATA_SEMESTER")
@Data
public class Semester implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEMESTER_ID", unique = true, nullable = false)
    private Long id;

    private String name;

    private boolean active;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACADEMIC_YEAR_ID", nullable = false)
    private AcademicYear academicYear;

    @JsonIgnore
    @OneToMany(mappedBy = "pk.yearLevel", cascade = CascadeType.ALL)
    private List<SemesterYearLevel> yearLevels = Lists.newArrayList();
}
