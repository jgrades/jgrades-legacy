/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.api.lic;

import org.jgrades.lic.api.model.Licence;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ILicenceManagerService {
    List<Licence> getAll();

    Licence get(Long uid);

    Licence uploadAndInstall(MultipartFile licence, MultipartFile signature) throws IOException;

    void uninstall(Long uid);
}
