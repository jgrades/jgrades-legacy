package org.jgrades.lic.api.crypto.decrypt;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jgrades.lic.api.crypto.utils.KeyStoreContentExtractor;
import org.jgrades.lic.api.crypto.utils.LicConstants;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceMarshallingFactory;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.jgrades.lic.api.crypto.utils.LicConstants.*;

class LicenceDecryptionProvider {
    private final KeyStoreContentExtractor extractor;

    public LicenceDecryptionProvider(KeyStoreContentExtractor extractor) {
        this.extractor = extractor;
    }

    public Licence decrypt(File encryptedLicenceFile) throws IOException {
        byte[] encryptedLicXmlBytes = FileUtils.readFileToByteArray(encryptedLicenceFile);
        byte[] decryptedLicXmlBytes = decrypt(encryptedLicXmlBytes);
        return transformToObject(decryptedLicXmlBytes);
    }

    private byte[] decrypt(byte[] encryptedLicXmlBytes) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_PROVIDER_INTERFACE);
            SecretKeySpec secretKeySpec = extractor.getPrivateKeyForEncryptionAndDecryption();
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return getDecryptedOutputStream(encryptedLicXmlBytes, cipher).toByteArray();
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ByteArrayOutputStream getDecryptedOutputStream(byte[] encryptedLicXmlBytes, Cipher cipher) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        CipherOutputStream cos = new CipherOutputStream(bos, cipher);
        IOUtils.write(encryptedLicXmlBytes, cos);
        cos.close();
        return bos;
    }

    private Licence transformToObject(byte[] decryptedLicXmlBytes) {
        Unmarshaller unmarshaller = LicenceMarshallingFactory.getUnmarshaller();
        ByteArrayInputStream is = new ByteArrayInputStream(decryptedLicXmlBytes);
        try {
            return (Licence) unmarshaller.unmarshal(is);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
