package org.jgrades.api.lic.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.format.DateTimeFormatter;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"name", "version", "validFrom", "validTo"})
@XmlJavaTypeAdapter(type = DateTime.class, value = DateTimeAdapter.class)
public class Product {

    @XmlElement(name = "name", required = true)
    private String name;

    @XmlElement(name = "version", required = true)
    private String version;

    @XmlElement(name = "valid_from", required = true)
    private DateTime validFrom;

    @XmlElement(name = "valid_to", required = true)
    private DateTime validTo;

    public Product() {
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Product rhs = (Product) obj;
        return new EqualsBuilder()
                .append(this.name, rhs.name)
                .append(this.version, rhs.version)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(version)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("version", version)
                .append("validFrom", validFrom)
                .append("validTo", validTo)
                .toString();
    }
}
