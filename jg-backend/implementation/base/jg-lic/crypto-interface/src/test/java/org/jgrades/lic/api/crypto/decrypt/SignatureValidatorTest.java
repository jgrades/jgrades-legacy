package org.jgrades.lic.api.crypto.decrypt;

import org.jgrades.security.utils.KeyStoreContentExtractor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class SignatureValidatorTest {
    private SignatureValidator signatureValidator;

    @Before
    public void setUp() throws Exception {
        File keystore = new ClassPathResource("jg-ks-test.jceks").getFile();
        File secData = new ClassPathResource("sec-test.dat").getFile();
        KeyStoreContentExtractor extractor = new KeyStoreContentExtractor(keystore, secData);

        signatureValidator = new SignatureValidator(extractor);
        assertThat(signatureValidator).isNotNull();
    }

    @Test
    public void shouldValidate_whenCorrectSignaturePassed() throws Exception {
        // given
        File encryptedLicenceFile = new ClassPathResource("encrypted.lic").getFile();
        File signatureFile = new ClassPathResource("encrypted.lic.sign").getFile();

        // when
        boolean validationSuccess = signatureValidator.signatureValidated(encryptedLicenceFile, signatureFile);

        // then
        assertThat(validationSuccess).isTrue();
    }

    @Test
    public void shouldNotValidate_whenBrokenSignaturePassed() throws Exception {
        // given
        File encryptedLicenceFile = new ClassPathResource("encrypted.lic").getFile();
        File signatureFile = new ClassPathResource("encrypted.lic.sign.broken").getFile();

        // when
        boolean validationSuccess = signatureValidator.signatureValidated(encryptedLicenceFile, signatureFile);

        // then
        assertThat(validationSuccess).isFalse();
    }

    @Test
    public void shouldNotValidate_whenSignatureWasCreatedWithAnotherPrivateKey() throws Exception {
        //TODO - generation of new key pair needed
    }
}
