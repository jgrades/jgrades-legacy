package org.jgrades.lic.app.utils;

public final class LicConstants {
    public static final String KEYSTORE_TYPE = "JCEKS";

    public static final String ENCRYPTION_KEY_ALIAS = "jg-crypto";
    public static final String SIGNATURE_KEY_ALIAS = "jg-signature";

    public static final String CIPHER_PROVIDER_INTERFACE = "AES/ECB/PKCS5Padding";
    public static final String SIGNATURE_PROVIDER_INTERFACE = "SHA256withRSA";

    public static final String SIGNATURE_FILE_EXTENSION = ".sign";

    private LicConstants() {
    }
}
