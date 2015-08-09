package org.jgrades.lic.service;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.jgrades.lic.BaseTest;
import org.jgrades.lic.api.exception.LicenceNotFoundException;
import org.jgrades.lic.api.exception.UnreliableLicenceException;
import org.jgrades.lic.api.model.Customer;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;
import org.jgrades.lic.api.model.Product;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.jgrades.lic.dao.LicenceRepository;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenceManagingServiceImplTest extends BaseTest {
    private static final String LICENCE_1 = "correct-licence.lic";
    private static final String SIGNATURE_1 = "correct-licence.lic.sign";
    private static final String LICENCE_2 = "correct-licence-2.lic";
    private static final String SIGNATURE_2 = "correct-licence-2.lic.sign";
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    @Autowired
    private LicenceManagingService managingService;
    @Autowired
    private LicenceRepository licenceRepository;
    @Autowired
    private ConfigurableEnvironment environment;

    @Test
    public void shouldInstallCorrectly_whenLicenceAndSignatureAreCorrect() throws Exception {
        // given
        String licencePath = getPath(LICENCE_1);
        String signaturePath = getPath(SIGNATURE_1);

        // when
        Licence licence = managingService.installLicence(licencePath, signaturePath);

        // then
        assertThat(licenceRepository.findAll()).hasSize(1);
        assertThat(licence).isEqualTo(getLicence1());
    }

    @Test(expected = UnreliableLicenceException.class)
    public void shouldThrow_whenLicenceFileNotFound() throws Exception {
        // given
        File tempEmptyFile = tempFolder.newFile();
        String licencePath = tempEmptyFile.getAbsolutePath();
        tempEmptyFile.delete();

        String signaturePath = getPath(SIGNATURE_1);

        // when
        managingService.installLicence(licencePath, signaturePath);

        // then
        // should throw UnreliableLicenceException
    }

    @Test(expected = UnreliableLicenceException.class)
    public void shouldThrow_whenSignatureFileNotFound() throws Exception {
        // given
        String licencePath = getPath(LICENCE_1);

        File tempEmptyFile = tempFolder.newFile();
        String signaturePath = tempEmptyFile.getAbsolutePath();
        tempEmptyFile.delete();

        // when
        managingService.installLicence(licencePath, signaturePath);

        // then
        // should throw UnreliableLicenceException
    }

    @Test(expected = UnreliableLicenceException.class)
    public void shouldThrow_whenSignatureIsNotValid() throws Exception {
        // given
        String licencePath = getPath(LICENCE_1);
        String signaturePath = getPath(SIGNATURE_2);

        // when
        managingService.installLicence(licencePath, signaturePath);

        // then
        // should throw UnreliableLicenceException
    }

    @Test
    public void shouldUninstallCorrectlyAndRemoveFiles_whenLicenceWasInstalled() throws Exception {
        // given
        licenceRepository.deleteAll();

        String licencePath = getPath(LICENCE_1);
        String signaturePath = getPath(SIGNATURE_1);

        File licenceFile = tempFolder.newFile();
        File signatureFile = tempFolder.newFile();

        FileUtils.copyFile(new File(licencePath), licenceFile);
        FileUtils.copyFile(new File(signaturePath), signatureFile);

        Licence licence = managingService.installLicence(licenceFile.getAbsolutePath(), signatureFile.getAbsolutePath());

        // when
        managingService.uninstallLicence(licence);

        // then
        assertThat(licenceRepository.findAll()).isEmpty();
        assertThat(licenceFile).doesNotExist();
        assertThat(signatureFile).doesNotExist();
    }

    @Test
    public void shouldReturnAllLicencesInSystem() throws Exception {
        // given
        String licencePath1 = getPath(LICENCE_1);
        String signaturePath1 = getPath(SIGNATURE_1);

        String licencePath2 = getPath(LICENCE_2);
        String signaturePath2 = getPath(SIGNATURE_2);

        managingService.installLicence(licencePath1, signaturePath1);
        managingService.installLicence(licencePath2, signaturePath2);

        // when
        List<Licence> licences = managingService.getAll();

        // then
        assertThat(licences).hasSize(2);
        assertThat(licences).containsExactly(getLicence1(), getLicence2());
    }

    @Test
    public void shouldReturnEmptyList_whenThereIsNoLicencesInSystem() throws Exception {
        // when
        List<Licence> licences = managingService.getAll();

        // then
        assertThat(licences).isEmpty();
    }

    @Test(expected = LicenceNotFoundException.class)
    public void shouldThrowException_whenLicenceWIthGivenUidIsNotPresent() throws Exception {
        // given
        Long uid = 1225L;

        // when
        managingService.get(uid);

        // then
        // should throw LicenceNotFoundException
    }

    @After
    public void tearDown() throws Exception {
        licenceRepository.deleteAll();
    }

    private String getPath(String filename) throws IOException {
        return new ClassPathResource(filename).getFile().getAbsolutePath();
    }

    private Licence getLicence1() {
        Licence licence = new Licence();
        licence.setUid(42L);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("XIV LO");
        customer.setAddress("Wroclaw");
        customer.setPhone("+48 71 234 56 78");
        licence.setCustomer(customer);

        Product product = new Product();
        product.setName("JG-BASE");
        product.setVersion("0.4-DEV-SNAPSHOT");
        product.setValidFrom(new DateTime(2015, 7, 25, 0, 0, 0));
        product.setValidTo(new DateTime(2016, 7, 25, 0, 0, 0));
        licence.setProduct(product);

        LicenceProperty property = new LicenceProperty();
        property.setName("expiredDays");
        property.setValue("14");
        List<LicenceProperty> properties = Lists.newArrayList(property);
        licence.setProperties(properties);

        return licence;
    }

    private Licence getLicence2() {
        Licence licence = new Licence();
        licence.setUid(43L);

        Customer customer = new Customer();
        customer.setId(2L);
        customer.setName("XIII LO");
        customer.setAddress("Wroclaw");
        customer.setPhone("+48 71 234 56 78");
        licence.setCustomer(customer);

        Product product = new Product();
        product.setName("JG-BASE");
        product.setVersion("0.4-DEV-SNAPSHOT");
        product.setValidFrom(new DateTime(2015, 7, 25, 0, 0, 0));
        product.setValidTo(new DateTime(2016, 7, 25, 0, 0, 0));
        licence.setProduct(product);

        LicenceProperty property = new LicenceProperty();
        property.setName("expiredDays");
        property.setValue("14");
        List<LicenceProperty> properties = Lists.newArrayList(property);
        licence.setProperties(properties);

        return licence;
    }
}
