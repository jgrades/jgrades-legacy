package org.jgrades.lic.api.exception;

public class LicenceNotFoundException extends LicenceException {
    public LicenceNotFoundException(Exception e) {
        super(e);
    }

    public LicenceNotFoundException(String message) {
        super(message);
    }
}
