package org.jgrades.lic.api.exception;

import java.io.IOException;

public class LicenceNotFoundException extends LicenceException {
    public LicenceNotFoundException(Exception e) {
        super(e);
    }
}
