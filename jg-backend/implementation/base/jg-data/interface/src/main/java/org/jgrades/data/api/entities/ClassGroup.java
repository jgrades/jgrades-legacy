package org.jgrades.data.api.entities;

import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "JG_DATA_CLASS_GROUP")
@Data
public class ClassGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "SEMESTER_ID", referencedColumnName = "SEMESTER_ID"),
            @JoinColumn(name = "YEAR_LEVEL_ID", referencedColumnName = "YEAR_LEVEL_ID")
    })
    private SemesterYearLevel semesterYearLevel;

    private String name;

    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classGroup")
    private List<Division> divisions = Lists.newArrayList();

    // TODO add methods to quickly setting/extracting members of ClassGroup
//    @Transient
//    public Set<Student> getMembers() {
//        Division classGroupDivision = CollectionUtils.find(divisions, division -> Division.FULL_CLASSGROUP_DIVISION_NAME.equals(division.getName()));
//        SubGroup subGroup = Iterables.getFirst(classGroupDivision.getSubGroups(), null);
//    }
//
//    public void setMembers(Set<Student> students){
//
//    }
}
