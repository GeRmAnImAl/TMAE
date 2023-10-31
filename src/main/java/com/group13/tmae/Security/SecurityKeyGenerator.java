package com.group13.tmae.Security;

import java.security.SecureRandom;
import java.util.Base64;

public class SecurityKeyGenerator {
    private final SecureRandom random;
    private final byte[] bytes;

    public SecurityKeyGenerator() {
        this.random = new SecureRandom();
        this.bytes = new byte[64];
    }

    public String generateSecureKey(){
        random.nextBytes(this.bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}

