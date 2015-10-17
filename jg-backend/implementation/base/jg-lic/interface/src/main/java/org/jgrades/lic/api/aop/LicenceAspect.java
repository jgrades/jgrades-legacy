/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.api.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jgrades.lic.api.exception.LicenceNotFoundException;
import org.jgrades.lic.api.model.LicenceValidationResult;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
class LicenceAspect {
    @Autowired
    private LicenceCheckingService licenceCheckingService;

    @Pointcut(value = "execution(* *(..))")
    private void anyMethod() {
        //empty method for aspectj purposes
    }

    @Before("anyMethod() && @annotation(checkLicence)")
    public void checkLicenceForProductForClassAnnotated(CheckLicence checkLicence) {
        checkLicenceForProduct(checkLicence);
    }

    @Before("anyMethod() && @within(checkLicence)")
    public void checkLicenceForProductForMethodAnnotated(CheckLicence checkLicence) {
        checkLicenceForProduct(checkLicence);
    }

    private void checkLicenceForProduct(CheckLicence checkLicence) {
        String productName = checkLicence.value();
        LicenceValidationResult validationResult = licenceCheckingService.checkValidForProduct(productName);
        if (!validationResult.isValid()) {
            throw new LicenceNotFoundException("This invocation of API needs valid licence for product: " +
                    productName + ", but it failed. Reason: " + validationResult.getErrorMessage());
        }
    }
}
