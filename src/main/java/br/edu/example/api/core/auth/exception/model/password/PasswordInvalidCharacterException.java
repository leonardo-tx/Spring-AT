package br.edu.example.api.core.auth.exception.model.password;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class PasswordInvalidCharacterException extends ValidationException {
    public PasswordInvalidCharacterException() {
        super("auth.password.invalid.character", "The password contains invalid characters.");
    }
}
