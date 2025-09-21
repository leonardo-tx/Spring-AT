package br.edu.example.api.core.generic.exception.model.phone;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class PhoneNullException extends ValidationException {
    public PhoneNullException() {
        super("phone.null", "The phone number cannot be null.");
    }
}
