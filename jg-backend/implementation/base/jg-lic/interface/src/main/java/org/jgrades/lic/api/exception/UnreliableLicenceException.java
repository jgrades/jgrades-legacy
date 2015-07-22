package org.jgrades.lic.api.exception;

import java.io.IOException;

public class UnreliableLicenceException extends LicenceException {
    public UnreliableLicenceException(Exception e) {
        super(e);
    }
}
