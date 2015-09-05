package org.jgrades.security.api.entities;

import lombok.Data;
import org.jgrades.data.api.model.roles.JgRole;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JG_SECURITY_PASSWORD_POLICY")
@Data
public class PasswordPolicy implements Serializable {
    @Id
    @Enumerated(EnumType.STRING)
    private JgRole jgRole;

    private Integer expirationDays;

    private Integer minimumLength;

    private Integer maximumNumberOfFailedLogin;
}
