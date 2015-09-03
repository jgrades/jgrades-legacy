package org.jgrades.data.api.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "JG_DATA_SUBGROUP")
@Data
public class SubGroup implements Serializable {
    public static final String FULL_CLASSGROUP_SUBGROUP_NAME = "_CLASSGROUP";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIVISION_ID", nullable = false)
    private Division division;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "JG_DATA_SUBGROUP_STUDENT",
            joinColumns = {@JoinColumn(name = "SUBGROUP_ID")},
            inverseJoinColumns = {@JoinColumn(name = "STUDENT_ID")})
    private Set<Student> members = new HashSet<Student>();
}
