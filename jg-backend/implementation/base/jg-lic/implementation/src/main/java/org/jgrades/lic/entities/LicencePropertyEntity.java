package org.jgrades.lic.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JG_LIC_PROPERTY")
@Data
@EqualsAndHashCode(exclude = {"id"})
public class LicencePropertyEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String value;
}
