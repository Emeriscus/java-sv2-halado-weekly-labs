package week01.webshop.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static week01.webshop.utils.UserValidator.*;

class UserValidatorTest {

    @Test
    void isValidEmailTestWithValidEmailTest() {
        assertTrue(isValidEmail("emeriscus@domain.co.in"));
        assertTrue(isValidEmail("emeriscus@domain.com"));
        assertTrue(isValidEmail("emeriscus.name@domain.com"));
    }

    @Test
    void isValidEmailTestWithInvalidEmailTest() {
        assertFalse(isValidEmail(".emeriscus@google.com"));
        assertFalse(isValidEmail("emeriscus@google.com."));
        assertFalse(isValidEmail("emeriscus@google..com"));
        assertFalse(isValidEmail("emeriscus@google.c"));
        assertFalse(isValidEmail("emeriscus@google.corporate"));
        assertFalse(isValidEmail("emer'iscus@domain.com"));
        assertFalse(isValidEmail(" emeriscus@domain.com"));
        assertFalse(isValidEmail("emeriscus @domain.com"));
    }

    @Test
    void isValidPasswordWithValidPasswordTest() {
        assertTrue(isValidPassword("sdDee@g9"));
    }

    @Test
    void isValidPasswordWithInvalidPasswordTest() {
        assertFalse(isValidPassword(null));
        assertFalse(isValidPassword("sdDe.g9"));
        assertFalse(isValidPassword("assdsfdfgfgd"));
        assertFalse(isValidPassword("asf9dgdsgsdg"));
        assertFalse(isValidPassword("sdg9Dthrthhh"));
        assertFalse(isValidPassword("DDDDDDDDDDDD"));
        assertFalse(isValidPassword("DDDD9ZEETRTT"));
        assertFalse(isValidPassword("ERETT9.FFFGH"));
        assertFalse(isValidPassword(".?.?.?.?.?.?."));
        assertFalse(isValidPassword(""));
    }

    @Test
    void validateUserWithValidUserTest() {
        assertTrue(validateUser("test@test.hu", "dffbxfbDDD.77"));
    }

    @Test
    void validateUserWithInvalidUserTest() {
        assertFalse(validateUser("test@test.hu", "dffb"));
        assertFalse(validateUser("test.test.hu", "dffbxfbDDD.77"));
        assertFalse(validateUser("test.test.hu", "dff"));
    }
}