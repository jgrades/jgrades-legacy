package org.jgrades.data.api.entities;


import com.google.common.collect.Lists;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "JG_DATA_SCHOOL")
public class School {
    private Long id;

    private String name;
    private String shortName;
    private String nameOnDiploma;
    private String nameOnGraduateDiploma;
    private SchoolType type;
    private String address;
    private String vatIdentificationNumber;
    private String webpage;
    private String email;
    private String contactPhone;

    private List<AcademicYear> academicYears = Lists.newArrayList();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getNameOnDiploma() {
        return nameOnDiploma;
    }

    public void setNameOnDiploma(String nameOnDiploma) {
        this.nameOnDiploma = nameOnDiploma;
    }

    public String getNameOnGraduateDiploma() {
        return nameOnGraduateDiploma;
    }

    public void setNameOnGraduateDiploma(String nameOnGraduateDiploma) {
        this.nameOnGraduateDiploma = nameOnGraduateDiploma;
    }

    @Enumerated(EnumType.STRING)
    public SchoolType getType() {
        return type;
    }

    public void setType(SchoolType type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVatIdentificationNumber() {
        return vatIdentificationNumber;
    }

    public void setVatIdentificationNumber(String vatIdentificationNumber) {
        this.vatIdentificationNumber = vatIdentificationNumber;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "school")
    public List<AcademicYear> getAcademicYears() {
        return academicYears;
    }

    public void setAcademicYears(List<AcademicYear> academicYears) {
        this.academicYears = academicYears;
    }
}
