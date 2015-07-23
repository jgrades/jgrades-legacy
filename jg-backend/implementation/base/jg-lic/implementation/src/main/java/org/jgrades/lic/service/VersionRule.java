package org.jgrades.lic.service;

import org.jgrades.lic.api.model.Licence;
import org.springframework.beans.factory.annotation.Value;

public class VersionRule implements ValidationRule {
    @Value("${lic.product.release.version}")
    private String version;

    public VersionRule(String version) {
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
