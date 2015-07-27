package org.jgrades.lic.service;

import com.google.common.collect.Lists;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.FileUtils;
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

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
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
        Licence licence = null;
        try {
            licence = licenceDecryptionService.decrypt(keystorePath, secDataPath, licencePath);
            boolean isValid = licenceDecryptionService.validSignature(keystorePath, secDataPath, licencePath, signaturePath);

            if (isValid) {
                LicenceEntity licenceEntity = mapper.map(licence, LicenceEntity.class);
                licenceEntity.setLicenceFilePath(licencePath);
                licenceEntity.setSignatureFilePath(signaturePath);
                licenceRepository.save(licenceEntity);
            } else {
                throw new SignatureException();
            }
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | SignatureException e) {
            throw new UnreliableLicenceException(e);
        }
        return licence;
    }

    @Override
    public void uninstallLicence(Licence licence) {
        LicenceEntity licenceEntity = licenceRepository.findOne(licence.getUid());
        if (Optional.ofNullable(licenceEntity).isPresent()) {
            FileUtils.deleteQuietly(new File(licenceEntity.getLicenceFilePath()));
            FileUtils.deleteQuietly(new File(licenceEntity.getSignatureFilePath()));
            licenceRepository.delete(licenceEntity);
        } else {
            throw new LicenceNotFoundException("Licence with UID: " + licence.getUid() + " is not found in system");
        }
    }

    @Override
    public Licence get(Long uid) {
        return mapper.map(licenceRepository.findOne(uid), Licence.class);
    }

    @Override
    public List<Licence> getAll() {
        List<Licence> licences = Lists.newArrayList();
        List<LicenceEntity> entitiesList = IteratorUtils.toList(licenceRepository.findAll().iterator());
        for (LicenceEntity entity : entitiesList) {
            licences.add(mapper.map(entity, Licence.class));
        }
        return licences;
    }
}
