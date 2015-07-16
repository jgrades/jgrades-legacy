package org.jgrades.lic.app.service.crypto.decrypt;

import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.app.LicenceFactory;
import org.jgrades.lic.app.service.crypto.KeyStoreContentExtractor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenceDecryptionProviderTest {
    private LicenceDecryptionProvider decryptionProvider;

    @Before
    public void setUp() throws Exception {
        File keystore = new ClassPathResource("jg-ks-test.jceks").getFile();
        File secData = new ClassPathResource("sec-test.dat").getFile();
        KeyStoreContentExtractor extractor = new KeyStoreContentExtractor(keystore, secData);

        decryptionProvider = new LicenceDecryptionProvider(extractor);
        assertThat(decryptionProvider).isNotNull();
    }

    @Test
    public void shouldDecryptLicence() throws Exception {
        // given
        File encryptedLicenceFile = new ClassPathResource("encrypted.lic").getFile();
        Licence expectedLicence = LicenceFactory.getCorrectLicence();

        // when
        Licence licence = decryptionProvider.decrypt(encryptedLicenceFile);

        // then
        assertThat(licence).isEqualTo(expectedLicence);
    }


}