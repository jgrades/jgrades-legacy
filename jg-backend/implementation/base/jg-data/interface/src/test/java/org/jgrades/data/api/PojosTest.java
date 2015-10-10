/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.data.api;

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
import org.jgrades.data.api.entities.*;
import org.jgrades.data.api.entities.roles.*;
import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PojosTest {
    private static final List<Class> POJOS = Lists.newArrayList(
            AcademicYear.class,
            AdministratorDetails.class,
            ClassGroup.class,
            Classroom.class,
            Division.class,
            ManagerDetails.class,
            ParentDetails.class,
            School.class,
            SchoolDay.class,
            SchoolDayPeriod.class,
            Semester.class,
            SemesterYearLevel.class,
            SemesterYearLevelId.class,
            StudentDetails.class,
            SubGroup.class,
            Subject.class,
            TeacherDetails.class,
            User.class,
            YearLevel.class
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

        List<PojoClass> entitiesClass = PojoClassFactory.getPojoClasses("org.jgrades.data.api");

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

    @Test
    public void testGettersAndSetters() throws Exception {
        BeanTester tester = new BeanTester();
        tester.setIterations(1);
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.ignoreProperty("user");
        configurationBuilder.ignoreProperty("student");
        configurationBuilder.ignoreProperty("parent");
        configurationBuilder.ignoreProperty("roles");
        Configuration config = configurationBuilder.build();
        for (Class clazz : POJOS) {
            tester.testBean(clazz, config);
        }
    }
}
