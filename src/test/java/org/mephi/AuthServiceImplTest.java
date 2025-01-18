package org.mephi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mephi.AuthServiceImpl.ERROR_EMPTY_PASSWORD;
import static org.mephi.AuthServiceImpl.USER_NOT_NULL_MESSAGE;

class AuthServiceImplTest {

    public static final String TEST_USER = "testUser";
    public static final String SECURE_PASSWORD = "securePassword123";
    public static final String WRONG_PASSWORD = "wrongPassword";
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl();
    }

    @Test
    void checkPassword_ShouldReturnTrue_WhenPasswordMatches() {
        // Arrange
        User user = new User(TEST_USER, SECURE_PASSWORD);
        String providedPassword = SECURE_PASSWORD;
        // Act
        boolean result = authService.checkPassword(user, providedPassword);

        // Assert
        assertTrue(result, "Пароль должен совпадать и возвращать значение true");
    }

    @Test
    void checkPassword_ShouldReturnFalse_WhenPasswordDoesNotMatch() {
        // Arrange
        User user = new User(TEST_USER, SECURE_PASSWORD);
        String providedPassword = WRONG_PASSWORD;
        // Act
        boolean result = authService.checkPassword(user, providedPassword);

        // Assert
        assertFalse(result, "При несоответствии пароля должно быть возвращено значение false");
    }

    @Test
    void checkPassword_ShouldThrowException_WhenUserIsNull() {
        // Arrange
        User user = null;
        String providedPassword = SECURE_PASSWORD;

        // Assert
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> {
                    // Act
                    authService.checkPassword(user, providedPassword);
                });

        assertEquals(USER_NOT_NULL_MESSAGE, exception.getMessage());
    }

    @Test
    void checkPassword_ShouldThrowException_WhenProvidedPasswordIsNull() {
        // Arrange
        User user = new User(TEST_USER, SECURE_PASSWORD);
        user.setPassword("securePassword123");
        String providedPassword = null;

        // Assert
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> {
            // Act
            authService.checkPassword(user, providedPassword);
        });

        assertEquals(ERROR_EMPTY_PASSWORD, exception.getMessage());
    }

    @Test
    void checkPassword_ShouldReturnFalse_WhenStoredPasswordIsNull() {
        // Arrange
        User user = new User(TEST_USER, null);
        String providedPassword = "securePassword123";

        // Act
        boolean result = authService.checkPassword(user, providedPassword);

        // Assert
        assertFalse(result, "Если сохраненный пароль равен null, он должен вернуть значение false");
    }

}