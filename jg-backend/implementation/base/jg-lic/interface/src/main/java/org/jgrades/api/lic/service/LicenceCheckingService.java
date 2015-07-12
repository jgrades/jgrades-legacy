package org.jgrades.api.lic.service;

import org.jgrades.api.lic.exception.LicenceException;
import org.jgrades.api.lic.model.Licence;

import java.util.Map;

public interface LicenceCheckingService {
    boolean checkValid(Licence licence) throws LicenceException;

    boolean checkValidForProduct(String productName) throws LicenceException;

    Map<Licence, Boolean> checkPreciselyAllLicences();
}
