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

import org.jgrades.lic.api.crypto.exception.LicenceCryptographyException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.security.utils.KeyStoreContentExtractor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class LicenceDecryptionService {
    public Licence decrypt(String keystorePath, String secDatPath, String licencePath) throws LicenceCryptographyException, IOException {
        KeyStoreContentExtractor extractor =
                new KeyStoreContentExtractor(new File(keystorePath), new File(secDatPath));
        LicenceDecryptionProvider decryptionProvider = new LicenceDecryptionProvider(extractor);
        return decryptionProvider.decrypt(new File(licencePath));
    }

    public boolean validSignature(String keystorePath, String secDatPath,
                                  String licencePath, String signaturePath) throws LicenceCryptographyException, IOException {
        KeyStoreContentExtractor extractor =
                new KeyStoreContentExtractor(new File(keystorePath), new File(secDatPath));
        SignatureValidator signatureValidator = new SignatureValidator(extractor);
        return signatureValidator.signatureValidated(new File(licencePath), new File(signaturePath));
    }
}
