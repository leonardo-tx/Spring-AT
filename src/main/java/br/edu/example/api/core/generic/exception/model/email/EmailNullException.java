package br.edu.example.api.core.generic.exception.model.email;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class EmailNullException extends ValidationException {
    public EmailNullException() {
        super("email.null", "The email cannot be null.");
    }
}
