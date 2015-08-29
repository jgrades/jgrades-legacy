package org.jgrades.lic.entities;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "JG_LIC_LICENCE")
@Data
@EqualsAndHashCode(of = "uid")
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
}
