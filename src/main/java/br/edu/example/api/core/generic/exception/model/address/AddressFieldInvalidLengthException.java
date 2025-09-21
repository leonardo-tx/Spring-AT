package br.edu.example.api.core.generic.exception.model.address;

import br.edu.example.api.core.generic.exception.ValidationException;
import br.edu.example.api.core.generic.model.Address;

public final class AddressFieldInvalidLengthException extends ValidationException {
    public AddressFieldInvalidLengthException(String fieldName) {
        super(getCode(fieldName), getMessage(fieldName));
    }

    private static String getCode(String fieldName) {
        return String.format("address.%s.invalid.length", fieldName);
    }

    private static String getMessage(String fieldName) {
        return String.format(
                "The %s length must be between %d and %d characters",
                fieldName,
                Address.MIN_LENGTH,
                Address.MAX_LENGTH
        );
    }
}
