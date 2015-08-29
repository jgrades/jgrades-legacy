package org.jgrades.logging.model;

import ch.qos.logback.classic.Level;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggingConfigurationTest {
    @Test
    public void shouldSettersAndGettersBePresentForAllFields() {
        // given
        PojoCoverageFilterService filterService = PojoCoverageFilterServiceFactory.configureAndGetPojoCoverageFilterService();
        filterService.registerCoverageDetector(Jacoco.getInstance());

        List<PojoClass> entitiesClass = Lists.newArrayList(PojoClassFactory.getPojoClass(LoggingConfiguration.class));

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
    public void shouldEqualsMethodUseAllFields() throws Exception {
        // given
        List<Class> classes = Lists.newArrayList(LoggingConfiguration.class);

        // when then
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

    @Test
    public void shouldHaveAllInformationsInToString() throws Exception {
        // given
        LoggingStrategy strategy = LoggingStrategy.LOG_FILE_PER_LEVEL;
        Level level = Level.WARN;
        String maxFileSize = "100KB";
        Integer maxDays = 7;

        LoggingConfiguration configuration = new LoggingConfiguration(strategy, level, maxFileSize, maxDays);

        // when
        String string = configuration.toString();

        // then
        assertThat(string).contains("LoggingConfiguration(loggingStrategy=LOG_FILE_PER_LEVEL, level=WARN, maxFileSize=100KB, maxDays=7)");
    }
}
