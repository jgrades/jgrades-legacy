/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.entities;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
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
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class LicenceEntitiesModelTest {
    private static final List<Class> POJOS = Lists.newArrayList(
            CustomerEntity.class,
            LicenceEntity.class,
            LicencePropertyEntity.class,
            ProductEntity.class
    );

    static {
        System.setProperty("org.apache.commons.logging.Log",
                "org.apache.commons.logging.impl.NoOpLog");

        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.ERROR);
    }

    @Test
    public void shouldSettersAndGettersBePresentForAllFields() {
        // given
        PojoCoverageFilterService filterService = PojoCoverageFilterServiceFactory.configureAndGetPojoCoverageFilterService();
        filterService.registerCoverageDetector(Jacoco.getInstance());

        ArrayList<PojoClass> entitiesClass = Lists.newArrayList(
                filterService.adapt(PojoClassFactory.getPojoClass(CustomerEntity.class)),
                filterService.adapt(PojoClassFactory.getPojoClass(LicenceEntity.class)),
                filterService.adapt(PojoClassFactory.getPojoClass(LicencePropertyEntity.class)),
                filterService.adapt(PojoClassFactory.getPojoClass(ProductEntity.class))
        );

        PojoValidator pojoValidator = new PojoValidator();

        pojoValidator.addRule(new GetterMustExistRule());
        pojoValidator.addRule(new SetterMustExistRule());

        pojoValidator.addTester(new SetterTester());
        pojoValidator.addTester(new GetterTester());

        // when then
        for (PojoClass pojoClass : entitiesClass) {
            pojoValidator.runValidation(pojoClass);
        }
    }
}
