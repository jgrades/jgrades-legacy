package org.jgrades.lic.api.crypto.utils;

import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static org.jgrades.lic.api.crypto.utils.LicConstants.*;

public class KeyStoreContentExtractor {
    private final File keystore;
    private final SecDatFileContentExtractor secExtractor;

    public KeyStoreContentExtractor(File keystore, File secDatFile) throws IOException {
        this.keystore = keystore;
        this.secExtractor = new SecDatFileContentExtractor(secDatFile);
    }

    public SecretKeySpec getPrivateKeyForEncryptionAndDecryption() {
        return (SecretKeySpec) getPrivateKey(ENCRYPTION_KEY_ALIAS, secExtractor.getEncryptionDat());
    }

    public PrivateKey getPrivateKeyForSigning() {
        return (PrivateKey) getPrivateKey(SIGNATURE_KEY_ALIAS, secExtractor.getSignatureDat());
    }

    public X509Certificate getCertificateForVerification() {
        return getCertificate(SIGNATURE_KEY_ALIAS);
    }

    private Object getPrivateKey(String alias, char[] secDat) {
        try {
            KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE);
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
            KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE);
            ks.load(new FileInputStream(keystore), secExtractor.getKeystoreDat());
            return (X509Certificate) ks.getCertificate(signatureKeyAlias);
        } catch (KeyStoreException | CertificateException |
                NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
