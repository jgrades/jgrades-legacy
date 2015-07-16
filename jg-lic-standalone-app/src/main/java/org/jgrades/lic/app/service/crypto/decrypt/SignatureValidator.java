package org.jgrades.lic.app.service.crypto.decrypt;

import org.apache.commons.io.FileUtils;
import org.jgrades.lic.app.service.crypto.KeyStoreContentExtractor;
import org.jgrades.lic.app.utils.LicConstants;

import java.io.File;
import java.io.IOException;
import java.security.*;
import java.security.cert.X509Certificate;

public class SignatureValidator {
    private final KeyStoreContentExtractor keyExtractor;

    public SignatureValidator(KeyStoreContentExtractor keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    public boolean signatureValidated(File encryptedLicenceFile, File signatureFile) {
        try {
            X509Certificate certificate = keyExtractor.getCertificateForVerification();
            PublicKey publicKey = certificate.getPublicKey();

            Signature signature = Signature.getInstance(LicConstants.SIGNATURE_PROVIDER_INTERFACE);
            signature.initVerify(publicKey);
            signature.update(FileUtils.readFileToByteArray(encryptedLicenceFile));

            return signature.verify(FileUtils.readFileToByteArray(signatureFile));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
