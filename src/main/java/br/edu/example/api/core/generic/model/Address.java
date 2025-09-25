package br.edu.example.api.core.generic.model;

import br.edu.example.api.core.generic.exception.model.address.AddressFieldInvalidLengthException;
import br.edu.example.api.core.generic.exception.model.address.AddressFieldNullException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public final class Address {
    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 255;

    private final String street;
    private final String number;
    private final String complement;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final CEP cep;

    public static Address valueOf(
            String street,
            String number,
            String complement,
            String neighborhood,
            String city,
            String state,
            CEP cep
    ) {
        validateLength("street", street);
        validateLength("number", number);
        if (complement != null) {
            validateLength("complement", complement);
        }
        validateLength("neighborhood", neighborhood);
        validateLength("city", city);
        validateLength("state", state);
        Objects.requireNonNull(cep);

        return new Address(street, number, complement, neighborhood, city, state, cep);
    }

    public static Address valueOfUnsafe(
            String street,
            String number,
            String complement,
            String neighborhood,
            String city,
            String state,
            CEP cep
    ) {
        return new Address(street, number, complement, neighborhood, city, state, cep);
    }

    private static void validateLength(String fieldName, String value) {
        if (value == null) {
            throw new AddressFieldNullException(fieldName);
        }
        int length = value.length();
        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw new AddressFieldInvalidLengthException(fieldName);
        }
    }
}
