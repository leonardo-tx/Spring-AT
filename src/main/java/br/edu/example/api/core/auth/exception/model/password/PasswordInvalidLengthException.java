package br.edu.example.api.core.auth.exception.model.password;

import br.edu.example.api.core.auth.model.Password;
import br.edu.example.api.core.generic.exception.ValidationException;

public final class PasswordInvalidLengthException extends ValidationException {
    private static final String MESSAGE = String.format(
            "The user email length must be greater than or equal to %d characters.",
            Password.MIN_LENGTH
    );

    public PasswordInvalidLengthException() {
        super("auth.password.invalid.length", MESSAGE);
    }
}
