package org.jgrades.lic.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "JG_LIC_PRODUCT")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String version;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime validFrom;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime validTo;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(DateTime validFrom) {
        this.validFrom = validFrom;
    }

    public DateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(DateTime validTo) {
        this.validTo = validTo;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("version", version)
                .append("validFrom", validFrom)
                .append("validTo", validTo)
                .toString();
    }
}
