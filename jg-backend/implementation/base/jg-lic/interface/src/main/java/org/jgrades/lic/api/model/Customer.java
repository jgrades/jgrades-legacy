package org.jgrades.lic.api.model;

import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"name", "address", "phone"})
@Data
public final class Customer {
    @XmlAttribute(name = "id")
    private Long id;

    @XmlElement(name = "name", required = true)
    private String name;

    @XmlElement(name = "address")
    private String address;

    @XmlElement(name = "phone")
    private String phone;
}
