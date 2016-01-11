/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.api.backup;

import org.jgrades.backup.api.entities.Backup;
import org.jgrades.backup.api.entities.BackupEvent;
import org.jgrades.backup.api.model.RestoreSettings;
import org.jgrades.rest.api.common.RestCrudPagingService;

import java.util.List;

public interface IBackupService extends RestCrudPagingService<Backup, Long> {
    void insertOrUpdate(Backup entity); //Deprecated

    void makeBackup();

    void refresh();

    void restore(Long id, RestoreSettings restoreSettings);

    void interrupt(Long id);

    List<BackupEvent> getEvents(Long id);
}
