package org.jgrades.lic.app.service.crypto.encrypt;

import org.apache.commons.io.FileUtils;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.app.LicenceFactory;
import org.jgrades.lic.app.service.crypto.KeyStoreContentExtractor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenceEncryptionProviderTest {
    private LicenceEncryptionProvider encryptionProvider;

    @Before
    public void setUp() throws Exception {
        File keystore = new ClassPathResource("jg-ks-test.jceks").getFile();
        File secData = new ClassPathResource("sec-test.dat").getFile();
        KeyStoreContentExtractor extractor = new KeyStoreContentExtractor(keystore, secData);

        encryptionProvider = new LicenceEncryptionProvider(extractor);
        assertThat(encryptionProvider).isNotNull();
    }

    @Test
    //@Ignore("CI failed. Ignore temporary")
    public void shouldEncryptLicence() throws Exception {
        // given
        Licence licence = LicenceFactory.getCorrectLicence();

        File encryptedLicence = new ClassPathResource("encrypted.lic").getFile();
        System.out.println("md5encrypted: "+DigestUtils.md5DigestAsHex(FileUtils.readFileToByteArray(encryptedLicence)));
        System.out.println("md5ks: "+DigestUtils.md5DigestAsHex(FileUtils.readFileToByteArray(new ClassPathResource("jg-ks-test.jceks").getFile())));
        System.out.println("md5sec: "+DigestUtils.md5DigestAsHex(FileUtils.readFileToByteArray(new ClassPathResource("sec-test.dat").getFile())));


        // when
        byte[] encryptedBytes = encryptionProvider.encrypt(licence);

        // then
        assertThat(encryptedBytes).isEqualTo(FileUtils.readFileToByteArray(encryptedLicence));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenNullPassed() throws Exception {
        // when
        encryptionProvider.encrypt(null);

        // then
        // should throw IllegalArgumentException
    }
}
