package org.jgrades.data.api.entities;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.jgrades.data.api.model.roles.Roles;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "JG_DATA_USER")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    private String name;

    private String surname;

    private String email;

    private boolean active;

    @Transient
    private Roles roles = new Roles();

    @Column
    @Type(type = CustomType.JODA_DATE_TIME)
    private DateTime lastVisit;
}
