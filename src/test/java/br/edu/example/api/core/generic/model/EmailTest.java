package br.edu.example.api.core.generic.model;

import br.edu.example.api.core.generic.exception.model.email.EmailInvalidFormatException;
import br.edu.example.api.core.generic.exception.model.email.EmailInvalidLengthException;
import br.edu.example.api.core.generic.exception.model.email.EmailNullException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    @Test
    void shouldCreateEmailWithValidValue() {
        String validEmail = "test@example.com";
        Email email = Email.valueOf(validEmail);

        assertNotNull(email);
        assertEquals(validEmail, email.getValue());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        assertThrows(EmailNullException.class, () -> Email.valueOf(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "ab", "abc", "abcd"})
    void shouldThrowExceptionWhenEmailIsTooShort(String shortEmail) {
        assertThrows(EmailInvalidLengthException.class, () -> Email.valueOf(shortEmail));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsTooLong() {
        String longEmail = "a".repeat(Email.MAX_LENGTH + 1) + "@example.com";
        assertThrows(EmailInvalidLengthException.class, () -> Email.valueOf(longEmail));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid-email",
            "test@",
            "@example.com",
            "test@.com",
            "test@example.",
            "test@example..com",
            "test@.example.com",
            "test@-example.com"
    })
    void shouldThrowExceptionWhenEmailHasInvalidFormat(String invalidEmail) {
        assertThrows(EmailInvalidFormatException.class, () -> Email.valueOf(invalidEmail));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "test@example.com",
            "user.name@domain.br",
            "user_name@domain.gov.br",
            "user-name@domain.edu",
            "user123@domain.gg",
            "user+tag@domain.kr",
            "user.name@sub.domain.com",
            "first.last@example.co.uk",
            "email@domain-one.xyz",
            "email@domain.name",
            "email@domain.co.jp",
            "1234567890@domain.com",
            "email@domain-web.com"
    })
    void shouldAcceptValidEmailFormats(String validEmail) {
        assertDoesNotThrow(() -> {
            Email email = Email.valueOf(validEmail);
            assertNotNull(email);
            assertEquals(validEmail, email.getValue());
        });
    }

    @Test
    void shouldCreateEmailUsingFromInfraMethod() {
        String emailValue = "test@infra.com";
        Email email = Email.valueOfUnsafe(emailValue);

        assertNotNull(email);
        assertEquals(emailValue, email.getValue());
    }

    @Test
    void fromInfraShouldBypassValidation() {
        String invalidEmail = "invalid-email";
        Email email = Email.valueOfUnsafe(invalidEmail);

        assertNotNull(email);
        assertEquals(invalidEmail, email.getValue());
    }

    @Test
    void shouldRespectLengthConstants() {
        String minLengthEmail = "a@b.cd";
        assertDoesNotThrow(() -> Email.valueOf(minLengthEmail));

        String domainPart = "@abc.defgh";
        String localPart = "a".repeat(Email.MAX_LENGTH - domainPart.length());
        String maxLengthEmail = localPart + domainPart;
        assertEquals(Email.MAX_LENGTH, maxLengthEmail.length());
        assertDoesNotThrow(() -> Email.valueOf(maxLengthEmail));
    }
}
