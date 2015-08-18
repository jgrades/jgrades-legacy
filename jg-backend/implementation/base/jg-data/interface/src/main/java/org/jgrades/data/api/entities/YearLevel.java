package org.jgrades.data.api.entities;

import com.google.common.collect.Lists;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "JG_DATA_YEAR_LEVEL")
public class YearLevel {
    private Long id;
    private String name;
    private List<SemesterYearLevel> semesters = Lists.newArrayList();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "YEAR_LEVEL_ID", unique = true, nullable = false)
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

    @OneToMany(mappedBy = "pk.semester", cascade = CascadeType.ALL)
    public List<SemesterYearLevel> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<SemesterYearLevel> semesters) {
        this.semesters = semesters;
    }
}
