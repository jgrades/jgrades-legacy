/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.api.model;

import com.google.common.collect.Lists;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.coverage.impl.Jacoco;
import com.openpojo.reflection.coverage.service.PojoCoverageFilterService;
import com.openpojo.reflection.coverage.service.PojoCoverageFilterServiceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LicenceModelTest {

    @Test
    public void shouldSettersAndGettersBePresentForAllFields() {
        // given
        PojoCoverageFilterService filterService = PojoCoverageFilterServiceFactory.configureAndGetPojoCoverageFilterService();
        filterService.registerCoverageDetector(Jacoco.getInstance());

        ArrayList<PojoClass> licenceClasses = Lists.newArrayList(
                filterService.adapt(PojoClassFactory.getPojoClass(Customer.class)),
                filterService.adapt(PojoClassFactory.getPojoClass(Licence.class)),
                filterService.adapt(PojoClassFactory.getPojoClass(LicenceProperty.class)),
                filterService.adapt(PojoClassFactory.getPojoClass(Product.class)),
                filterService.adapt(PojoClassFactory.getPojoClass(LicenceValidationResult.class))
        );

        PojoValidator pojoValidator = new PojoValidator();

        pojoValidator.addRule(new GetterMustExistRule());
        pojoValidator.addRule(new SetterMustExistRule());

        pojoValidator.addTester(new SetterTester());
        pojoValidator.addTester(new GetterTester());

        // when then
        for (PojoClass pojoClass : licenceClasses) {
            pojoValidator.runValidation(pojoClass);
        }
    }

    @Test
    public void shouldEqualsMethodUseAllFields() throws Exception {
        // given
        List<Class> classes = Lists.newArrayList(
                Licence.class,
                Customer.class,
                LicenceProperty.class
        );

        // when then
        getConfiguredEqualsVerifierForProduct().verify();
        for (Class clazz : classes) {
            getConfiguredEqualsVerifier(clazz).verify();
        }
    }

    private EqualsVerifier getConfiguredEqualsVerifier(Class clazz) {
        return EqualsVerifier.forClass(clazz)
                .allFieldsShouldBeUsed()
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS);
    }

    private EqualsVerifier getConfiguredEqualsVerifierForProduct() {
        return EqualsVerifier.forClass(Product.class)
                .allFieldsShouldBeUsedExcept("validFrom", "validTo")
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS);
    }
}
