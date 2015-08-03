package org.jgrades.rest.lic;

import org.apache.commons.io.FileUtils;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.jgrades.logging.logger.JGLoggingFactory;
import org.jgrades.logging.logger.JGradesLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/licence", produces = MediaType.APPLICATION_JSON_VALUE)
public class LicenceManagerService {
    private static final JGradesLogger LOGGER = JGLoggingFactory.getLogger(LicenceManagerService.class);

    @Autowired
    private LicenceManagingService licenceManagingService;

    @Autowired
    private IncomingFilesNameResolver filesNameResolver;

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<Licence> getAll() {
        return licenceManagingService.getAll();
    }

    @RequestMapping(value = "/{uid}", method = RequestMethod.GET)
    public
    @ResponseBody
    Licence get(@PathVariable Long uid) {
        LOGGER.info("Getting licence with uid {}", uid);
        return licenceManagingService.get(uid);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public
    @ResponseBody
    Licence uploadAndInstall(@RequestParam("licence") MultipartFile licence,
                             @RequestParam("signature") MultipartFile signature) throws IOException {
        LOGGER.info("Licence installation service invoked");
        checkFilesExisting(licence, signature);

        filesNameResolver.init();
        File licenceFile = filesNameResolver.getLicenceFile();
        File signatureFile = filesNameResolver.getSignatureFile();

        LOGGER.info("Saving received licence file to {}", licenceFile.getAbsolutePath());
        FileUtils.writeByteArrayToFile(licenceFile, licence.getBytes());

        LOGGER.info("Saving received signature file to {}", signatureFile.getAbsolutePath());
        FileUtils.writeByteArrayToFile(signatureFile, signature.getBytes());

        return licenceManagingService.installLicence(licenceFile.getAbsolutePath(), signatureFile.getAbsolutePath());
    }

    private void checkFilesExisting(MultipartFile licence, MultipartFile signature) {
        if (licence.isEmpty()) {
            LOGGER.warn("Licence file is empty");
            throw new IllegalArgumentException("Empty licence file");
        } else if (signature.isEmpty()) {
            LOGGER.warn("Signature file is empty");
            throw new IllegalArgumentException("Empty signature file");
        }
    }

    @RequestMapping(value = "/{uid}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    boolean uninstall(@PathVariable Long uid) {
        LOGGER.info("Starting of removing a licence with uid {}", uid);
        Licence licenceToRemove = licenceManagingService.get(uid);
        licenceManagingService.uninstallLicence(licenceToRemove);
        return true;
    }
}
