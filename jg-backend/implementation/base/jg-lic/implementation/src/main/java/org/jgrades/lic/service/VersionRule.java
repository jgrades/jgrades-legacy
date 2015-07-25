package org.jgrades.lic.service;

import org.jgrades.lic.api.model.Licence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VersionRule implements ValidationRule {
    private String version;

    @Autowired
    public VersionRule(@Value("${lic.product.release.version}") String version) {
        this.version = version;
    }

    @Override
    public boolean isValid(Licence licence) {
        return licence.getProduct().getVersion().equalsIgnoreCase(version);
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
