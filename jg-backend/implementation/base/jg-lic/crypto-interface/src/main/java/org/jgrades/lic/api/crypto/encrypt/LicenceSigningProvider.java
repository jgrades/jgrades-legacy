/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.api.crypto.encrypt;


import org.jgrades.lic.api.crypto.exception.LicenceCryptographyException;
import org.jgrades.security.utils.KeyStoreContentExtractor;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Optional;

import static org.jgrades.security.utils.CryptoDataConstants.SIGNATURE_PROVIDER_INTERFACE;

class LicenceSigningProvider {
    private final KeyStoreContentExtractor extractor;

    public LicenceSigningProvider(KeyStoreContentExtractor extractor) {
        this.extractor = extractor;
    }

    public byte[] sign(byte[] encryptedLicXmlBytes) throws LicenceCryptographyException {
        try {
            if (!Optional.ofNullable(encryptedLicXmlBytes).isPresent()) {
                throw new IllegalArgumentException();
            }
            Signature signature = Signature.getInstance(SIGNATURE_PROVIDER_INTERFACE);
            signature.initSign(extractor.getPrivateKeyForSigning());
            signature.update(encryptedLicXmlBytes);
            return signature.sign();
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new LicenceCryptographyException(e);
        }
    }
}
