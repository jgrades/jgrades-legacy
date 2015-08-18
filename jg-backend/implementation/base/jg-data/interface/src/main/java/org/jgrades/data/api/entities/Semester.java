package org.jgrades.data.api.entities;

import com.google.common.collect.Lists;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "JG_DATA_SEMESTER")
public class Semester {
    private Long id;
    private String name;
    private AcademicYear academicYear;

    private List<SemesterYearLevel> yearLevels = Lists.newArrayList();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEMESTER_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACADEMIC_YEAR_ID", nullable = false)
    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    @OneToMany(mappedBy = "pk.yearLevel", cascade = CascadeType.ALL)
    public List<SemesterYearLevel> getYearLevels() {
        return yearLevels;
    }

    public void setYearLevels(List<SemesterYearLevel> yearLevels) {
        this.yearLevels = yearLevels;
    }
}
