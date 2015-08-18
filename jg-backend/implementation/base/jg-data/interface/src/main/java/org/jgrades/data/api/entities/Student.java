package org.jgrades.data.api.entities;

import org.hibernate.annotations.Type;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "JG_DATA_STUDENT")
@PrimaryKeyJoinColumn(name = "USER_ID")
public class Student extends User {
    private String contactPhone;
    private LocalDate dateOfBirth;
    private String nationalIdentificationNumber;
    private String address;

    private Parent parent;

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Column
    @Type(type = CustomType.JODA_LOCAL_DATE)
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationalIdentificationNumber() {
        return nationalIdentificationNumber;
    }

    public void setNationalIdentificationNumber(String nationalIdentificationNumber) {
        this.nationalIdentificationNumber = nationalIdentificationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID", nullable = false)
    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
