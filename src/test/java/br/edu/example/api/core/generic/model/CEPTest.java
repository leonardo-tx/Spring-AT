package br.edu.example.api.core.generic.model;

import br.edu.example.api.core.generic.exception.model.cep.CEPInvalidLengthException;
import br.edu.example.api.core.generic.exception.model.cep.CEPNullException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CEPTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "123456-78",
            "51051403",
            "20040002",
            "227258-91",
            "699327-01"
    })
    void shouldCreateValidCEP(String value) {
        CEP cep = CEP.valueOf(value);
        assertEquals(value.replaceAll("\\D", ""), cep.getValue());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        assertThrows(CEPNullException.class, () -> CEP.valueOf(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "1234567", "123456789", "1234567890"})
    void shouldThrowExceptionWhenLengthIsInvalid(String invalidValue) {
        assertThrows(CEPInvalidLengthException.class, () -> CEP.valueOf(invalidValue));
    }

    @Test
    void shouldNotValidateWhenUsingUnsafe() {
        assertDoesNotThrow(() -> CEP.valueOfUnsafe("abc123"));
    }
}
