package org.jgrades.lic.api.service;

import org.jgrades.lic.api.exception.LicenceException;
import org.jgrades.lic.api.model.Licence;

import java.util.List;

public interface LicenceManagingService {
    Licence installLicence(String licencePath, String signaturePath) throws LicenceException;

    void uninstallLicence(Licence licence);

    Licence get(Long uid);

    List<Licence> getAll();
}
