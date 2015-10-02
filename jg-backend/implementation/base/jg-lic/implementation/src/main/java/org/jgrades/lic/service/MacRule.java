/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.service;

import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.lic.api.exception.ViolationOfLicenceConditionException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class MacRule implements ValidationRule {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(MacRule.class);

    private static final String MAC_PROPERTY_NAME = "mac";

    private List<NetworkInterface> networkInterfaces;

    public MacRule() {
    }

    public MacRule(List<NetworkInterface> networkInterfaces) {
        this.networkInterfaces = networkInterfaces;
    }

    @Override
    public boolean isValid(Licence licence) {
        LOGGER.debug("Start checking MacRule for licence with uid {}", licence.getUid());
        List<LicenceProperty> properties = licence.getProperties();
        UnmodifiableIterator<LicenceProperty> macPropertyIterator = Iterators.filter(
                properties.iterator(), licenceProperty -> {
                    return licenceProperty.getName().equals(MAC_PROPERTY_NAME);
                });
        LicenceProperty property = null;
        if (macPropertyIterator.hasNext()) {
            property = macPropertyIterator.next();
        }

        if (Optional.ofNullable(property).isPresent()) {
            LOGGER.debug("Mac property found for licence with uid {}", licence.getUid());
            try {
                prepareNetworkInterfacesIfNeeded();
            } catch (SocketException e) {
                LOGGER.error("Error during preparing network interfaces", e);
                throw new ViolationOfLicenceConditionException(e);
            }
            String requestedMac = property.getValue();
            LOGGER.debug("Requested MAC: {}", requestedMac);
            for (NetworkInterface networkInterface : networkInterfaces) {
                try {
                    String currentMac = getCurrentMac(networkInterface);
                    LOGGER.trace("Proceeded network interface: {}, with MAC: {}", networkInterface.getDisplayName(), currentMac);
                    if (currentMac.equalsIgnoreCase(requestedMac)) {
                        LOGGER.debug("MAC {} is matching to expected MAC", currentMac);
                        return true;
                    }
                } catch (SocketException e) {
                    LOGGER.debug("Error during extracting MAC for network interface {}", networkInterface.getDisplayName());
                }
            }
            LOGGER.debug("There is no any network interfaces or all has not correctly MAC");
            return false;
        } else {
            LOGGER.debug("Mac property not found for licence with uid {}", licence.getUid());
        }
        return true;
    }

    private void prepareNetworkInterfacesIfNeeded() throws SocketException {
        if (networkInterfaces == null) {
            networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            LOGGER.trace("Network interfaces in system: {}", networkInterfaces);
        }
    }

    protected String getCurrentMac(NetworkInterface networkInterface) throws SocketException {
        byte[] macBytes = networkInterface.getHardwareAddress();
        if (macBytes == null) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < macBytes.length; i++) {
            sb.append(String.format("%02X%s", macBytes[i], (i < macBytes.length - 1) ? ":" : StringUtils.EMPTY));
        }
        return sb.toString();
    }
}
