package br.edu.example.api.core.generic.exception.model.email;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class EmailInvalidFormatException extends ValidationException {
    public EmailInvalidFormatException() {
        super("email.invalid.format", "The email format is invalid.");
    }
}
