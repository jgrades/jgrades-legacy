package org.jgrades.lic.service;

import org.jgrades.lic.BaseTest;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.dao.LicenceRepository;
import org.jgrades.lic.entities.LicenceEntity;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore("Tests will be delivered soon")
public class LicenceCheckingServiceImplTest extends BaseTest {
    @Value("${lic.product.release.version}")
    private String currentVersion;

    @Autowired
    private LicenceCheckingService checkingService;

    @Autowired
    private LicenceRepository licenceRepository;

    @Test
    public void shouldValid_whenAllRulesAreSatisfied() throws Exception {
        // given
        Licence licence = getValidLicenceWithoutProperties();

        // when
        boolean isValid = checkingService.checkValid(licence);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldNotValid_whenDateRuleIsNotSatisfied() throws Exception {
        // given
        Licence licence = getExpiredLicenceWithoutProperties();

        // when
        boolean isValid = checkingService.checkValid(licence);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotValid_whenMacPropertyIsPresent_andMacRuleIsNotSatisfied() throws Exception {
        // given
        Licence licence = getExpiredLicenceWithStrangeMac();

        // when
        boolean isValid = checkingService.checkValid(licence);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotValid_whenVersionRuleIsNotSatisfied() throws Exception {
        // given
        Licence licence = getExpiredLicenceWithAnotherVersion();

        // when
        boolean isValid = checkingService.checkValid(licence);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldValidForProduct_whenThereIsOneValidLicenceForGivenProduct() throws Exception {
        // given
        String productName = "JG-PLANNER";
        licenceRepository.save(getLicenceEntity(productName, currentVersion, true));

        // when
        boolean isValid = checkingService.checkValidForProduct(productName);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldValidForProduct_whenThereIsOneValidLicenceAndOneInvalidLicenceForGivenProduct() throws Exception {
        // given
        String productName = "JG-PLANNER";
        licenceRepository.save(getLicenceEntity(productName, currentVersion, true));
        licenceRepository.save(getLicenceEntity(productName, currentVersion, false));

        // when
        boolean isValid = checkingService.checkValidForProduct(productName);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldNotValidForProduct_whenThereIsNoLicenceForGivenProduct() throws Exception {
        // given
        String productName = "JG-PLANNER";

        // when
        boolean isValid = checkingService.checkValidForProduct(productName);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotValidForProduct_whenThereIsNoValidLicenceForGivenProduct() throws Exception {
        // given
        String productName = "JG-PLANNER";
        licenceRepository.save(getLicenceEntity(productName, currentVersion, true));
        licenceRepository.save(getLicenceEntity(productName, currentVersion, false));

        // when
        boolean isValid = checkingService.checkValidForProduct("JG-BASE");

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotValidForProduct_whenThereIsNoValidLicenceForGivenProductInCurrentVersion() throws Exception {
        // given
        String productName = "JG-PLANNER";
        licenceRepository.save(getLicenceEntity(productName, "1.0VERSION", true));
        licenceRepository.save(getLicenceEntity(productName, currentVersion, false));

        // when
        boolean isValid = checkingService.checkValidForProduct(productName);

        // then
        assertThat(isValid).isFalse();
    }

    @After
    public void tearDown() throws Exception {
        licenceRepository.deleteAll();
    }

    private Licence getExpiredLicenceWithStrangeMac() {
        return null;
    }

    private Licence getExpiredLicenceWithAnotherVersion() {
        return null;
    }

    private Licence getExpiredLicenceWithoutProperties() {
        return null;
    }

    private Licence getValidLicenceWithoutProperties() {
        return null;
    }

    private LicenceEntity getLicenceEntity(String productName, String version, boolean valid) {
        return null;
    }
}
