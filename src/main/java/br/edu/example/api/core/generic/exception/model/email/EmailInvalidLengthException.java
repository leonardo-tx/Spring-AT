package br.edu.example.api.core.generic.exception.model.email;

import br.edu.example.api.core.generic.exception.ValidationException;
import br.edu.example.api.core.generic.model.Email;

public final class EmailInvalidLengthException extends ValidationException {
    private static final String MESSAGE = String.format(
            "The email length must be between %d and %d characters.",
            Email.MIN_LENGTH,
            Email.MAX_LENGTH
    );

    public EmailInvalidLengthException() {
        super("email.invalid.length", MESSAGE);
    }
}
