/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

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
