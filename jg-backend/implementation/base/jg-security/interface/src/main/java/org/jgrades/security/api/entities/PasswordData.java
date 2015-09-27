package org.jgrades.security.api.entities;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JG_SECURITY_PASSWORD_DATA")
@Data
public class PasswordData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    private String password;

    @Column
    @Type(type = CustomType.JODA_DATE_TIME)
    private DateTime lastChange;

    private Integer failedLoginAmount;
}
