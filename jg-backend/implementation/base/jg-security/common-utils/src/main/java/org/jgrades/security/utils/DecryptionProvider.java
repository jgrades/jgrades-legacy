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

import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.jgrades.security.utils.CryptoDataConstants.CIPHER_PROVIDER_INTERFACE;

public class DecryptionProvider {
    private final KeyStoreContentExtractor extractor;

    public DecryptionProvider(KeyStoreContentExtractor extractor) {
        this.extractor = extractor;
    }

    private static ByteArrayOutputStream getDecryptedOutputStream(byte[] encryptedLicXmlBytes, Cipher cipher) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        CipherOutputStream cos = new CipherOutputStream(bos, cipher);
        IOUtils.write(encryptedLicXmlBytes, cos);
        cos.close();
        return bos;
    }

    public byte[] decrypt(byte[] encryptedBytes) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_PROVIDER_INTERFACE);
            SecretKeySpec secretKeySpec = extractor.getPrivateKeyForEncryptionAndDecryption();
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return getDecryptedOutputStream(encryptedBytes, cipher).toByteArray();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidKeyException | IOException e) {
            throw new CryptographyException(e);
        }
    }
}
