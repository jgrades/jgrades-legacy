package org.jgrades.data.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Data;
import org.jgrades.data.api.model.SchoolType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "JG_DATA_SCHOOL")
@Data
public class School implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String shortName;

    private String nameOnDiploma;

    private String nameOnGraduateDiploma;

    @Enumerated(EnumType.STRING)
    private SchoolType type;

    private String address;

    private String vatIdentificationNumber;

    private String webpage;

    private String email;

    private String contactPhone;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "school")
    private List<AcademicYear> academicYears = Lists.newArrayList();
}
