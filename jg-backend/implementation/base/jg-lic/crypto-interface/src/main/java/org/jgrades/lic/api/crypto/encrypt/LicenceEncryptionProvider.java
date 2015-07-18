package org.jgrades.lic.api.crypto.encrypt;

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
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class LicenceEncryptionProvider {
    private final KeyStoreContentExtractor extractor;

    public LicenceEncryptionProvider(KeyStoreContentExtractor extractor) {
        this.extractor = extractor;
    }

    public byte[] encrypt(Licence licence) {
        byte[] licXmlBytes = transformToBytesArray(licence);
        return encrypt(licXmlBytes);
    }

    private byte[] transformToBytesArray(Licence licence) {
        Marshaller marshaller = LicenceMarshallingFactory.getMarshaller();
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            marshaller.marshal(licence, os);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

    private byte[] encrypt(byte[] licXmlBytes) {
        try {
            Cipher cipher = Cipher.getInstance(LicConstants.CIPHER_PROVIDER_INTERFACE);
            SecretKeySpec privKey = extractor.getPrivateKeyForEncryptionAndDecryption();
            cipher.init(Cipher.ENCRYPT_MODE, privKey);
            return getEncryptedOutputStream(licXmlBytes, cipher).toByteArray();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ByteArrayOutputStream getEncryptedOutputStream(byte[] licXmlBytes, Cipher cipher) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream cos = new CipherOutputStream(bos, cipher);
        IOUtils.write(licXmlBytes, cos);
        cos.close();
        return bos;
    }
}
