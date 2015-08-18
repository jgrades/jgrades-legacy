package org.jgrades.data.api.entities;

import com.google.common.collect.Lists;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "JG_DATA_CLASS_GROUP")
public class ClassGroup {
    private Long id;
    private SemesterYearLevel semesterYearLevel;
    private String name;
    private String description;
    private List<Division> divisions = Lists.newArrayList();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "SEMESTER_ID", referencedColumnName = "SEMESTER_ID"),
            @JoinColumn(name = "YEAR_LEVEL_ID", referencedColumnName = "YEAR_LEVEL_ID")
    })
    public SemesterYearLevel getSemesterYearLevel() {
        return semesterYearLevel;
    }

    public void setSemesterYearLevel(SemesterYearLevel semesterYearLevel) {
        this.semesterYearLevel = semesterYearLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classGroup")
    public List<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
    }
}
