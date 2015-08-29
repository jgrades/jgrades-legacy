package org.jgrades.lic.api.model;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "property")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public final class LicenceProperty {
    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlValue
    private String value;
}
