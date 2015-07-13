package org.jgrades.lic.api.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "licence")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"customer", "product", "properties"})
public class Licence {
    @XmlAttribute(name = "uid", required = true)
    private Long uid;

    @XmlElement(name = "customer", required = true)
    private Customer customer;

    @XmlElement(name = "product", required = true)
    private Product product;

    @XmlElement(name = "property")
    @XmlElementWrapper(name = "properties")
    private List<LicenceProperty> properties;

    public Licence() {
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<LicenceProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<LicenceProperty> properties) {
        this.properties = properties;
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
        Licence rhs = (Licence) obj;
        return new EqualsBuilder()
                .append(this.uid, rhs.uid)
                .append(this.customer, rhs.customer)
                .append(this.product, rhs.product)
                .append(this.properties, rhs.properties)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uid)
                .append(customer)
                .append(product)
                .append(properties)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uid", uid)
                .append("customer", customer)
                .append("product", product)
                .append("properties", properties)
                .toString();
    }
}
