package com.group13.tmae.Security;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for generating secure random keys, which can be used in various parts of the security
 * configuration, such as "remember me" functionality. It leverages {@link SecureRandom} to ensure
 * high-entropy random keys.
 */
public class SecurityKeyGenerator {

    /**
     * Instance of {@link SecureRandom} for generating high-entropy random values.
     */
    private final SecureRandom random;

    /**
     * Byte array to store random values that will be used to generate a secure key.
     */
    private final byte[] bytes;

    /**
     * Constructor that initializes the {@link SecureRandom} instance and the byte array.
     */
    public SecurityKeyGenerator() {
        this.random = new SecureRandom();
        this.bytes = new byte[64];
    }

    /**
     * Generates a base64 encoded secure random key.
     * This key can be used for security tokens or "remember me" functionality.
     *
     * @return A base64 encoded string representing a secure random key.
     */
    public String generateSecureKey(){
        random.nextBytes(this.bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}

