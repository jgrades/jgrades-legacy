package org.jgrades.lic.service;

import com.google.common.collect.ImmutableList;
import org.dozer.Mapper;
import org.jgrades.lic.api.exception.LicenceException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.dao.LicenceRepository;
import org.jgrades.lic.entities.LicenceEntity;
import org.jgrades.logging.logger.JGLoggingFactory;
import org.jgrades.logging.logger.JGradesLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class LicenceCheckingServiceImpl implements LicenceCheckingService {
    private static final JGradesLogger LOGGER = JGLoggingFactory.getLogger(LicenceCheckingServiceImpl.class);

    @Autowired
    private LicenceRepository licenceRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private VersionRule versionRule;

    private List<ValidationRule> rules;

    @PostConstruct
    public void initIt() throws Exception {
        rules = ImmutableList.of(new DateRule(), new MacRule(), versionRule);
    }

    @Override
    public boolean checkValid(Licence licence) throws LicenceException {
        LOGGER.debug("Start checking validation of licence {}", licence);
        for (ValidationRule rule : rules) {
            LOGGER.debug("Start checking rule of validation: {} for licence with uid {}", rule.getClass().getName(), licence.getUid());
            if (!rule.isValid(licence)) {
                LOGGER.debug("Licence {} is not pass validation process with rule: {}", licence.getUid(), rule.getClass().getName());
                return false;
            }
        }
        LOGGER.debug("Licence with uid {} is valid", licence.getUid());
        return true;
    }

    @Override
    public boolean checkValidForProduct(String productName) throws LicenceException {
        LOGGER.debug("Start checking licences for product {}", productName);

        List<LicenceEntity> licences = licenceRepository.findByProductName(productName);

        for (LicenceEntity licenceEntity : licences) {
            LOGGER.debug("Licence with uid {} is for product {}", licenceEntity.getUid(), productName);
            Licence licence = mapper.map(licenceEntity, Licence.class);
            if (checkValid(licence)) {
                LOGGER.debug("Valid licence for product {} found", productName);
                return true;
            }
        }
        LOGGER.debug("Licences for product {} not found or all are not valid", productName);
        return false;
    }
}
