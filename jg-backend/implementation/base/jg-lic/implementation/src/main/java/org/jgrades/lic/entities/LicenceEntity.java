package org.jgrades.lic.entities;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "JG_LIC_LICENCE")
public class LicenceEntity implements Serializable {
    @Id
    private Long uid;

    @OneToOne(cascade = CascadeType.ALL)
    private CustomerEntity customer;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductEntity product;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LicencePropertyEntity> properties = Lists.<LicencePropertyEntity>newArrayList();

    private String licenceFilePath;

    private String signatureFilePath;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long id) {
        this.uid = id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public List<LicencePropertyEntity> getProperties() {
        return properties;
    }

    public void setProperties(List<LicencePropertyEntity> properties) {
        this.properties = properties;
    }

    public String getLicenceFilePath() {
        return licenceFilePath;
    }

    public void setLicenceFilePath(String licenceFilePath) {
        this.licenceFilePath = licenceFilePath;
    }

    public String getSignatureFilePath() {
        return signatureFilePath;
    }

    public void setSignatureFilePath(String signatureFilePath) {
        this.signatureFilePath = signatureFilePath;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uid", uid)
                .append("customer", customer)
                .append("product", product)
                .append("properties", properties)
                .append("licenceFilePath", licenceFilePath)
                .append("signatureFilePath", signatureFilePath)
                .toString();
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
        LicenceEntity rhs = (LicenceEntity) obj;
        boolean equals = new EqualsBuilder()
                .append(this.uid, rhs.uid)
                .append(this.customer, rhs.customer)
                .append(this.product, rhs.product)
                .append(this.licenceFilePath, rhs.licenceFilePath)
                .append(this.signatureFilePath, rhs.signatureFilePath)
                .isEquals();

        return equals && CollectionUtils.containsAll(this.properties, rhs.properties);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uid)
                .toHashCode();
    }
}
