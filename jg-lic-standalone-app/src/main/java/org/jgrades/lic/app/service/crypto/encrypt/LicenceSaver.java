package org.jgrades.lic.app.service.crypto.encrypt;

import org.apache.commons.io.FileUtils;
import org.jgrades.lic.app.utils.LicConstants;

import java.io.File;
import java.io.IOException;

public class LicenceSaver {
    private final File licenceFile;

    public LicenceSaver(String licenceFilePath) {
        this.licenceFile = new File(licenceFilePath);
    }

    public void saveLicence(byte[] encryptedXmlFileBytes) throws IOException {
        FileUtils.writeByteArrayToFile(licenceFile, encryptedXmlFileBytes);
    }

    public void saveSignature(byte[] signatureBytes) throws IOException {
        String signatureFilePath = licenceFile.getAbsolutePath() + LicConstants.SIGNATURE_FILE_EXTENSION;
        FileUtils.writeByteArrayToFile(new File(signatureFilePath), signatureBytes);
    }
}
