package br.edu.example.api.core.generic.exception.model.phone;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class PhoneInvalidCharacterException extends ValidationException {
    public PhoneInvalidCharacterException() {
        super("phone.invalid.character", "All characters of a phone must be numeric.");
    }
}
