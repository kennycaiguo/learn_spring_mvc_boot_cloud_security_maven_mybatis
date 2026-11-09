package org.kenny.bank.common.util;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class Argon2PasswordUtil {

    // Argon2 参数配置（可根据需求调整）
    private static final int ITERATIONS = 3;          // 迭代次数
    private static final int MEMORY_COST = 65536;     // 内存成本（64MB）
    private static final int PARALLELISM = 4;         // 并行度
    private static final int SALT_LENGTH = 16;        // 盐值长度（16字节）
    private static final int HASH_LENGTH = 32;        // 哈希输出长度（32字节）

    /**
     * 生成 Argon2 哈希密码（含盐值）
     */
    public static String hashPassword(String password) {
        // 1. 生成随机盐值
        byte[] salt = generateSalt();

        // 2. 配置 Argon2 参数
        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withSalt(salt)
                .withIterations(ITERATIONS)
                .withMemoryAsKB(MEMORY_COST)
                .withParallelism(PARALLELISM);

        // 3. 生成哈希
        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(builder.build());
        byte[] hash = new byte[HASH_LENGTH];
        generator.generateBytes(password.getBytes(StandardCharsets.UTF_8), hash);

        // 4. 返回格式：算法$版本$参数$盐$哈希（Base64编码）
        return "argon2id" +
                "$v=19" +
                "$m=" + MEMORY_COST + ",t=" + ITERATIONS + ",p=" + PARALLELISM +
                "$" + Base64.getEncoder().encodeToString(salt) +
                "$" + Base64.getEncoder().encodeToString(hash);
    }

    /**
     * 验证密码是否正确
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        // 1. 解析存储的哈希字符串
        String[] parts = hashedPassword.split("\\$");
        if (parts.length != 5 || !parts[0].equals("argon2id")) {
            throw new IllegalArgumentException("Invalid Argon2 hash format");
        }

        // 2. 提取参数
        byte[] salt = Base64.getDecoder().decode(parts[3]);
        byte[] storedHash = Base64.getDecoder().decode(parts[4]);

        // 3. 重新计算哈希
        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withSalt(salt)
                .withIterations(ITERATIONS)
                .withMemoryAsKB(MEMORY_COST)
                .withParallelism(PARALLELISM);

        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(builder.build());
        byte[] computedHash = new byte[storedHash.length];
        generator.generateBytes(password.getBytes(StandardCharsets.UTF_8), computedHash);

        // 4. 比较哈希值
        return constantTimeEquals(storedHash, computedHash);
    }

    /**
     * 生成随机盐值
     */
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * 安全比较字节数组（防止时序攻击）
     */
    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }

    public static void main(String[] args) {
        String password = "123456";
        String hashedPassword = hashPassword(password);
        System.out.println(hashedPassword);
        System.out.println(verifyPassword("123456", hashedPassword));
    }
}
