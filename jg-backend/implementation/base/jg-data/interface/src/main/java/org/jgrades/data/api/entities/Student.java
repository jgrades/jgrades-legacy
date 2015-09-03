package org.jgrades.data.api.entities;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "JG_DATA_STUDENT")
@PrimaryKeyJoinColumn(name = "USER_ID")
@Data
public class Student extends User {
    private String contactPhone;

    @Column
    @Type(type = CustomType.JODA_LOCAL_DATE)
    private LocalDate dateOfBirth;

    private String nationalIdentificationNumber;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID", nullable = false)
    private Parent parent;
}
