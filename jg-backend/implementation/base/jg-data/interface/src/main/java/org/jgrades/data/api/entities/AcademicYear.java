package org.jgrades.data.api.entities;

import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "JG_DATA_ACADEMIC_YEAR")
@Data
public class AcademicYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHOOL_ID", nullable = false)
    private School school;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "academicYear")
    private List<Semester> semesters = Lists.newArrayList();
}
