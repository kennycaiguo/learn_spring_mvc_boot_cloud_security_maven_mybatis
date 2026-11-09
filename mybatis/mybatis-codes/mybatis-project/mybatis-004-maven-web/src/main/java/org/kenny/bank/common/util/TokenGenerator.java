package org.kenny.bank.common.util;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    public static String generateSecureToken() {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        random.nextBytes(tokenBytes); // 填充数据到tokenBytes
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    public static void main(String[] args) {
        System.out.println(generateSecureToken());
    }
}
