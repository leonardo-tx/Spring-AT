package br.edu.example.api.core.generic.model;

import br.edu.example.api.core.generic.exception.model.address.AddressFieldInvalidLengthException;
import br.edu.example.api.core.generic.exception.model.address.AddressFieldNullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    private final CEP validCEP = CEP.valueOf("12345678");

    @Test
    void shouldCreateValidAddress() {
        Address address = Address.valueOf(
                "Rua A",
                "123",
                "Apto 1",
                "Bairro B",
                "Cidade C",
                "Estado D",
                validCEP
        );
        assertNotNull(address);
        assertEquals("Rua A", address.getStreet());
        assertEquals("123", address.getNumber());
        assertEquals("Apto 1", address.getComplement());
        assertEquals("Bairro B", address.getNeighborhood());
        assertEquals("Cidade C", address.getCity());
        assertEquals("Estado D", address.getState());
        assertEquals(validCEP, address.getCep());
    }

    @Test
    void shouldCreateValidAddressWithoutComplement() {
        Address address = Address.valueOf(
                "Rua A",
                "123",
                null,
                "Bairro B",
                "Cidade C",
                "Estado D",
                validCEP
        );
        assertNotNull(address);
        assertEquals("Rua A", address.getStreet());
        assertEquals("123", address.getNumber());
        assertNull(address.getComplement());
        assertEquals("Bairro B", address.getNeighborhood());
        assertEquals("Cidade C", address.getCity());
        assertEquals("Estado D", address.getState());
        assertEquals(validCEP, address.getCep());
    }

    @Test
    void shouldThrowExceptionWhenStreetIsNull() {
        assertThrows(AddressFieldNullException.class, () ->
                Address.valueOf(null, "123", "Apto 1", "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenNumberIsNull() {
        assertThrows(AddressFieldNullException.class, () ->
                Address.valueOf("Rua A", null, "Apto 1", "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenNeighborhoodIsNull() {
        assertThrows(AddressFieldNullException.class, () ->
                Address.valueOf("Rua A", "123", "Apto 1", null, "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenCityIsNull() {
        assertThrows(AddressFieldNullException.class, () ->
                Address.valueOf("Rua A", "123", "Apto 1", "Bairro B", null, "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenStateIsNull() {
        assertThrows(AddressFieldNullException.class, () ->
                Address.valueOf("Rua A", "123", "Apto 1", "Bairro B", "Cidade C", null, validCEP));
    }

    @Test
    void shouldThrowExceptionWhenCEPIsNull() {
        assertThrows(NullPointerException.class, () ->
                Address.valueOf("Rua A", "123", "Apto 1", "Bairro B", "Cidade C", "Estado D", null));
    }

    @Test
    void shouldThrowExceptionWhenStreetTooLong() {
        String longStreet = "A".repeat(Address.MAX_LENGTH + 1);
        assertThrows(AddressFieldInvalidLengthException.class, () ->
                Address.valueOf(longStreet, "123", "Apto 1", "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenNumberTooLong() {
        String longNumber = "9".repeat(Address.MAX_LENGTH + 1);
        assertThrows(AddressFieldInvalidLengthException.class, () ->
                Address.valueOf("Rua A", longNumber, "Apto 1", "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenComplementTooLong() {
        String longComplement = "C".repeat(Address.MAX_LENGTH + 1);
        assertThrows(AddressFieldInvalidLengthException.class, () ->
                Address.valueOf("Rua A", "123", longComplement, "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenNeighborhoodTooLong() {
        String longNeighborhood = "N".repeat(Address.MAX_LENGTH + 1);
        assertThrows(AddressFieldInvalidLengthException.class, () ->
                Address.valueOf("Rua A", "123", "Apto 1", longNeighborhood, "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenCityTooLong() {
        String longCity = "C".repeat(Address.MAX_LENGTH + 1);
        assertThrows(AddressFieldInvalidLengthException.class, () ->
                Address.valueOf("Rua A", "123", "Apto 1", "Bairro B", longCity, "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenStateTooLong() {
        String longState = "S".repeat(Address.MAX_LENGTH + 1);
        assertThrows(AddressFieldInvalidLengthException.class, () ->
                Address.valueOf("Rua A", "123", "Apto 1", "Bairro B", "Cidade C", longState, validCEP));
    }

    @Test
    void shouldThrowExceptionWhenStreetTooShort() {
        String longStreet = "";
        assertThrows(AddressFieldInvalidLengthException.class, () ->
                Address.valueOf(longStreet, "123", "Apto 1", "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenNumberTooShort() {
        String longNumber = "";
        assertThrows(AddressFieldInvalidLengthException.class, () ->
                Address.valueOf("Rua A", longNumber, "Apto 1", "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenComplementTooShort() {
        String longComplement = "";
        assertThrows(AddressFieldInvalidLengthException.class, () ->
                Address.valueOf("Rua A", "123", longComplement, "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenNeighborhoodTooShort() {
        String longNeighborhood = "";
        assertThrows(AddressFieldInvalidLengthException.class, () ->
                Address.valueOf("Rua A", "123", "Apto 1", longNeighborhood, "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenCityTooShort() {
        String longCity = "";
        assertThrows(AddressFieldInvalidLengthException.class, () ->
                Address.valueOf("Rua A", "123", "Apto 1", "Bairro B", longCity, "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenStateTooShort() {
        String longState = "";
        assertThrows(AddressFieldInvalidLengthException.class, () ->
                Address.valueOf("Rua A", "123", "Apto 1", "Bairro B", "Cidade C", longState, validCEP));
    }

    @Test
    void shouldNotValidateWhenUsingUnsafe() {
        assertDoesNotThrow(() -> Address.valueOfUnsafe(null, null, null, null, null, null, null));
        assertDoesNotThrow(() -> Address.valueOfUnsafe(
                "Rua A", "123", "Apto 1", "Bairro B", "Cidade C", "Estado D", validCEP
        ));
    }
}
