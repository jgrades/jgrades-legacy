package org.jgrades.lic.service;

import com.google.common.collect.ImmutableList;
import org.dozer.Mapper;
import org.jgrades.lic.api.exception.LicenceException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.dao.LicenceRepository;
import org.jgrades.lic.entities.LicenceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenceCheckingServiceImpl implements LicenceCheckingService {
    @Autowired
    private VersionRule versionRule;

    private final List<ValidationRule> rules = ImmutableList.of(new DateRule(), new MacRule(), versionRule);

    @Autowired
    private LicenceRepository licenceRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public boolean checkValid(Licence licence) throws LicenceException {
        for (ValidationRule rule : rules) {
            if (!rule.isValid(licence)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkValidForProduct(String productName) throws LicenceException {
        List<LicenceEntity> licences = licenceRepository.findByProductName(productName);

        for (LicenceEntity licenceEntity : licences) {
            Licence licence = mapper.map(licenceEntity, Licence.class);
            if (checkValid(licence)) {
                return true;
            }
        }

        return false;
    }
}
