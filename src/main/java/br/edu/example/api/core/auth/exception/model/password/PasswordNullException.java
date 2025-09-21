package br.edu.example.api.core.auth.exception.model.password;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class PasswordNullException extends ValidationException {
    public PasswordNullException() {
        super("auth.password.null", "The user password cannot be null.");
    }
}
