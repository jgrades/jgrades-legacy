/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.backup.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.jgrades.backup.api.model.BackupEventSeverity;
import org.jgrades.backup.api.model.BackupEventType;
import org.jgrades.backup.api.model.BackupOperation;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JG_BACKUP_BACKUP_EVENT")
@Getter
@Setter
public class BackupEvent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BackupEventType eventType;

    @Enumerated(EnumType.STRING)
    private BackupEventSeverity severity;

    @Enumerated(EnumType.STRING)
    private BackupOperation operation;

    @Column
    @Type(type = CustomType.JODA_DATE_TIME)
    private DateTime startTime;

    @Column
    @Type(type = CustomType.JODA_DATE_TIME)
    private DateTime endTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BACKUP_ID", nullable = false)
    private Backup backup;

    private String message;
}
