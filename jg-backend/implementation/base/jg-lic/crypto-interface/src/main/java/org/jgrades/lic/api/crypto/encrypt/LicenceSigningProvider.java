package org.jgrades.lic.api.crypto.encrypt;

import org.jgrades.lic.api.crypto.utils.KeyStoreContentExtractor;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Optional;

import static org.jgrades.lic.api.crypto.utils.LicConstants.SIGNATURE_PROVIDER_INTERFACE;

class LicenceSigningProvider {
    private final KeyStoreContentExtractor extractor;

    public LicenceSigningProvider(KeyStoreContentExtractor extractor) {
        this.extractor = extractor;
    }

    public byte[] sign(byte[] encryptedLicXmlBytes) {
        if (!Optional.ofNullable(encryptedLicXmlBytes).isPresent()) {
            throw new IllegalArgumentException();
        }
        try {
            Signature signature = Signature.getInstance(SIGNATURE_PROVIDER_INTERFACE);
            signature.initSign(extractor.getPrivateKeyForSigning());
            signature.update(encryptedLicXmlBytes);
            return signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }
}
