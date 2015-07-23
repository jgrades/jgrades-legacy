package org.jgrades.lic.api.service;

import org.jgrades.lic.api.exception.LicenceException;
import org.jgrades.lic.api.model.Licence;

public interface LicenceCheckingService {
    boolean checkValid(Licence licence) throws LicenceException;

    boolean checkValidForProduct(String productName) throws LicenceException;
}
