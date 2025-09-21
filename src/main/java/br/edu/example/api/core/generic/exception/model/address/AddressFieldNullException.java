package br.edu.example.api.core.generic.exception.model.address;

import br.edu.example.api.core.generic.exception.ValidationException;
import br.edu.example.api.core.generic.model.Address;

public final class AddressFieldNullException extends ValidationException {
    public AddressFieldNullException(String fieldName) {
        super(getCode(fieldName), getMessage(fieldName));
    }

    private static String getCode(String fieldName) {
        return String.format("address.%s.null", fieldName);
    }

    private static String getMessage(String fieldName) {
        return String.format("The %s cannot be null.", fieldName);
    }
}
