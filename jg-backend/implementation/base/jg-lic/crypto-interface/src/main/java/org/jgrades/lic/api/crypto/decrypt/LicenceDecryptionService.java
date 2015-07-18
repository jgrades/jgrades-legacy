package org.jgrades.lic.api.crypto.decrypt;

import org.jgrades.lic.api.crypto.utils.KeyStoreContentExtractor;
import org.jgrades.lic.api.model.Licence;

import java.io.File;
import java.io.IOException;

public class LicenceDecryptionService {
    public Licence decrypt(String keystorePath, String secDatPath, String licencePath) throws IOException {
        KeyStoreContentExtractor extractor =
                new KeyStoreContentExtractor(new File(keystorePath), new File(secDatPath));
        LicenceDecryptionProvider decryptionProvider = new LicenceDecryptionProvider(extractor);
        return decryptionProvider.decrypt(new File(licencePath));
    }

    public boolean validSignature(String keystorePath, String secDatPath,
                                  String licencePath, String signaturePath) throws IOException {
        KeyStoreContentExtractor extractor =
                new KeyStoreContentExtractor(new File(keystorePath), new File(secDatPath));
        SignatureValidator signatureValidator = new SignatureValidator(extractor);
        return signatureValidator.signatureValidated(new File(licencePath), new File(signaturePath));
    }
}
