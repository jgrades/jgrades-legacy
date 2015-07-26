package org.jgrades.lic.entities;

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

import java.util.ArrayList;

public class LicenceEntitiesModelTest {
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
