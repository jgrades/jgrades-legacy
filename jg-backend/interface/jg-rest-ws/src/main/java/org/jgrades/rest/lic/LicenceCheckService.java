package org.jgrades.rest.lic;

import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/licence/check",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class LicenceCheckService {
    @Autowired
    private LicenceManagingService licenceManagingService;

    @Autowired
    private LicenceCheckingService licenceCheckingService;

    @RequestMapping(value = "/{uid}", method = RequestMethod.GET)
    public boolean check(@PathVariable Long uid) {
        Licence licence = licenceManagingService.get(uid);
        return licenceCheckingService.checkValid(licence);
    }

    @RequestMapping(value = "/product/{productName}", method = RequestMethod.GET)
    public boolean checkForProduct(@PathVariable String productName) {
        return licenceCheckingService.checkValidForProduct(productName);
    }
}
