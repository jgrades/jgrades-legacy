/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.utils;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import static org.assertj.core.api.Assertions.assertThat;

public class KeyStoreContentExtractorTest {
    private KeyStoreContentExtractor extractor;

    private File keystore;
    private File secData;

    private File cryptoPrivateKey;
    private File signingPrivateKey;
    private File publicKey;

    @Before
    public void setUp() throws Exception {
        keystore = new ClassPathResource("jg-ks-test.jceks").getFile();
        secData = new ClassPathResource("sec-test.dat").getFile();

        cryptoPrivateKey = new ClassPathResource("crypto.key").getFile();
        signingPrivateKey = new ClassPathResource("signing.key").getFile();
        publicKey = new ClassPathResource("pub.key").getFile();

        extractor = new KeyStoreContentExtractor(keystore, secData);
        assertThat(extractor).isNotNull();
    }

    @Test
    public void shouldExtractPrivateKeyForEncryption() throws Exception {
        // when
        SecretKeySpec secretKeySpec = extractor.getPrivateKeyForEncryptionAndDecryption();

        // then
        assertThat(secretKeySpec).isNotNull();
        assertThat(secretKeySpec.getAlgorithm()).isEqualTo("AES");
        assertThat(secretKeySpec.getEncoded()).isEqualTo(FileUtils.readFileToByteArray(cryptoPrivateKey));
    }

    @Test
    public void shouldExtractPrivateKeyForSigning() throws Exception {
        // when
        PrivateKey privateKey = extractor.getPrivateKeyForSigning();

        // then
        assertThat(privateKey).isNotNull();
        assertThat(privateKey.getAlgorithm()).isEqualTo("RSA");
        assertThat(privateKey.getEncoded()).isEqualTo(FileUtils.readFileToByteArray(signingPrivateKey));
    }

    @Test
    public void shouldExtractCertificateForVerification() throws Exception {
        // when
        X509Certificate certificate = extractor.getCertificateForVerification();

        // then
        assertThat(certificate).isNotNull();
        assertThat(certificate.getPublicKey().getAlgorithm()).isEqualTo("RSA");
        assertThat(certificate.getPublicKey().getEncoded()).isEqualTo(FileUtils.readFileToByteArray(publicKey));
    }
}
