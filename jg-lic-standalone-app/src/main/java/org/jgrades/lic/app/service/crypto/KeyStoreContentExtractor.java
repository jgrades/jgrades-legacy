package org.jgrades.lic.app.service.crypto;

import org.jgrades.lic.app.utils.LicConstants;
import org.jgrades.lic.app.utils.SecDatFileContentExtractor;

import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class KeyStoreContentExtractor {
    private final File keystore;
    private final SecDatFileContentExtractor secExtractor;

    public KeyStoreContentExtractor(File keystore, File secDatFile) throws IOException {
        this.keystore = keystore;
        this.secExtractor = new SecDatFileContentExtractor(secDatFile);
    }

    public SecretKeySpec getPrivateKeyForEncryptionAndDecryption() {
        return (SecretKeySpec) getPrivateKey(LicConstants.ENCRYPTION_KEY_ALIAS, secExtractor.getEncryptionDat());
    }

    public PrivateKey getPrivateKeyForSigning() {
        return (PrivateKey) getPrivateKey(LicConstants.SIGNATURE_KEY_ALIAS, secExtractor.getSignatureDat());
    }

    public X509Certificate getCertificateForVerification() {
        return getCertificate(LicConstants.SIGNATURE_KEY_ALIAS);
    }

    private Object getPrivateKey(String alias, char[] secDat) {
        try {
            KeyStore ks = KeyStore.getInstance(LicConstants.KEYSTORE_TYPE);
            ks.load(new FileInputStream(keystore), secExtractor.getKeystoreDat());
            return ks.getKey(alias, secDat);
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException
                | IOException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    private X509Certificate getCertificate(String signatureKeyAlias) {
        try {
            KeyStore ks = KeyStore.getInstance(LicConstants.KEYSTORE_TYPE);
            ks.load(new FileInputStream(keystore), secExtractor.getKeystoreDat());
            return (X509Certificate) ks.getCertificate(signatureKeyAlias);
        } catch (KeyStoreException | CertificateException |
                NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
