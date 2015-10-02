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
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;
import org.jgrades.lic.api.model.Product;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;

import java.util.List;
import java.util.Optional;

class DateRule implements ValidationRule {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(DateRule.class);
    private static final String EXPIRED_DAYS_PROPERTY_NAME = "expiredDays";

    @Override
    public boolean isValid(Licence licence) {
        LOGGER.debug("Start checking DateRule for licence with uid {}", licence.getUid());
        Product product = licence.getProduct();
        boolean startDateIsBeforeNow = product.getValidFrom().isBeforeNow();
        boolean endDateIsAfterNow = product.getValidTo().isAfterNow();
        LOGGER.debug("Is start date before now for licence with uid {}: {}", licence.getUid(), startDateIsBeforeNow);
        LOGGER.debug("Is end date after now for licence with uid {}: {}", licence.getUid(), endDateIsAfterNow);
        boolean isExpiredDaysModeActive = checkExpiredDaysMode(licence);
        return startDateIsBeforeNow && (endDateIsAfterNow || isExpiredDaysModeActive);
    }

    private boolean checkExpiredDaysMode(Licence licence) {
        List<LicenceProperty> properties = licence.getProperties();
        UnmodifiableIterator<LicenceProperty> expiredDaysProperty = Iterators.filter(properties.iterator(), licenceProperty -> {
            return licenceProperty.getName().equals(EXPIRED_DAYS_PROPERTY_NAME);
        });
        LicenceProperty property = null;
        if (expiredDaysProperty.hasNext()) {
            property = expiredDaysProperty.next();
        }

        if (Optional.ofNullable(property).isPresent()) {
            LOGGER.debug("ExpiredDays property found for licence with uid {}", licence.getUid());
            int days = Integer.parseInt(property.getValue());
            if (licence.getProduct().getValidTo().plusDays(days).isAfterNow()) {
                LOGGER.debug("Licence with uid {} is in expiredDays period ({} extra days)", licence.getUid(), days);
                return true;
            }
        } else {
            LOGGER.debug("ExpiredDays property not found for licence with uid {}", licence.getUid());
        }
        return false;
    }
}
