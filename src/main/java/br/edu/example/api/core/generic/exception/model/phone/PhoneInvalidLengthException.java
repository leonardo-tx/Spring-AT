package br.edu.example.api.core.generic.exception.model.phone;

import br.edu.example.api.core.generic.exception.ValidationException;
import br.edu.example.api.core.generic.model.Phone;

public final class PhoneInvalidLengthException extends ValidationException {
    private static final String MESSAGE = String.format(
            "The phone number length must be between %d and %d characters.",
            Phone.MIN_LENGTH,
            Phone.MAX_LENGTH
    );

    public PhoneInvalidLengthException() {
        super("phone.invalid.length", MESSAGE);
    }
}
