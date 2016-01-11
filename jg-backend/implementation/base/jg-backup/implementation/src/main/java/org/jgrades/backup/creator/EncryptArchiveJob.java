/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.backup.creator;

import org.apache.commons.io.FileUtils;
import org.jgrades.backup.api.dao.BackupEventRepository;
import org.jgrades.backup.api.entities.Backup;
import org.jgrades.backup.api.entities.BackupEvent;
import org.jgrades.backup.api.model.BackupEventSeverity;
import org.jgrades.backup.api.model.BackupEventType;
import org.jgrades.backup.api.model.BackupOperation;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.security.utils.EncryptionProvider;
import org.jgrades.security.utils.SignatureProvider;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@DisallowConcurrentExecution
public class EncryptArchiveJob implements Job {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(EncryptArchiveJob.class);

    @Value("${backup.internal.files.directory}")
    private String internalFilesDirectory;

    @Value("${backup.logs.files.directory}")
    private String logsFilesDirectory;

    @Autowired
    private EncryptionProvider encryptionProvider;

    @Autowired
    private SignatureProvider signatureProvider;

    @Autowired
    private BackupEventRepository backupEventRepository;

    private Backup backup;

    private SchedulerContext schedulerContext;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        init(context);
        BackupEvent event = getNewEvent("Backup encryption of internal files");
        try {
            byte[] encryptBytes = encryptInternalZip();
            File encryptedInternalZip = saveEncryptedZip(encryptBytes);
            saveSignatureOfZip(encryptedInternalZip);
            updateEventType(event, BackupEventType.FINISHED);
        } catch (Exception e) {
            LOGGER.error("Error during creating Backup of internal files. Process will be continued", e);
            schedulerContext.put("backupWarningFlag", true);
            setWarnDetails(event);
        }
    }

    private void init(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        try {
            schedulerContext = context.getScheduler().getContext();
            backup = (Backup) schedulerContext.get("backup");
        } catch (SchedulerException e) {
            LOGGER.error("Error during initialize EncryptArchiveJob. Process stopped", e);
            throw new JobExecutionException(e);
        }
    }


    private byte[] encryptInternalZip() throws IOException {
        File internalZip = new File(internalFilesDirectory + File.separator + "internal.bak.tmp");
        byte[] encryptBytes = encryptionProvider.encrypt(FileUtils.readFileToByteArray(internalZip));
        FileUtils.deleteQuietly(internalZip);
        return encryptBytes;
    }

    private File saveEncryptedZip(byte[] encryptBytes) throws IOException {
        String encryptedZipName = backup.getPath() + File.separator + "jg-internal-" + backup.getName() + ".bak";
        File encryptInternalZip = new File(encryptedZipName);
        FileUtils.writeByteArrayToFile(encryptInternalZip, encryptBytes);
        return encryptInternalZip;
    }

    private void saveSignatureOfZip(File encryptInternalZip) throws IOException {
        byte[] signature = signatureProvider.sign(FileUtils.readFileToByteArray(encryptInternalZip));
        File encryptInternalZipSignature = new File(encryptInternalZip.getAbsolutePath() + ".sign");
        FileUtils.writeByteArrayToFile(encryptInternalZipSignature, signature);
    }

    private BackupEvent getNewEvent(String message) {
        BackupEvent event = new BackupEvent();
        event.setEventType(BackupEventType.ONGOING);
        event.setSeverity(BackupEventSeverity.INFO);
        event.setOperation(BackupOperation.BACKUPING);
        event.setStartTime(LocalDateTime.now());
        event.setBackup(backup);
        event.setMessage(message);
        backupEventRepository.save(event);
        return event;
    }

    private void updateEventType(BackupEvent event, BackupEventType backupEventType) {
        event.setEventType(backupEventType);
        event.setEndTime(LocalDateTime.now());
        backupEventRepository.save(event);
    }

    private void setWarnDetails(BackupEvent event) {
        event.setEventType(BackupEventType.FINISHED);
        event.setSeverity(BackupEventSeverity.WARNING);
        event.setEndTime(LocalDateTime.now());
        backupEventRepository.save(event);
    }
}
