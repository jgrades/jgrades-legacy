package org.jgrades.lic.service;

import org.apache.commons.collections.IteratorUtils;
import org.dozer.Mapper;
import org.jgrades.lic.api.crypto.decrypt.LicenceDecryptionService;
import org.jgrades.lic.api.exception.LicenceException;
import org.jgrades.lic.api.exception.LicenceNotFoundException;
import org.jgrades.lic.api.exception.UnreliableLicenceException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.jgrades.lic.dao.LicenceRepository;
import org.jgrades.lic.entities.LicenceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class LicenceManagingServiceImpl implements LicenceManagingService {
    @Value("${lic.keystore.path}")
    private String keystorePath;

    @Value("${lic.sec.data.path}")
    private String secDataPath;

    @Autowired
    private LicenceRepository licenceRepository;

    @Autowired
    private LicenceDecryptionService licenceDecryptionService;

    @Autowired
    private Mapper mapper;

    @Override
    public Licence installLicence(String licencePath, String signaturePath) throws LicenceException {
        try {
            Licence licence = licenceDecryptionService.decrypt(keystorePath, secDataPath, licencePath);
            boolean isValid = licenceDecryptionService.validSignature(keystorePath, secDataPath, licencePath, signaturePath);
            if(isValid){
                licenceRepository.save(mapper.map(licence, LicenceEntity.class));
            } else{
                //throw new UnreliableLicenceException();
            }
        } catch (IOException e) {
            throw new LicenceException(e);
        }
        return null;
    }

    @Override
    public void uninstallLicence(Licence licence) {
        LicenceEntity licenceEntity = licenceRepository.findOne(licence.getUid());
        if(Optional.ofNullable(licenceEntity).isPresent()){
            licenceRepository.delete(licenceEntity);
        } else {
           // throw new LicenceNotFoundException();
        }
    }

    @Override
    public List<Licence> getAll() {
        return IteratorUtils.toList(licenceRepository.findAll().iterator());
    }
}
