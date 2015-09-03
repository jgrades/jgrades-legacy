package org.jgrades.lic.api.model;

import lombok.Data;

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
