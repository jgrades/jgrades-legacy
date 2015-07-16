package org.jgrades.lic.app.service.crypto.encrypt;

import org.jgrades.lic.app.service.crypto.KeyStoreContentExtractor;
import org.jgrades.lic.app.utils.LicConstants;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Optional;

public class LicenceSigningProvider {
    private final KeyStoreContentExtractor extractor;

    public LicenceSigningProvider(KeyStoreContentExtractor extractor) {
        this.extractor = extractor;
    }

    public byte[] sign(byte[] encryptedLicXmlBytes) {
        if (!Optional.ofNullable(encryptedLicXmlBytes).isPresent()) {
            throw new IllegalArgumentException();
        }
        try {
            Signature signature = Signature.getInstance(LicConstants.SIGNATURE_PROVIDER_INTERFACE);
            signature.initSign(extractor.getPrivateKeyForSigning());
            signature.update(encryptedLicXmlBytes);
            return signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }
}
