package org.jgrades.data.api.entities;

import lombok.Data;
import org.jgrades.data.api.model.roles.ParentDetails;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JG_DATA_PARENT")
@PrimaryKeyJoinColumn(name = "USER_ID")
@Data
public class Parent extends User implements ParentDetails, Serializable {
    private String contactPhone;

    private String address;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private Student student;
}
