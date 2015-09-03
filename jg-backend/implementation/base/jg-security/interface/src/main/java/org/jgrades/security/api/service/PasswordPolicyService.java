package org.jgrades.security.api.service;

import org.jgrades.data.api.model.roles.JgRole;
import org.jgrades.security.api.entities.PasswordPolicy;

import java.util.Set;

public interface PasswordPolicyService {
    void putPasswordPolicy(PasswordPolicy passwordPolicy);

    PasswordPolicy getForRole(JgRole jgRole);

    Set<PasswordPolicy> getPasswordPolicies();
}
