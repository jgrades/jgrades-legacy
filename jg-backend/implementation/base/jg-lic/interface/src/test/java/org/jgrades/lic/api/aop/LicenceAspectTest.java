package org.jgrades.lic.api.aop;

import org.jgrades.lic.api.LicServiceMockConfig;
import org.jgrades.lic.api.config.LicApiConfig;
import org.jgrades.lic.api.exception.LicenceExpiredException;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LicApiConfig.class, LicServiceMockConfig.class})
public class LicenceAspectTest {
    private static final String BASE_PRODUCT_NAME = "JG-BASE";
    private static final String EXAMPLE_CUSTOM_PRODUCT_NAME = FakeService.CUSTOM_PRODUCT_NAME;

    @Autowired
    private LicenceCheckingService licenceService;

    @Autowired
    private FakeService fakeService;

    @Before
    public void setUp() throws Exception {
        reset(licenceService);
    }

    @Test
    public void shouldCheckLicenceForBase_whenMethodRequiredBaseLicenceActivated() throws Exception {
        // when
        fakeService.operationRequiredBaseLicence();

        // then
        verify(licenceService, times(1)).checkValidForProduct(BASE_PRODUCT_NAME);
    }

    @Test
    public void shouldCheckLicenceForBase_forEachInvoking() throws Exception {
        // when
        fakeService.operationRequiredBaseLicence();
        fakeService.operationRequiredBaseLicence();
        fakeService.operationRequiredBaseLicence();

        // then
        verify(licenceService, times(3)).checkValidForProduct(BASE_PRODUCT_NAME);
    }

    /*
        Explaination of weird result:
        http://stackoverflow.com/questions/13564627/spring-aop-not-working-for-method-call-inside-another-method
     */
    @Test
    public void shouldNotCheckLicence_whenInvokingMethodIsInsideAnother() throws Exception {
        // when
        fakeService.someInternalOperationNeedsBaseLicence();

        // then
        verifyNoMoreInteractions(licenceService);
    }

    @Test
    public void shouldCheckCustomProductLicence_whenMethodRequiredThisLicenceActivated() throws Exception {
        // when
        fakeService.operationRequiredCustomComponentName();

        // then
        verify(licenceService, times(1)).checkValidForProduct(EXAMPLE_CUSTOM_PRODUCT_NAME);
    }

    @Test
    public void shouldNotWorking_whenAspectMountedOnStaticMethod() throws Exception {
        // when
        FakeService.foooo();

        // then
        verifyZeroInteractions(licenceService);
    }

    @Test(expected = LicenceExpiredException.class)
    public void shouldThrowException_whenLicenceServiceThrowException() throws Exception {
        // given
        when(licenceService.checkValidForProduct(BASE_PRODUCT_NAME)).thenThrow(LicenceExpiredException.class);

        // when
        fakeService.operationRequiredBaseLicence();

        // then
        // should throw LicenceExpiredException
    }
}
