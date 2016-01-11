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

import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.jgrades.security.utils.CryptoDataConstants.CIPHER_PROVIDER_INTERFACE;

public class EncryptionProvider {
    private final KeyStoreContentExtractor extractor;

    public EncryptionProvider(KeyStoreContentExtractor extractor) {
        this.extractor = extractor;
    }

    private static ByteArrayOutputStream getEncryptedOutputStream(byte[] licXmlBytes, Cipher cipher) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream cos = new CipherOutputStream(bos, cipher);
        IOUtils.write(licXmlBytes, cos);
        cos.close();
        return bos;
    }

    public byte[] encrypt(byte[] bytes) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_PROVIDER_INTERFACE);
            SecretKeySpec privKey = extractor.getPrivateKeyForEncryptionAndDecryption();
            cipher.init(Cipher.ENCRYPT_MODE, privKey);
            return getEncryptedOutputStream(bytes, cipher).toByteArray();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                IOException | InvalidKeyException e) {
            throw new CryptographyException(e);
        }
    }
}
