/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.accounts.mass;

import org.apache.commons.lang.RandomStringUtils;
import org.jgrades.admin.api.model.PasswordGenerationStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultPasswordGenerationStrategy implements PasswordGenerationStrategy {
    @Value("${admin.password.generated.length}")
    private int passwordLength;

    @Value("${admin.password.generated.contains.letters}")
    private boolean containsLetters;

    @Value("${admin.password.generated.contains.numbers}")
    private boolean containsNumbers;

    @Override
    public String getPassword() {
        return RandomStringUtils.random(passwordLength, containsLetters, containsNumbers);
    }
}
