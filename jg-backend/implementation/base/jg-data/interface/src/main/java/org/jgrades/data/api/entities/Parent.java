package org.jgrades.data.api.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JG_DATA_PARENT")
@PrimaryKeyJoinColumn(name = "USER_ID")
@Data
public class Parent extends User implements Serializable {
    private String contactPhone;

    private String address;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private Student student;
}
