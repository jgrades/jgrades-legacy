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
import org.jgrades.lic.api.model.Licence;
import org.jgrades.security.utils.KeyStoreContentExtractor;

import java.io.File;
import java.io.IOException;

public class LicenceEncryptionService {
    public void encryptAndSign(Licence licence, String keystorePath, String secDatPath, String licencePath) throws LicenceCryptographyException, IOException {
        KeyStoreContentExtractor extractor =
                new KeyStoreContentExtractor(new File(keystorePath), new File(secDatPath));
        LicenceEncryptionProvider encryptionProvider = new LicenceEncryptionProvider(extractor);
        byte[] encryptedBytes = encryptionProvider.encrypt(licence);
        LicenceSigningProvider signingProvider = new LicenceSigningProvider(extractor);
        byte[] signature = signingProvider.sign(encryptedBytes);
        LicenceSaver saver = new LicenceSaver(licencePath);

        saver.saveLicence(encryptedBytes);
        saver.saveSignature(signature);
    }
}
