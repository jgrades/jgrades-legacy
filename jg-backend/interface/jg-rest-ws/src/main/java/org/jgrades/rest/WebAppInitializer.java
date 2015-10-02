/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest;

import org.jgrades.admin.config.AdminConfig;
import org.jgrades.data.config.DataConfig;
import org.jgrades.lic.config.LicConfig;
import org.jgrades.monitor.config.MonitorConfig;
import org.jgrades.property.ApplicationPropertiesConfig;
import org.jgrades.security.config.SecurityConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;

public class WebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationPropertiesConfig.class, MonitorConfig.class, LicConfig.class, DataConfig.class, SecurityConfig.class, AdminConfig.class);
        rootContext.refresh();

        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.setInitParameter("defaultHtmlEscape", "true");

        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
        mvcContext.register(ApplicationPropertiesConfig.class, RestConfig.class);

        ServletRegistration.Dynamic appServlet = servletContext.addServlet("jgRestServlet", new DispatcherServlet(mvcContext));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
        appServlet.setMultipartConfig(new MultipartConfigElement(mvcContext.getEnvironment().getProperty("rest.lic.path"), 1024 * 1024 * 5, 1024 * 1024 * 5 * 5, 1024 * 1024));

        FilterRegistration.Dynamic springSecurityFilterChain = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
        springSecurityFilterChain.addMappingForUrlPatterns(null, false, "/*");
    }

}
