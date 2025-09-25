package br.edu.example.api.core.auth.model;

import br.edu.example.api.core.auth.exception.model.password.PasswordBytesOverflowException;
import br.edu.example.api.core.auth.exception.model.password.PasswordInvalidCharacterException;
import br.edu.example.api.core.auth.exception.model.password.PasswordInvalidLengthException;
import br.edu.example.api.core.auth.exception.model.password.PasswordNullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {
    @Test
    void shouldCreateValidPassword() {
        String validPassword = "password123";
        Password password = Password.valueOf(validPassword);
        assertEquals(validPassword, password.getValue());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        assertThrows(PasswordNullException.class, () -> Password.valueOf(null));
    }

    @Test
    void shouldThrowExceptionWhenPasswordTooShort() {
        assertThrows(PasswordInvalidLengthException.class, () -> Password.valueOf("ABC12"));
    }

    @Test
    void shouldThrowExceptionWhenPasswordHasInvalidCharacters() {
        assertThrows(PasswordInvalidCharacterException.class, () -> Password.valueOf("testeISO\n"));
        assertThrows(PasswordInvalidCharacterException.class, () -> Password.valueOf("Teste\0ISO"));
    }

    @Test
    void shouldThrowExceptionWhenPasswordExceedsMaxBytes() {
        String longPassword = "é".repeat(Password.MAX_BYTES / 2 + 1) + "abcdef";
        assertThrows(PasswordBytesOverflowException.class, () -> Password.valueOf(longPassword));
    }

    @Test
    void shouldNotValidateWhenUsingUnsafe() {
        assertDoesNotThrow(() -> Password.valueOfUnsafe(null));
        assertDoesNotThrow(() -> Password.valueOfUnsafe("abc"));
        assertDoesNotThrow(() -> Password.valueOfUnsafe("á".repeat(100)));
    }
}
