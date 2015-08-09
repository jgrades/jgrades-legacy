package org.jgrades.rest.lic;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.lic.api.exception.LicenceNotFoundException;
import org.jgrades.lic.api.model.Customer;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.Product;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.jgrades.rest.RestConfig;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LicMockConfig.class, RestConfig.class})
@WebAppConfiguration
public class LicenceManagerServiceTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    private MockMvc mockMvc;
    @Autowired
    private LicenceManagerService licenceManagerService;

    @Autowired
    private LicenceManagingService licenceManagingServiceMock;

    @Autowired
    private IncomingFilesNameResolver incomingFilesNameResolverMock;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(licenceManagerService)
                .setHandlerExceptionResolvers(new RestConfig().restExceptionResolver())
                .build();
        Mockito.reset(licenceManagingServiceMock, incomingFilesNameResolverMock);
    }

    @Test
    public void shouldReturnEmptyJson_whenNoLicenceInSystem() throws Exception {
        // given
        when(licenceManagingServiceMock.getAll()).thenReturn(Lists.newArrayList());

        // when
        mockMvc.perform(get("/licence"))
                .andExpect(status().isOk())
                .andExpect(content().string(is("[]")));

        // then
        verify(licenceManagingServiceMock, times(1)).getAll();
    }

    @Test
    public void shouldReturnLicence_whenSingleIsInSystem() throws Exception {
        // given
        Licence licence = getExampleLicence();
        when(licenceManagingServiceMock.getAll()).thenReturn(Lists.newArrayList(licence));

        // when
        mockMvc.perform(get("/licence"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].uid", is(licence.getUid().intValue())))
                .andExpect(jsonPath("$[0].customer.name", is(licence.getCustomer().getName())))
                .andExpect(jsonPath("$[0].product.version", is(licence.getProduct().getVersion())));

        // then
        verify(licenceManagingServiceMock, times(1)).getAll();
    }

    @Test
    public void shouldReturnLicenceWithGivenUid_whenIsInSystem() throws Exception {
        // given
        Long uid = 122L;
        Licence licence = getExampleLicence();
        when(licenceManagingServiceMock.get(eq(uid))).thenReturn(licence);

        // when
        mockMvc.perform(get("/licence/{uid}", uid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid", is(licence.getUid().intValue())))
                .andExpect(jsonPath("$.customer.name", is(licence.getCustomer().getName())))
                .andExpect(jsonPath("$.product.version", is(licence.getProduct().getVersion())));

        // then
        verify(licenceManagingServiceMock, times(1)).get(uid);
    }

    @Test
    public void shouldReturnNotFound_whenLicenceWithGivenUidIsNotInSystem() throws Exception {
        // given
        Long uid = 122L;
        when(licenceManagingServiceMock.get(eq(uid))).thenThrow(LicenceNotFoundException.class);

        // when
        mockMvc.perform(get("/licence/{uid}", uid))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", is(LicenceNotFoundException.class.getSimpleName())));

        // then
        verify(licenceManagingServiceMock, times(1)).get(uid);
    }

    @Test
    public void shouldProcessMultipartFiles_andCorrectlyInstallLicence() throws Exception {
        // given
        Licence licence = getExampleLicence();

        File licenceTargetFile = tempFolder.newFile();
        File signatureTargetFile = tempFolder.newFile();

        when(incomingFilesNameResolverMock.getLicenceFile()).thenReturn(licenceTargetFile);
        when(incomingFilesNameResolverMock.getSignatureFile()).thenReturn(signatureTargetFile);

        byte[] licenceFileContent = "LIC_CONTENT".getBytes();
        byte[] signatureFileContent = "SIGNATURE_CONTENT".getBytes();

        MockMultipartFile licenceInputFile = new MockMultipartFile("licence", "jg.lic", "text/plain", licenceFileContent);
        MockMultipartFile signatureInputFile = new MockMultipartFile("signature", "jg.lic.sign", "text/plain", signatureFileContent);

        when(licenceManagingServiceMock.installLicence(
                        eq(licenceTargetFile.getAbsolutePath()),
                        eq(signatureTargetFile.getAbsolutePath()))
        ).thenReturn(licence);

        // when
        mockMvc.perform(
                fileUpload("/licence")
                        .file(licenceInputFile)
                        .file(signatureInputFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid", is(licence.getUid().intValue())))
                .andExpect(jsonPath("$.customer.name", is(licence.getCustomer().getName())))
                .andExpect(jsonPath("$.product.version", is(licence.getProduct().getVersion())));

        // then
        assertThat(FileUtils.readFileToByteArray(licenceTargetFile)).isEqualTo(licenceFileContent);
        assertThat(FileUtils.readFileToByteArray(signatureTargetFile)).isEqualTo(signatureFileContent);

        verify(licenceManagingServiceMock, times(1))
                .installLicence(licenceTargetFile.getAbsolutePath(), signatureTargetFile.getAbsolutePath());
    }

    @Test
    public void shouldThrowException_whenLicenceFileIsEmpty() throws Exception {
        // given
        byte[] licenceFileContent = StringUtils.EMPTY.getBytes();
        byte[] signatureFileContent = "SIGNATURE_CONTENT".getBytes();

        MockMultipartFile licenceInputFile = new MockMultipartFile("licence", "jg.lic", "text/plain", licenceFileContent);
        MockMultipartFile signatureInputFile = new MockMultipartFile("signature", "jg.lic.sign", "text/plain", signatureFileContent);

        // when then
        mockMvc.perform(
                fileUpload("/licence")
                        .file(licenceInputFile)
                        .file(signatureInputFile))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", is(IllegalArgumentException.class.getSimpleName())));
    }

    @Test
    public void shouldThrowException_whenSignatureFileIsEmpty() throws Exception {
        // given
        byte[] licenceFileContent = "LIC_CONTENT".getBytes();
        byte[] signatureFileContent = StringUtils.EMPTY.getBytes();

        MockMultipartFile licenceInputFile = new MockMultipartFile("licence", "jg.lic", "text/plain", licenceFileContent);
        MockMultipartFile signatureInputFile = new MockMultipartFile("signature", "jg.lic.sign", "text/plain", signatureFileContent);

        // when then
        mockMvc.perform(fileUpload("/licence")
                .file(licenceInputFile)
                .file(signatureInputFile))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", is(IllegalArgumentException.class.getSimpleName())));
    }

    @Test
    public void shouldUninstall_whenLicenceExistInSystem() throws Exception {
        // given
        Long uid = 123L;
        Licence licence = new Licence();

        when(licenceManagingServiceMock.get(eq(uid))).thenReturn(licence);

        // when
        mockMvc.perform(delete("/licence/{uid}", uid))
                .andExpect(status().isOk())
                .andExpect(content().string(is("true")));

        // then
        verify(licenceManagingServiceMock, times(1)).get(uid);
        verify(licenceManagingServiceMock, times(1)).uninstallLicence(licence);
    }

    private Licence getExampleLicence() {
        Licence licence = new Licence();
        licence.setUid(122L);

        Customer customer = new Customer();
        customer.setId(12L);
        customer.setName("XIV LO");

        Product product = new Product();
        product.setName("JG-BASE");
        product.setValidFrom(DateTime.now());
        product.setValidTo(DateTime.now().plusMonths(1));
        product.setVersion("0.4");

        licence.setCustomer(customer);
        licence.setProduct(product);
        return licence;
    }
}
