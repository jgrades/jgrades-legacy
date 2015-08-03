package org.jgrades.lic.service;

import org.dozer.Mapper;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.dao.LicenceRepository;
import org.jgrades.lic.entities.LicenceEntity;
import org.jgrades.logging.logger.JGLoggingFactory;
import org.jgrades.logging.logger.JGradesLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LicenceSchedulerService {
    private static final JGradesLogger LOGGER = JGLoggingFactory.getLogger(LicenceSchedulerService.class);

    @Autowired
    private LicenceRepository licenceRepository;

    @Autowired
    private LicenceCheckingService checkingService;

    @Autowired
    private Mapper mapper;

    @Scheduled(cron = "${lic.checker.scheduler.cron}")
    public void monitorValidOfAllLicences() {
        LOGGER.debug("Start scheduled validation of all licences in system");
        Iterable<LicenceEntity> licences = licenceRepository.findAll();
        licences.forEach(licenceEntity -> {
            if (checkingService.checkValid(mapper.map(licenceEntity, Licence.class))) {
                LOGGER.debug("Licence with uid {} for product {} is valid", licenceEntity.getUid(), licenceEntity.getProduct().getName());
            } else {
                LOGGER.error("Licence with uid {} for product {} is NOT valid", licenceEntity.getUid(), licenceEntity.getProduct().getName());
            }
        });
        LOGGER.debug("End of scheduled validation of all licences in system");
    }
}
