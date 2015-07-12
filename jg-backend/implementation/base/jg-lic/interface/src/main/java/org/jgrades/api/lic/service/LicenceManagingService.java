package org.jgrades.api.lic.service;

import org.jgrades.api.lic.exception.LicenceException;
import org.jgrades.api.lic.model.Licence;

import java.util.List;

public interface LicenceManagingService {
    Licence installLicence(String licencePath, String signaturePath) throws LicenceException;

    void uninstallLicence(Licence licence);

    List<Licence> getAll();
}
