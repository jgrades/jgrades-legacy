package org.jgrades.rest.lic;

import org.jgrades.lic.api.exception.LicenceNotFoundException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.jgrades.rest.RestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LicMockConfig.class, RestConfig.class})
@WebAppConfiguration
public class LicenceCheckServiceTest {
    private MockMvc mockMvc;

    @Autowired
    private LicenceCheckService licenceCheckService;

    @Autowired
    private LicenceManagingService licenceManagingServiceMock;

    @Autowired
    private LicenceCheckingService licenceCheckingServiceMock;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(licenceCheckService)
                .setHandlerExceptionResolvers(new RestConfig().restExceptionResolver())
                .build();
        Mockito.reset(licenceManagingServiceMock, licenceCheckingServiceMock);
    }

    @Test
    public void shouldConfirmValid_whenLicenceIsValid() throws Exception {
        // given
        Long uid = 1234L;
        Licence licence = new Licence();

        when(licenceManagingServiceMock.get(eq(uid))).thenReturn(licence);
        when(licenceCheckingServiceMock.checkValid(eq(licence))).thenReturn(true);

        // when
        mockMvc.perform(get("/licence/check/{uid}", uid))
                .andExpect(status().isOk())
                .andExpect(content().string(is("true")));

        // then
        verify(licenceManagingServiceMock, times(1)).get(uid);
        verify(licenceCheckingServiceMock, times(1)).checkValid(licence);
    }

    @Test
    public void shouldConfirmNotValid_whenLicenceIsNotValid() throws Exception {
        // given
        Long uid = 1234L;
        Licence licence = new Licence();

        when(licenceManagingServiceMock.get(eq(uid))).thenReturn(licence);
        when(licenceCheckingServiceMock.checkValid(eq(licence))).thenReturn(false);

        // when
        mockMvc.perform(get("/licence/check/{uid}", uid))
                .andExpect(status().isOk())
                .andExpect(content().string(is("false")));

        // then
        verify(licenceManagingServiceMock, times(1)).get(uid);
        verify(licenceCheckingServiceMock, times(1)).checkValid(licence);
    }

    @Test
    public void shouldReturnNotFoundStatus_whenLicenceNotFound() throws Exception {
        // given
        when(licenceManagingServiceMock.get(anyLong())).thenReturn(null);
        when(licenceCheckingServiceMock.checkValid(null)).thenThrow(LicenceNotFoundException.class);

        // when then
        mockMvc.perform(get("/licence/check/{uid}", 1500100900L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", is(LicenceNotFoundException.class.getSimpleName())));
    }

    @Test
    public void shouldConfirmValidForProduct_whenLicenceForProductIsValid() throws Exception {
        // given
        String productName = "JG-BASE";

        when(licenceCheckingServiceMock.checkValidForProduct(productName)).thenReturn(true);

        // when
        mockMvc.perform(get("/licence/check/product/{productName}", productName))
                .andExpect(status().isOk())
                .andExpect(content().string(is("true")));

        // then
        verify(licenceCheckingServiceMock, times(1)).checkValidForProduct(productName);
    }

    @Test
    public void shouldConfirmNotValidForProduct_whenLicenceForProductIsNotValid() throws Exception {
        // given
        String productName = "JG-BASE";

        when(licenceCheckingServiceMock.checkValidForProduct(productName)).thenReturn(false);

        // when
        mockMvc.perform(get("/licence/check/product/{productName}", productName))
                .andExpect(status().isOk())
                .andExpect(content().string(is("false")));

        // then
        verify(licenceCheckingServiceMock, times(1)).checkValidForProduct(productName);
    }
}
