package br.edu.example.api.core.generic.model;

import br.edu.example.api.core.generic.exception.model.person.name.PersonNameInvalidLengthException;
import br.edu.example.api.core.generic.exception.model.person.name.PersonNameNullException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PersonNameTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "Johnny Silverhand",
            "Gabriela Reis",
            "Leonardo Teixeira",
            "Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
    })
    void shouldCreateValidPersonName(String value) {
        PersonName personName = PersonName.valueOf(value);
        assertEquals(value, personName.getValue());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        assertThrows(PersonNameNullException.class, () -> PersonName.valueOf(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "", "Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void shouldThrowExceptionWhenLengthIsInvalid(String invalidValue) {
        assertThrows(PersonNameInvalidLengthException.class, () -> PersonName.valueOf(invalidValue));
    }

    @Test
    void shouldNotValidateWhenUsingUnsafe() {
        assertDoesNotThrow(() -> PersonName.valueOfUnsafe(null));
        assertDoesNotThrow(() -> PersonName.valueOfUnsafe(""));
        assertDoesNotThrow(() -> PersonName.valueOfUnsafe("A"));
        assertDoesNotThrow(() -> PersonName.valueOfUnsafe("B".repeat(PersonName.MAX_LENGTH + 1)));
    }
}
