package org.jgrades.api.lic.model;

import com.google.common.collect.Lists;
import com.openpojo.reflection.PojoClass;
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
        ArrayList<PojoClass> licenceClasses = Lists.newArrayList(
                PojoClassFactory.getPojoClass(Customer.class),
                PojoClassFactory.getPojoClass(Licence.class),
                PojoClassFactory.getPojoClass(LicenceProperty.class),
                PojoClassFactory.getPojoClass(Product.class)
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
