package br.edu.example.api.core.auth.exception.model.password;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class PasswordBytesOverflowException extends ValidationException {
    public PasswordBytesOverflowException() {
        super("auth.password.bytes.overflow", "The user password has exceeds the maximum allowed bytes.");
    }
}
