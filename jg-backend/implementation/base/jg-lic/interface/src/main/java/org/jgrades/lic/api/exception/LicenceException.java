package org.jgrades.lic.api.exception;

public class LicenceException extends RuntimeException {
    public LicenceException() {
        super();
    }

    public LicenceException(Exception e) {
        super(e);
    }

    public LicenceException(String message) {
        super(message);
    }
}
