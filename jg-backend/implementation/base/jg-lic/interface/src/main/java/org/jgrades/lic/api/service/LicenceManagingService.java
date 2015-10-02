/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.api.service;

import org.jgrades.lic.api.exception.LicenceException;
import org.jgrades.lic.api.model.Licence;

import java.util.List;

public interface LicenceManagingService {
    Licence installLicence(String licencePath, String signaturePath) throws LicenceException;

    void uninstallLicence(Licence licence);

    Licence get(Long uid);

    List<Licence> getAll();
}
