package org.jgrades.data.api.entities;

import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "JG_DATA_YEAR_LEVEL")
@Data
public class YearLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "YEAR_LEVEL_ID", unique = true, nullable = false)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "pk.semester", cascade = CascadeType.ALL)
    private List<SemesterYearLevel> semesters = Lists.newArrayList();
}
