package org.jgrades.lic.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JG_LIC_CUSTOMER")
@Data
@EqualsAndHashCode(exclude = {"id"})

public class CustomerEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private String name;

    private String address;

    private String phone;
}
