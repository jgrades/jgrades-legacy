/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.backup.api.service;

import org.jgrades.backup.api.entities.Backup;
import org.jgrades.backup.api.entities.BackupEvent;
import org.jgrades.backup.api.model.RestoreSettings;

import java.util.List;

public interface BackupManagerService {
    void makeNow();

    Backup getAll();

    void refreshBackupDirectory();

    void restore(Backup backup, RestoreSettings restoreSettings);

    void delete(Backup backup);

    void interruptMaking(Backup backup);

    List<BackupEvent> getEvents(Backup backup);
}
