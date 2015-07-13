package org.jgrades.lic.api.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jgrades.lic.api.exception.LicenceException;
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
    }

    @Before("anyMethod() && @annotation(checkLicence)")
    public void checkLicenceForProduct(CheckLicence checkLicence) throws LicenceException {
        String productName = checkLicence.value();
        licenceCheckingService.checkValidForProduct(productName);
    }
}
