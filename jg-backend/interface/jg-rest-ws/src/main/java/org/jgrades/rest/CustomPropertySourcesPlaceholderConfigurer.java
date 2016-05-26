/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.StringValueResolver;
import org.springframework.web.context.support.StandardServletEnvironment;

import javax.annotation.PostConstruct;
import java.util.Iterator;


public class CustomPropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer implements SmartLifecycle {
    private PropertiesPersister propertiesPersister;
//    private Environment environment;
    private Resource[] locations;

    @Override
    public void setPropertiesPersister(PropertiesPersister propertiesPersister){
        this.propertiesPersister = propertiesPersister;
        super.setPropertiesPersister(propertiesPersister);
    }
//
//    public void setEnvironment(Environment environment){
//        this.environment = environment;
//        super.setEnvironment(environment);
//    }

    @Override
    public void setLocation(Resource location) {
        this.locations = new Resource[] {location};
        super.setLocation(location);
    }

    /**
     * Set locations of properties files to be loaded.
     * <p>Can point to classic properties files or to XML files
     * that follow JDK 1.5's properties XML format.
     * <p>Note: Properties defined in later files will override
     * properties defined earlier files, in case of overlapping keys.
     * Hence, make sure that the most specific files are the last
     * ones in the given list of locations.
     */
    public void setLocations(Resource... locations) {
        this.locations = locations;
        super.setLocations(locations);
    }

    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, final ConfigurablePropertyResolver propertyResolver) throws BeansException {
        propertyResolver.setPlaceholderPrefix(this.placeholderPrefix);
        propertyResolver.setPlaceholderSuffix(this.placeholderSuffix);
        propertyResolver.setValueSeparator(this.valueSeparator);
        StringValueResolver valueResolver = new StringValueResolver() {
            public String resolveStringValue(String strVal) {
                String resolved = CustomPropertySourcesPlaceholderConfigurer.this.ignoreUnresolvablePlaceholders?propertyResolver.resolvePlaceholders(strVal):propertyResolver.resolveRequiredPlaceholders(strVal);
                return resolved.equals(CustomPropertySourcesPlaceholderConfigurer.this.nullValue)?null:resolved;
            }
        };
        this.doProcessProperties(beanFactoryToProcess, valueResolver);
    }

    @PostConstruct
    public void fillMap() {
        org.springframework.core.env.PropertySources appliedPropertySources = getAppliedPropertySources();
        appliedPropertySources.forEach(propertySource -> System.out.println(propertySource.getSource().toString()));
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable runnable) {

    }

    @Override
    public void start() {
        org.springframework.core.env.PropertySources appliedPropertySources = getAppliedPropertySources();
        Iterator<PropertySource<?>> iterator = appliedPropertySources.iterator();
        while (iterator.hasNext()){
            PropertySource<?> ps = iterator.next();
            if(ps.getSource() instanceof StandardServletEnvironment){
                StandardServletEnvironment env = (StandardServletEnvironment) ps.getSource();
                for (PropertySource<?> propertySource : env.getPropertySources()) {
                    if(propertySource instanceof ResourcePropertySource){
                        ResourcePropertySource rps = (ResourcePropertySource) propertySource;
                        if(rps.getName().contains("URL")){
                            System.out.println(rps);
                        }
                    }
                }

            }
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return 0;
    }
}
