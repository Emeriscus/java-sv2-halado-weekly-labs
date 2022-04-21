package week01.webshop.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static week01.webshop.utils.PasswordGenerator.*;

class PasswordGeneratorTest {


    @Test
    void generateAndVerifySecurePasswordTest() {

        String password = "aaiawsduhSDF@.4535";
        String salt = generateSalt();
        String storedSecurePassword = generateSecurePassword(password, salt);

        String providedPassword = "aaiawsduhSDF@.4535";
        String newSecurePassword = generateSecurePassword(providedPassword, salt);

        assertEquals(newSecurePassword, storedSecurePassword);
        assertTrue(verifyUserPassword(providedPassword, storedSecurePassword, salt));
    }
}