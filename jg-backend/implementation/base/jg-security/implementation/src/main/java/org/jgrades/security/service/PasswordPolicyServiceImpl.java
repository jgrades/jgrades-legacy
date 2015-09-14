package org.jgrades.security.service;

import com.google.common.collect.Sets;
import org.jgrades.data.api.model.roles.JgRole;
import org.jgrades.security.api.dao.PasswordPolicyRepository;
import org.jgrades.security.api.entities.PasswordPolicy;
import org.jgrades.security.api.service.PasswordPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PasswordPolicyServiceImpl implements PasswordPolicyService {
    @Autowired
    private PasswordPolicyRepository pswdPolicyRepository;

    @Override
    public void putPasswordPolicy(PasswordPolicy passwordPolicy) {
        pswdPolicyRepository.save(passwordPolicy);
    }

    @Override
    public PasswordPolicy getForRole(JgRole jgRole) {
        return pswdPolicyRepository.findOne(jgRole);
    }

    @Override
    public Set<PasswordPolicy> getPasswordPolicies() {
        return Sets.newHashSet(pswdPolicyRepository.findAll());
    }
}
