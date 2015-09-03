package org.jgrades.lic.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"name", "version", "validFrom", "validTo"})
@XmlJavaTypeAdapter(type = DateTime.class, value = LicenceDateTimeAdapter.class)
@Data
@EqualsAndHashCode(exclude = {"validFrom", "validTo"})
public final class Product {

    @XmlElement(name = "name", required = true)
    private String name;

    @XmlElement(name = "version", required = true)
    private String version;

    @XmlElement(name = "valid_from", required = true)
    private DateTime validFrom;

    @XmlElement(name = "valid_to", required = true)
    private DateTime validTo;
}
