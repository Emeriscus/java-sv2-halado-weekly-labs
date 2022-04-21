package week01.webshop.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordGenerator {

    private static final Random RANDOM = new SecureRandom();
    private static final String CHARACTERS = "<>#&&@{};*đĐ[]łŁ$ß¤×÷€?:_+!%/=()§-,.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 131072;
    private static final int KEY_LENGTH = 512;
    private static final int SALT_LENGTH = 30;
    private static final String algorithm = "PBKDF2WithHmacSHA512";

    public static String generateSalt() {
        StringBuilder salt = new StringBuilder(SALT_LENGTH);
        for (int i = 0; i < SALT_LENGTH; i++) {
            salt.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return new String(salt);
    }

    private static byte[] generateHash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password!", e);
        } finally {
            spec.clearPassword();
        }
    }

    public static String generateSecurePassword(String password, String salt) {
        byte[] securePassword = generateHash(password.toCharArray(), salt.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
    }

    public static boolean verifyUserPassword(String providedPassword, String storedSecuredPassword, String storedSalt) {
        String newSecurePassword = generateSecurePassword(providedPassword, storedSalt);
        return newSecurePassword.equalsIgnoreCase(storedSecuredPassword);
    }
}