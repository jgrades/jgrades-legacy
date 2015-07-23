package org.jgrades.lic.service;

import org.jgrades.lic.BaseTest;
import org.junit.Test;

public class LicenceCheckingServiceImplTest extends BaseTest {

    @Test
    public void shouldValid_whenAllRulesAreSatisfied() throws Exception {
        //TODO
    }

    @Test
    public void shouldNotValid_whenDateRuleIsNotSatisfied() throws Exception {
        //TODO
    }

    @Test
    public void shouldNotValid_whenMacPropertyIsPresent_andMacRuleIsNotSatisfied() throws Exception {
        //TODO
    }

    @Test
    public void shouldNotValid_whenVersionRuleIsNotSatisfied() throws Exception {
        //TODO
    }

    @Test
    public void shouldValidForProduct_whenThereIsOneValidLicenceForGivenProduct() throws Exception {
        //TODO
    }

    @Test
    public void shouldValidForProduct_whenThereIsOneValidLicenceAndOneInvalidLicenceForGivenProduct() throws Exception {
        //TODO
    }

    @Test
    public void shouldNotValidForProduct_whenThereIsNoLicenceForGivenProduct() throws Exception {
        //TODO
    }

    @Test
    public void shouldNotValidForProduct_whenThereIsNoValidLicenceForGivenProduct() throws Exception {
        //TODO
    }
}
