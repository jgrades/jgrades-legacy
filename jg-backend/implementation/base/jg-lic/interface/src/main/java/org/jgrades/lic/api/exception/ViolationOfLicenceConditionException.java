package org.jgrades.lic.api.exception;

import java.io.IOException;

public class ViolationOfLicenceConditionException extends LicenceException {
    public ViolationOfLicenceConditionException(Exception e) {
        super(e);
    }
}
