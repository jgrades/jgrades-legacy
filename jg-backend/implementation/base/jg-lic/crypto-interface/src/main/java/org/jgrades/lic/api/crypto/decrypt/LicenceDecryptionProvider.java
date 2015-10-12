/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.api.crypto.decrypt;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jgrades.lic.api.crypto.exception.LicenceCryptographyException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceMarshallingFactory;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.security.utils.KeyStoreContentExtractor;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.jgrades.security.utils.CryptoDataConstants.CIPHER_PROVIDER_INTERFACE;

class LicenceDecryptionProvider {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(LicenceDecryptionProvider.class);

    private final KeyStoreContentExtractor extractor;

    public LicenceDecryptionProvider(KeyStoreContentExtractor extractor) {
        this.extractor = extractor;
    }

    private static ByteArrayOutputStream getDecryptedOutputStream(byte[] encryptedLicXmlBytes, Cipher cipher) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        CipherOutputStream cos = new CipherOutputStream(bos, cipher);
        IOUtils.write(encryptedLicXmlBytes, cos);
        cos.close();
        return bos;
    }

    private static Licence transformToObject(byte[] decryptedLicXmlBytes) {
        Unmarshaller unmarshaller = LicenceMarshallingFactory.getUnmarshaller();
        ByteArrayInputStream is = new ByteArrayInputStream(decryptedLicXmlBytes);
        try {
            return (Licence) unmarshaller.unmarshal(is);
        } catch (JAXBException e) {
            LOGGER.error("Unmarshalling raw data to licence format failed", e);
        }
        return null;
    }

    public Licence decrypt(File encryptedLicenceFile) throws LicenceCryptographyException {
        try {
            byte[] encryptedLicXmlBytes = FileUtils.readFileToByteArray(encryptedLicenceFile);
            byte[] decryptedLicXmlBytes = decrypt(encryptedLicXmlBytes);
            return transformToObject(decryptedLicXmlBytes);
        } catch (IOException | InvalidKeyException |
                NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new LicenceCryptographyException(e);
        }
    }

    private byte[] decrypt(byte[] encryptedLicXmlBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Cipher cipher = Cipher.getInstance(CIPHER_PROVIDER_INTERFACE);
        SecretKeySpec secretKeySpec = extractor.getPrivateKeyForEncryptionAndDecryption();
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return getDecryptedOutputStream(encryptedLicXmlBytes, cipher).toByteArray();
    }
}
