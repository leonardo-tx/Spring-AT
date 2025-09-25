package br.edu.example.api.core.generic.model;

import br.edu.example.api.core.generic.exception.model.cpf.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CPFTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "80188829083",
            "38206706020",
            "96729646098",
            "21502549050",
            "87251206030"
    })
    void shouldCreateValidCPF(String value) {
        CPF cpf = CPF.valueOf(value);
        assertEquals(value, cpf.getValue());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        assertThrows(CPFNullException.class, () -> CPF.valueOf(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"023", "8234567890", "343456789012", "123456789012345"})
    void shouldThrowExceptionWhenLengthIsInvalid(String invalidValue) {
        assertThrows(CPFInvalidLengthException.class, () -> CPF.valueOf(invalidValue));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11111111111", "22222222222", "00000000000"})
    void shouldThrowExceptionWhenCPFHasAllSameDigits(String invalidCPF) {
        assertThrows(CPFInvalidSequenceException.class, () -> CPF.valueOf(invalidCPF));
    }

    @ParameterizedTest
    @ValueSource(strings = {"80188829074", "38206706041"})
    void shouldThrowExceptionWhenFirstDigitInvalid(String invalidCPF) {
        assertThrows(CPFFirstInvalidDigit.class, () -> CPF.valueOf(invalidCPF));
    }

    @ParameterizedTest
    @ValueSource(strings = {"80188829082", "38206706022"})
    void shouldThrowExceptionWhenSecondDigitInvalid(String invalidCPF) {
        assertThrows(CPFSecondInvalidDigit.class, () -> CPF.valueOf(invalidCPF));
    }

    @Test
    void shouldNotValidateWhenUsingUnsafe() {
        assertDoesNotThrow(() -> CPF.valueOfUnsafe("abc"));
    }
}
