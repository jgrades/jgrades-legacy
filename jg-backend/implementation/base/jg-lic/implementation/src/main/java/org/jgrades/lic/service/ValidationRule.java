package org.jgrades.lic.service;

import org.jgrades.lic.api.model.Licence;

interface ValidationRule {
    boolean isValid(Licence licence);
}
