package org.jgrades.lic.api.exception;

import java.io.IOException;

public class LicenceException extends RuntimeException {
    public LicenceException(IOException e) {
        super(e);
    }
}
