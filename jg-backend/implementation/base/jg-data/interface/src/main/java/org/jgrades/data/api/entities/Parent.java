package org.jgrades.data.api.entities;

import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "JG_DATA_PARENT")
@PrimaryKeyJoinColumn(name = "USER_ID")
@Data
public class Parent extends User {
    private String contactPhone;

    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Student> students = Lists.newArrayList();
}
