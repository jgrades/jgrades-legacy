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
import org.apache.commons.io.FileUtils;
import org.jgrades.backup.api.dao.BackupRepository;
import org.jgrades.backup.api.entities.Backup;
import org.jgrades.backup.api.entities.BackupEvent;
import org.jgrades.backup.api.model.RestoreSettings;
import org.jgrades.backup.api.service.BackupManagerService;
import org.jgrades.backup.creator.BackupDispatcher;
import org.jgrades.backup.manager.DirectoryRefreshRunner;
import org.jgrades.backup.restore.RestoringPerformer;
import org.jgrades.data.api.service.crud.AbstractPagingMgntService;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BackupManagerServiceImpl extends AbstractPagingMgntService<Backup, Long, BackupRepository>
        implements BackupManagerService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(BackupManagerServiceImpl.class);

    @Autowired
    private BackupDispatcher dispatcher;

    @Autowired
    private RestoringPerformer restoringPerformer;

    @Autowired
    private DirectoryRefreshRunner directoryRefreshRunner;

    @Autowired
    public BackupManagerServiceImpl(BackupRepository repository) {
        super(repository);
    }

    @Override
    public void makeNow() {
        dispatcher.reqestNew();
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
    public void remove(Backup backup) {
        removeBackupFromDisk(backup.getId());
        super.remove(backup);
    }

    @Override
    public void remove(List<Backup> backups) {
        for (Backup backup : backups) {
            removeBackupFromDisk(backup.getId());
        }
        super.remove(backups);
    }

    @Override
    public void removeId(Long id) {
        removeBackupFromDisk(id);
        super.removeId(id);
    }

    @Override
    @Transactional("mainTransactionManager")
    public void removeIds(List<Long> ids) {
        ids.forEach(this::removeBackupFromDisk);
        super.removeIds(ids);
    }

    private void removeBackupFromDisk(Long id) {
        Backup backup = repository.findOne(id);
        try {
            FileUtils.deleteDirectory(new File(backup.getPath()));
        } catch (IOException e) {
            LOGGER.warn("Unable to remove backup directory: {}", backup.getPath(), e);
        }
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
