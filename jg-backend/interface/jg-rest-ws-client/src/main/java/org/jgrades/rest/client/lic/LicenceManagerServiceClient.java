/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.lic;

import org.apache.commons.io.FileUtils;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.rest.api.lic.ILicenceManagerService;
import org.jgrades.rest.client.CoreRestClient;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class LicenceManagerServiceClient extends CoreRestClient
        implements ILicenceManagerService {
    private String tempDir;

    @Autowired
    public LicenceManagerServiceClient(@Value("${rest.client.temp.dir}") String tempDir,
                                       @Value("${rest.backend.base.url}") String backendBaseUrl,
                                       StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, restTemplate);
        this.tempDir = tempDir;
    }

    @Override
    public List<Licence> getAll() {
        String serviceUrl = backendBaseUrl + "/licence";
        ResponseEntity<List<Licence>> response = restTemplate.exchange(serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Licence>>() {
                });
        return response.getBody();
    }

    @Override
    public Licence get(Long uid) {
        String serviceUrl = backendBaseUrl + "/licence/" + uid;
        ResponseEntity<Licence> response = restTemplate.exchange(serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, Licence.class);
        return response.getBody();
    }

    @Override
    public Licence uploadAndInstall(MultipartFile licence, MultipartFile signature) throws IOException {
        Resource licenceResource = getResource(licence);
        Resource signatureResource = getResource(signature);
        try {
            String serviceUrl = backendBaseUrl + "/licence";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("licence", licenceResource);
            params.add("signature", signatureResource);

            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);

            ResponseEntity<Licence> response = restTemplate.exchange(serviceUrl, HttpMethod.POST,
                    requestEntity, Licence.class);
            return response.getBody();
        } finally {
            FileUtils.deleteQuietly(licenceResource.getFile());
            FileUtils.deleteQuietly(signatureResource.getFile());
        }
    }

    private FileSystemResource getResource(MultipartFile multipartFile) throws IOException {
        String resourcePath = tempDir + "/" + multipartFile.getName();
        File file = new File(resourcePath);
        FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
        return new FileSystemResource(file.getAbsolutePath());
    }

    @Override
    public void uninstall(Long uid) {
        String serviceUrl = backendBaseUrl + "/licence/" + uid;
        restTemplate.exchange(serviceUrl, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
    }
}
