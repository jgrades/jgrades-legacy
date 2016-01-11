/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.utils;

public final class CryptoDataConstants {
    public static final String KEYSTORE_TYPE = "JCEKS";

    public static final String ENCRYPTION_KEY_ALIAS = "jg-crypto";
    public static final String SIGNATURE_KEY_ALIAS = "jg-signature";

    public static final String CIPHER_PROVIDER_INTERFACE = "AES/ECB/PKCS5Padding";
    public static final String SIGNATURE_PROVIDER_INTERFACE = "SHA256withRSA";

    public static final String SIGNATURE_FILE_EXTENSION = ".sign";

    private CryptoDataConstants() {
    }
}
