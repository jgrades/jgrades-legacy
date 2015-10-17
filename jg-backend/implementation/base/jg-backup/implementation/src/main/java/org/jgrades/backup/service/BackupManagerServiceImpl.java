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

import com.google.common.collect.Lists;
import org.jgrades.backup.api.dao.BackupRepository;
import org.jgrades.backup.api.entities.Backup;
import org.jgrades.backup.api.entities.BackupEvent;
import org.jgrades.backup.api.model.RestoreSettings;
import org.jgrades.backup.api.service.BackupManagerService;
import org.jgrades.backup.creator.BackupDispatcher;
import org.jgrades.backup.manager.DirectoryRefreshRunner;
import org.jgrades.backup.restore.RestoringPerformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackupManagerServiceImpl implements BackupManagerService {
    @Autowired
    private BackupRepository repository;

    @Autowired
    private BackupDispatcher dispatcher;

    @Autowired
    private RestoringPerformer restoringPerformer;

    @Autowired
    private DirectoryRefreshRunner directoryRefreshRunner;

    @Override
    public void makeNow() {
        dispatcher.reqestNew();
    }

    @Override
    public Backup getWithId(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<Backup> getAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public void refreshBackupDirectory() {
        directoryRefreshRunner.refreshAsync();
    }

    @Override
    public void restore(Backup backup, RestoreSettings restoreSettings) {
        restoringPerformer.restore(backup, restoreSettings);
    }

    @Override
    public void delete(Backup backup) {
        repository.delete(backup);
    }

    @Override
    public void interruptMaking(Backup backup) {
        dispatcher.interrupt(backup);
    }

    @Override
    public List<BackupEvent> getEvents(Backup backup) {
        Backup persistBackup = repository.findOne(backup.getId());
        if (persistBackup != null) {
            return persistBackup.getEvents();
        }
        return Lists.newArrayList();
    }
}
