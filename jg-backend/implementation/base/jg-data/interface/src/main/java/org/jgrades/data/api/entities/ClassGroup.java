package org.jgrades.data.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "JG_DATA_CLASS_GROUP")
@Data
public class ClassGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "SEMESTER_ID", referencedColumnName = "SEMESTER_ID"),
            @JoinColumn(name = "YEAR_LEVEL_ID", referencedColumnName = "YEAR_LEVEL_ID")
    })
    private SemesterYearLevel semesterYearLevel;

    private String name;

    private String description;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classGroup")
    private List<Division> divisions = Lists.newArrayList();

    @JsonIgnore
    @Transient
    private Set<Student> members;
}
