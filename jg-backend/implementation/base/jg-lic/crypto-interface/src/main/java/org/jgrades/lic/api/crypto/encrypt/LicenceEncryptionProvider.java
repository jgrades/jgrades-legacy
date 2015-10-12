/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.api.crypto.encrypt;

import org.apache.commons.io.IOUtils;
import org.jgrades.lic.api.crypto.exception.LicenceCryptographyException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceMarshallingFactory;
import org.jgrades.security.utils.KeyStoreContentExtractor;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.jgrades.security.utils.CryptoDataConstants.CIPHER_PROVIDER_INTERFACE;

class LicenceEncryptionProvider {
    private final KeyStoreContentExtractor extractor;

    public LicenceEncryptionProvider(KeyStoreContentExtractor extractor) {
        this.extractor = extractor;
    }

    private static byte[] transformToBytesArray(Licence licence) throws JAXBException {
        Marshaller marshaller = LicenceMarshallingFactory.getMarshaller();
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        marshaller.marshal(licence, os);
        return os.toByteArray();
    }

    private static ByteArrayOutputStream getEncryptedOutputStream(byte[] licXmlBytes, Cipher cipher) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream cos = new CipherOutputStream(bos, cipher);
        IOUtils.write(licXmlBytes, cos);
        cos.close();
        return bos;
    }

    public byte[] encrypt(Licence licence) throws LicenceCryptographyException {
        try {
            byte[] licXmlBytes = transformToBytesArray(licence);
            return encrypt(licXmlBytes);
        } catch (JAXBException | InvalidKeyException |
                NoSuchAlgorithmException | NoSuchPaddingException |
                IOException e) {
            throw new LicenceCryptographyException(e);
        }
    }

    private byte[] encrypt(byte[] licXmlBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Cipher cipher = Cipher.getInstance(CIPHER_PROVIDER_INTERFACE);
        SecretKeySpec privKey = extractor.getPrivateKeyForEncryptionAndDecryption();
        cipher.init(Cipher.ENCRYPT_MODE, privKey);
        return getEncryptedOutputStream(licXmlBytes, cipher).toByteArray();
    }
}
