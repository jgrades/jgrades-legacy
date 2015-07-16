package org.jgrades.lic.api.service;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.jgrades.lic.api.model.Customer;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;
import org.jgrades.lic.api.model.Product;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenceMarshallingFactoryTest {
    private static final String CORRECT_LICENCE_FILENAME = "correct_licence.xml";
    private static final String INCORRECT_DATETIME_LICENCE_FILENAME = "incorrect_datetime_licence.xml";

    private Marshaller jaxbMarshaller;
    private Unmarshaller jaxbUnmarshaller;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        jaxbMarshaller = LicenceMarshallingFactory.getMarshaller();
        jaxbUnmarshaller = LicenceMarshallingFactory.getUnmarshaller();

        assertThat(jaxbMarshaller).isNotNull();
        assertThat(jaxbUnmarshaller).isNotNull();
    }

    @Test
    public void shouldMarshall_whenCorrectLicence() throws Exception {
        // given
        Licence licence = getCorrectLicence();
        File expectedXmlFile = new File(getResourcePath(CORRECT_LICENCE_FILENAME));
        File workingFile = folder.newFile();

        // when
        jaxbMarshaller.marshal(licence, System.out);
        jaxbMarshaller.marshal(licence, workingFile);

        // then
        assertThat(FileUtils.contentEquals(workingFile, expectedXmlFile));
    }

    @Test
    public void shouldUnmarshall_whenCorrectXML() throws Exception {
        // given
        String licenceFilePath = getResourcePath(CORRECT_LICENCE_FILENAME);

        // when
        Licence licence = (Licence) jaxbUnmarshaller.unmarshal(new File(licenceFilePath));

        // then
        assertThat(licence).isEqualTo(getCorrectLicence());
    }

    @Test(expected = UnmarshalException.class)
    public void shouldFailed_whenIncorrectDateTimeFormat() throws Exception {
        // given
        String licenceFilePath = getResourcePath(INCORRECT_DATETIME_LICENCE_FILENAME);

        // when
        jaxbUnmarshaller.unmarshal(new File(licenceFilePath));

        // then
        // should throw UnmarshalException
    }

    private Licence getCorrectLicence() {
        Licence licence = new Licence();
        licence.setUid(1234L);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("school1");
        customer.setAddress("address1");
        customer.setPhone("+48 601 234 567");

        Product product = new Product();
        product.setName("JG-BASE");
        product.setVersion("0.4");
        product.setValidFrom(new DateTime(0, DateTimeZone.UTC));
        product.setValidTo(new DateTime(0, DateTimeZone.UTC).plusMonths(1));

        LicenceProperty licProperty1 = new LicenceProperty();
        licProperty1.setName("mac");
        licProperty1.setValue("00:0A:E6:3E:FD:E1");

        LicenceProperty licProperty2 = new LicenceProperty();
        licProperty2.setName("expiredDays");
        licProperty2.setValue("14");

        List<LicenceProperty> properties = Lists.newArrayList(licProperty1, licProperty2);

        licence.setCustomer(customer);
        licence.setProduct(product);
        licence.setProperties(properties);

        return licence;
    }

    private String getResourcePath(String fileName) {
        return this.getClass().getClassLoader().getResource(fileName).getFile();
    }
}
