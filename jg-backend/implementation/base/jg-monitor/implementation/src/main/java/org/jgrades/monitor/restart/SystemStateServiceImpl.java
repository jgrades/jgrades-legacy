/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.monitor.restart;

import org.apache.commons.io.IOUtils;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.service.SystemStateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

@Service
public class SystemStateServiceImpl implements SystemStateService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(SystemStateServiceImpl.class);

    @Value("${monitor.tomcat.manager.path}")
    private String managerPath;

    @Value("${monitor.app.context.path}")
    private String contextPath;

    @Value("${monitor.tomcat.master.script.user}")
    private String masterScriptUser;

    @Value("${monitor.tomcat.master.script.password}")
    private String masterScriptPwd;

    @Value("${monitor.restart.timeout.miliseconds}")
    private Integer restartTimeout;

    @Override
    public String restartApplication() {
        try {
            URL url = new URL(buildAddress());
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(restartTimeout);
            setAuthProperty(conn);
            return IOUtils.toString(conn.getInputStream());
        } catch (IOException e) {
            LOGGER.debug("Problem during restarting tomcat", e);
            return e.getMessage();
        }
    }

    private String buildAddress() {
        return managerPath + "/text/reload?path=" + contextPath;
    }

    private void setAuthProperty(URLConnection connection) {
        if (masterScriptUser != null && masterScriptPwd != null) {
            String user_pass = masterScriptUser + ":" + masterScriptPwd;
            String encoded = Base64.getEncoder().encodeToString(user_pass.getBytes());
            connection.setRequestProperty("Authorization", "Basic " + encoded);
        }
    }
}
