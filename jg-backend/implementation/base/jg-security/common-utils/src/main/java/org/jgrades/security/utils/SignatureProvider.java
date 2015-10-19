/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Optional;

import static org.jgrades.security.utils.CryptoDataConstants.SIGNATURE_PROVIDER_INTERFACE;

public class SignatureProvider {
    private final KeyStoreContentExtractor extractor;

    public SignatureProvider(KeyStoreContentExtractor extractor) {
        this.extractor = extractor;
    }

    public byte[] sign(byte[] bytes) throws CryptographyException {
        try {
            if (!Optional.ofNullable(bytes).isPresent()) {
                throw new IllegalArgumentException();
            }
            Signature signature = Signature.getInstance(SIGNATURE_PROVIDER_INTERFACE);
            signature.initSign(extractor.getPrivateKeyForSigning());
            signature.update(bytes);
            return signature.sign();
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new CryptographyException(e);
        }
    }

    public boolean signatureValidated(File encryptedLicenceFile, File signatureFile) {
        try {
            X509Certificate certificate = extractor.getCertificateForVerification();
            PublicKey publicKey = certificate.getPublicKey();

            Signature signature = Signature.getInstance(SIGNATURE_PROVIDER_INTERFACE);
            signature.initVerify(publicKey);

            signature.update(FileUtils.readFileToByteArray(encryptedLicenceFile));

            return signature.verify(FileUtils.readFileToByteArray(signatureFile));
        } catch (SignatureException e) {
            return false;
        } catch (NoSuchAlgorithmException | InvalidKeyException | IOException e) {
            throw new CryptographyException(e);
        }
    }
}
