/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.backup.service;

import org.jgrades.backup.api.entities.Backup;
import org.jgrades.backup.api.entities.BackupEvent;
import org.jgrades.backup.api.model.RestoreSettings;
import org.jgrades.backup.api.service.BackupManagerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackupManagerServiceImpl implements BackupManagerService {
    @Override
    public void makeNow() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Backup getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void refreshBackupDirectory() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void restore(Backup backup, RestoreSettings restoreSettings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Backup backup) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void interruptMaking(Backup backup) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<BackupEvent> getEvents(Backup backup) {
        throw new UnsupportedOperationException();
    }
}
