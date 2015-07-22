package org.jgrades.lic.api.exception;

import java.io.IOException;

public class LicenceExpiredException extends LicenceException {
    public LicenceExpiredException(Exception e) {
        super(e);
    }
}
