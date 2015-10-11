/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResult {
    private boolean success;
    private String errorType;
    private String errorMsg;

    public LoginResult() {
        success = true;
    }
}
