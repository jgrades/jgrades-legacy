package org.jgrades.lic.api.crypto.encrypt;

import org.jgrades.lic.api.crypto.utils.KeyStoreContentExtractor;
import org.jgrades.lic.api.model.Licence;

import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class LicenceEncryptionService {
    public void encryptAndSign(Licence licence, String keystorePath, String secDatPath, String licencePath) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, JAXBException, SignatureException {
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
