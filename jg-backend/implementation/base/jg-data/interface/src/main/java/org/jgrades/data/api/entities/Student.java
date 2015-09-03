package org.jgrades.data.api.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JG_DATA_STUDENT")
@PrimaryKeyJoinColumn(name = "USER_ID")
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends User implements Serializable {
    private String contactPhone;

    @Column
    @Type(type = CustomType.JODA_LOCAL_DATE)
    private LocalDate dateOfBirth;

    private String nationalIdentificationNumber;

    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Parent parent;
}
