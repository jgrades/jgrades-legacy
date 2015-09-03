package org.jgrades.data.api.entities;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @Transient
    public Set<Student> getMembers() {
        return getFullClassSubGroup().getMembers();
    }

    public void setMembers(Set<Student> students){
        getFullClassSubGroup().setMembers(students);
    }

    private SubGroup getFullClassSubGroup() {
        Division classGroupDivision = CollectionUtils.find(divisions, division -> Division.FULL_CLASSGROUP_DIVISION_NAME.equals(division.getName()));
        return Iterables.getFirst(classGroupDivision.getSubGroups(), null);
    }
}
