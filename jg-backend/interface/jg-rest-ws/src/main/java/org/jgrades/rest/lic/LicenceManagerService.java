package org.jgrades.rest.lic;

import org.apache.commons.io.FileUtils;
import org.jgrades.lic.api.exception.LicenceException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

//TODO refactoring needed
@RestController
@RequestMapping(
        value = "/licence",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class LicenceManagerService {
    @Value("${rest.lic.path}")
    private String licPath;

    @Autowired
    private LicenceManagingService licenceManagingService;

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
        return licenceManagingService.get(uid);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadAndInstall(@RequestParam("file") MultipartFile[] files) {
        if (files.length != 2) {
            return "It should be 2 files";
        }
        MultipartFile licence = files[0];
        MultipartFile signature = files[1];

        if (licence.isEmpty() || signature.isEmpty()) {
            return "Some file/s is/are empty";
        }

        DateTime dateTime = DateTime.now();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyy-MM-dd.HH-mm-ss");
        File licenceFile = new File(licPath + File.separator + fmt.print(dateTime) + ".lic");
        File signatureFile = new File(licenceFile.getAbsolutePath() + ".sign");

        Licence installedLicence = null;
        try {
            FileUtils.writeByteArrayToFile(licenceFile, licence.getBytes());
            FileUtils.writeByteArrayToFile(signatureFile, signature.getBytes());

            installedLicence = licenceManagingService.installLicence(licenceFile.getAbsolutePath(), signatureFile.getAbsolutePath());
        } catch (IOException | LicenceException e) {
            return "Error during installation : " + e.getMessage();
        }
        return "Installed with uid " + installedLicence.getUid();
    }

    @RequestMapping(value = "/{uid}", method = RequestMethod.DELETE)
    public String uninstall(@PathVariable Long uid) {
        Licence licenceToRemove = licenceManagingService.get(uid);
        licenceManagingService.uninstallLicence(licenceToRemove);
        return "Uninstalled " + uid;
    }
}
