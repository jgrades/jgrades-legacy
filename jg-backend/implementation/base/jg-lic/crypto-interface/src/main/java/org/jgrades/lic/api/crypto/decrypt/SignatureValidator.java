package org.jgrades.lic.api.crypto.decrypt;

import org.apache.commons.io.FileUtils;
import org.jgrades.lic.api.crypto.utils.KeyStoreContentExtractor;
import org.jgrades.lic.api.crypto.utils.LicConstants;

import java.io.File;
import java.io.IOException;
import java.security.*;
import java.security.cert.X509Certificate;

import static org.jgrades.lic.api.crypto.utils.LicConstants.*;

class SignatureValidator {
    private final KeyStoreContentExtractor keyExtractor;

    public SignatureValidator(KeyStoreContentExtractor keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    public boolean signatureValidated(File encryptedLicenceFile, File signatureFile) {
        try {
            X509Certificate certificate = keyExtractor.getCertificateForVerification();
            PublicKey publicKey = certificate.getPublicKey();

            Signature signature = Signature.getInstance(SIGNATURE_PROVIDER_INTERFACE);
            signature.initVerify(publicKey);
            signature.update(FileUtils.readFileToByteArray(encryptedLicenceFile));

            return signature.verify(FileUtils.readFileToByteArray(signatureFile));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
