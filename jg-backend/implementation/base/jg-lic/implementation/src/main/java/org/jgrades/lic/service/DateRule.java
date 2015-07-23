package org.jgrades.lic.service;

import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;
import org.jgrades.lic.api.model.Product;

import java.util.List;
import java.util.Optional;

class DateRule implements ValidationRule {
    private static final String EXPIRED_DAYS_PROPERTY_NAME = "expiredDays";

    @Override
    public boolean isValid(Licence licence) {
        Product product = licence.getProduct();
        boolean startDateIsBeforeNow = product.getValidFrom().isBeforeNow();
        boolean endDateIsAfterNow = product.getValidTo().isAfterNow();
        boolean isExpiredDaysModeActive = checkExpiredDaysMode(licence);
        return (startDateIsBeforeNow && endDateIsAfterNow) || isExpiredDaysModeActive;
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
            int days = Integer.parseInt(property.getValue());
            if (licence.getProduct().getValidTo().plusDays(days).isAfterNow()) {
                return true;
            }
        }

        return false;
    }
}
