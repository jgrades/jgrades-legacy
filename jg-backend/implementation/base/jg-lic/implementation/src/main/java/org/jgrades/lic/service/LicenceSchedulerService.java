package org.jgrades.lic.service;

import org.dozer.Mapper;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.dao.LicenceRepository;
import org.jgrades.lic.entities.LicenceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LicenceSchedulerService {
    @Autowired
    private LicenceRepository licenceRepository;

    @Autowired
    private LicenceCheckingService checkingService;

    @Autowired
    private Mapper mapper;

    @Scheduled(cron = "${lic.checker.scheduler.cron}")
    public void monitorValidOfAllLicences() {
        //TODO log INFO
        Iterable<LicenceEntity> licences = licenceRepository.findAll();
        licences.forEach(licenceEntity -> {
            if (checkingService.checkValid(mapper.map(licenceEntity, Licence.class))) {
                //TODO log DEBUG
            } else {
                //TODO log ERROR
            }
        });
    }
}
