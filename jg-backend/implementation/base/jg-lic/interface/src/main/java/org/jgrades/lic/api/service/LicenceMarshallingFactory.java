/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.api.service;

import org.jgrades.lic.api.model.Licence;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

public final class LicenceMarshallingFactory {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(LicenceMarshallingFactory.class);

    private static JAXBContext jaxbContext;

    static {
        try {
            jaxbContext = JAXBContext.newInstance(Licence.class);
        } catch (JAXBException e) {
            LOGGER.error("Preparing LicenceMarshallingFactory failed", e);
        }
    }

    private LicenceMarshallingFactory() {
    }

    public static Marshaller getMarshaller() {
        Marshaller jaxbMarshaller = null;
        try {
            jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            LOGGER.error("Preparing Licence marshaller failed", e);
        }
        return jaxbMarshaller;
    }

    public static Unmarshaller getUnmarshaller() {
        Unmarshaller jaxbUnmarshaller = null;
        try {
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbUnmarshaller.setEventHandler(new DefaultValidationEventHandler());
        } catch (JAXBException e) {
            LOGGER.error("Preparing Licence unmarshaller failed", e);
        }
        return jaxbUnmarshaller;
    }
}
