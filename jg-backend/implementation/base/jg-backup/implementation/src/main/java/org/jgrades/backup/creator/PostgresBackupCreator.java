/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.backup.creator;

import com.google.common.collect.Lists;
import org.jgrades.data.api.model.DataSourceDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class PostgresBackupCreator implements DatabaseBackupCreator {
    public static final String PGPASSWORD_ENV_KEY = "PGPASSWORD";

    @Override
    public void runDbBackup(DataSourceDetails dataSourceDetails, String dbDumpPath) throws IOException {
        List<String> commands = Lists.newArrayList("pg_dump", "-i", "-F", "t", "-b", "-v",
                "-h", dataSourceDetails.host(),
                "-p", dataSourceDetails.port(),
                "-U", dataSourceDetails.getUsername(),
                "-f", dbDumpPath,
                "-d", dataSourceDetails.databaseName());

        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        Map<String, String> environment = processBuilder.environment();
        environment.put(PGPASSWORD_ENV_KEY, dataSourceDetails.getPassword());

        processBuilder.redirectErrorStream(true);
        processBuilder.inheritIO();
        processBuilder.start();
    }
}
