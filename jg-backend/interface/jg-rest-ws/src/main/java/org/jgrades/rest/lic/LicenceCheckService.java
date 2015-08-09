package org.jgrades.rest.lic;

import io.swagger.annotations.ApiOperation;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.jgrades.logging.logger.JGLoggingFactory;
import org.jgrades.logging.logger.JGradesLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/licence/check", produces = MediaType.APPLICATION_JSON_VALUE)
public class LicenceCheckService {
    private static final JGradesLogger LOGGER = JGLoggingFactory.getLogger(LicenceCheckService.class);

    @Autowired
    private LicenceManagingService licenceManagingService;

    @Autowired
    private LicenceCheckingService licenceCheckingService;

    @RequestMapping(value = "/{uid}", method = RequestMethod.GET)
    @ApiOperation(value = "Check is licence with given UID valid")
    public
    @ResponseBody
    boolean check(@PathVariable Long uid) {
        LOGGER.info("Checking licence with uid {}", uid);
        Licence licence = licenceManagingService.get(uid);
        return licenceCheckingService.checkValid(licence);
    }

    @RequestMapping(value = "/product/{productName}", method = RequestMethod.GET)
    public
    @ResponseBody
    boolean checkForProduct(@PathVariable String productName) {
        LOGGER.info("Checking licence for product {}", productName);
        return licenceCheckingService.checkValidForProduct(productName);
    }
}
