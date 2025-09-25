package br.edu.example.api.core.generic.model;

import br.edu.example.api.core.generic.exception.model.phone.PhoneInvalidCharacterException;
import br.edu.example.api.core.generic.exception.model.phone.PhoneInvalidLengthException;
import br.edu.example.api.core.generic.exception.model.phone.PhoneNullException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {
    @Test
    void shouldCreatePhoneWithValidNumericValue() {
        String validPhone = "11987654321";

        Phone phone = Phone.valueOf(validPhone);

        assertNotNull(phone);
        assertEquals(validPhone, phone.getValue());
    }

    @Test
    void shouldStripWhitespaceFromPhoneNumber() {
        String phoneWithWhitespace = "  11987654321  ";
        String expectedPhone = "11987654321";

        Phone phone = Phone.valueOf(phoneWithWhitespace);

        assertNotNull(phone);
        assertEquals(expectedPhone, phone.getValue());
    }

    @Test
    void shouldThrowExceptionWhenPhoneIsNull() {
        assertThrows(PhoneNullException.class, () -> Phone.valueOf(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a*", "abc", "1198765abc"})
    void shouldThrowExceptionWhenPhoneContainsNonNumericCharacters(String invalidPhone) {
        assertThrows(PhoneInvalidCharacterException.class, () -> Phone.valueOf(invalidPhone));
    }

    @Test
    void shouldThrowExceptionWhenPhoneIsTooShort() {
        assertThrows(PhoneInvalidLengthException.class, () -> Phone.valueOf("1"));
    }

    @Test
    void shouldThrowExceptionWhenPhoneIsTooLong() {
        String longPhone = "1".repeat(Phone.MAX_LENGTH + 1);

        assertThrows(PhoneInvalidLengthException.class, () -> Phone.valueOf(longPhone));
    }

    @Test
    void shouldAcceptPhoneWithMinimumLength() {
        String minLengthPhone = "12";

        assertDoesNotThrow(() -> {
            Phone phone = Phone.valueOf(minLengthPhone);
            assertNotNull(phone);
            assertEquals(minLengthPhone, phone.getValue());
        });
    }

    @Test
    void shouldAcceptPhoneWithMaximumLength() {
        String maxLengthPhone = "1".repeat(Phone.MAX_LENGTH);
        assertDoesNotThrow(() -> {
            Phone phone = Phone.valueOf(maxLengthPhone);
            assertNotNull(phone);
            assertEquals(maxLengthPhone, phone.getValue());
        });
    }

    @Test
    void shouldCreatePhoneUsingFromUnsafeMethod() {
        String phoneValue = "11987654321";

        Phone phone = Phone.valueOfUnsafe(phoneValue);

        assertNotNull(phone);
        assertEquals(phoneValue, phone.getValue());
    }

    @Test
    void shouldBypassValidationWhenUsingUnsafe() {
        String invalidPhone = "11abc123";

        Phone phone = Phone.valueOfUnsafe(invalidPhone);

        assertNotNull(phone);
        assertEquals(invalidPhone, phone.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "11",
            "123",
            "11987654321",
            "08001234567",
            "99999999999999999999999999999999"
    })
    void shouldAcceptVariousValidPhoneNumbers(String validPhone) {
        assertDoesNotThrow(() -> {
            Phone phone = Phone.valueOf(validPhone);
            assertNotNull(phone);
            assertEquals(validPhone, phone.getValue());
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {
            " 11987654321 ",
            "  11  ",
            "  1234567890  "
    })
    void shouldStripWhitespaceFromVariousPhoneNumbers(String phoneWithWhitespace) {
        String expectedPhone = phoneWithWhitespace.strip();

        Phone phone = Phone.valueOf(phoneWithWhitespace);

        assertNotNull(phone);
        assertEquals(expectedPhone, phone.getValue());
    }
}
