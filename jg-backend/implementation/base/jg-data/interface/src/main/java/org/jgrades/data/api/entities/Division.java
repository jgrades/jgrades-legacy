package org.jgrades.data.api.entities;

import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "JG_DATA_DIVISION")
@Data
public class Division implements Serializable {
    public static final String FULL_CLASSGROUP_DIVISION_NAME = "_DEFAULT_DIVISION";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLASS_GROUP_ID", nullable = false)
    private ClassGroup classGroup;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "division")
    private List<SubGroup> subGroups = Lists.newArrayList();
}
