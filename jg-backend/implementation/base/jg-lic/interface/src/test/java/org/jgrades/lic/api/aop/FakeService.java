package org.jgrades.lic.api.aop;

import org.springframework.stereotype.Component;

@Component
class FakeService {
    public static final String CUSTOM_PRODUCT_NAME = "JG-MESSAGING";

    @CheckLicence
    protected static String foooo() {
        return null;
    }

    @CheckLicence
    public void operationRequiredBaseLicence() {
    }

    public void someInternalOperationNeedsBaseLicence() {
        privateOperationRequiredBaseLicence();
    }

    @CheckLicence
    private void privateOperationRequiredBaseLicence() {

    }

    @CheckLicence("JG-MESSAGING")
    public Object operationRequiredCustomComponentName() {
        return null;
    }
}
